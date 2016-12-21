package com.teamhack.swachbharat.Social;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.teamhack.swachbharat.R;

import java.util.List;


/**
 * Created by ParthHingorani on 17-12-2016.
 */

public class SocialAdapter extends RecyclerView.Adapter<SocialAdapter.MyViewHolder> {

    static Context context;
    List<Social> socialList;
    private int lastPosition=-1;

    public SocialAdapter(Context context, List<Social> socialList)  {
        this.context=context;
        this.socialList=socialList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_social, parent, false);
        SocialAdapter.MyViewHolder holder = new SocialAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SocialAdapter.MyViewHolder holder, int position) {
        holder.setData(socialList.get(getItemCount()-position-1));
        setAnimation(holder.cardView, position);
    }

    public void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
            animation.setInterpolator(context,android.R.anim.anticipate_overshoot_interpolator);
            animation.setDuration(600);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return socialList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{



        CardView cardView;
        TextView title, author, time, detail;
        ImageView img_map;


        public MyViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            title = (TextView) itemView.findViewById(R.id.title);
            author = (TextView) itemView.findViewById(R.id.author);
            time = (TextView) itemView.findViewById(R.id.timestamp);
            detail = (TextView) itemView.findViewById(R.id.postDetails);
            img_map= (ImageView) itemView.findViewById(R.id.img_social_map);
        }

        public void setData(final Social data) {

            String center=data.latitude+","+data.longitude;
            String MAP_URL="https://maps.googleapis.com/maps/api/staticmap?center="+center+"&zoom=19&size=500x300&maptype=roadmap&markers=color:red%7C"+center+"%7Ckey="+context.getResources().getString(R.string.google_map_stat_key);
            Uri img_uri=Uri.parse(MAP_URL);

            String newTitle = "I want to " + data.getCategory();
            title.setText(newTitle);
            time.setText(data.getTime());
            author.setText(data.user.name);
            detail.setText(data.getSocialDetail());
            Glide.with(context).load(img_uri).into(img_map);
        }

    }

}
