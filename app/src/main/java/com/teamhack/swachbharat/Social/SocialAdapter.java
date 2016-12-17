package com.teamhack.swachbharat.Social;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;
import com.teamhack.swachbharat.R;

import java.util.List;


/**
 * Created by ParthHingorani on 17-12-2016.
 */

public class SocialAdapter extends RecyclerView.Adapter<SocialAdapter.MyViewHolder> {

    static Context context;
    List<Social> socialList;

    public SocialAdapter(Context context, List<Social> socialList)  {
        this.context=context;
        this.socialList=socialList;
    }

    @Override
    public SocialAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SocialAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_social, parent, false));
    }

    @Override
    public void onBindViewHolder(SocialAdapter.MyViewHolder holder, int position) {
        holder.setData(socialList.get(getItemCount()-position-1));
    }

    @Override
    public int getItemCount() {
        return socialList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder    {

        CardView cardView;
        TextView title, author, time, detail;
        SupportMapFragment fragment;

        public MyViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            title = (TextView) itemView.findViewById(R.id.title);
            author = (TextView) itemView.findViewById(R.id.author);
            time = (TextView) itemView.findViewById(R.id.timestamp);
            detail = (TextView) itemView.findViewById(R.id.postDetails);
        }

        public void setData(final Social data)   {
            String newTitle = "I want to " + data.getCategory();
            title.setText(newTitle);
            time.setText(data.getTime());
            author.setText(data.user.name);
            detail.setText(data.getSocialDetail());
        }
    }


}
