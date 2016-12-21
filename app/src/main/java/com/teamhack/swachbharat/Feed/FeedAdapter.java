package com.teamhack.swachbharat.Feed;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.teamhack.swachbharat.R;

import java.util.List;

/**
 * Created by Rishi on 08-12-2016.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder>{

    static Context context;
    List<Feed> feedList;
    private int lastPosition=-1;

    public FeedAdapter(Context context,List<Feed> feedList){
        this.context=context;
        this.feedList=feedList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_feed, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(feedList.get(getItemCount()-position-1));
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
        return feedList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView txt_title;
        TextView txt_content;
        TextView txt_author;
        TextView txt_time;
        ImageView img_feed;

        public MyViewHolder(View itemView) {
            super(itemView);
            cardView= (CardView) itemView.findViewById(R.id.list_feed_card);
            txt_title= (TextView) itemView.findViewById(R.id.txt_feed_title);
            txt_content= (TextView) itemView.findViewById(R.id.txt_feed_content);
            txt_author= (TextView) itemView.findViewById(R.id.txt_feed_author);
            txt_time= (TextView) itemView.findViewById(R.id.txt_feed_timestamp);
            img_feed= (ImageView) itemView.findViewById(R.id.img_feed_item);
        }

        public void setData(final Feed data){
//            cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(context, "Title: "+data.title, Toast.LENGTH_SHORT).show();
//                }
//            });
            if(data.imgurl.contentEquals("NIL")){
                img_feed.setVisibility(View.GONE);
            }else {
                img_feed.setVisibility(View.VISIBLE);
                Glide.with(context).load(data.imgurl).into(img_feed);
            }
            txt_title.setText(data.title);
            txt_content.setText(data.content);
            txt_author.setText(data.user.name);
            txt_time.setText(data.time);
        }
    }

}
