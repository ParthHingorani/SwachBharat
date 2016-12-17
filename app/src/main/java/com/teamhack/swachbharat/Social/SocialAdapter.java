package com.teamhack.swachbharat.Social;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;
import com.teamhack.swachbharat.Feed.Feed;
import com.teamhack.swachbharat.Feed.FeedAdapter;
import com.teamhack.swachbharat.R;

import java.util.List;


/**
 * Created by ParthHingorani on 17-12-2016.
 */

public class SocialAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder> {

    static Context context;
    List<Feed> feedList;

    public SocialAdapter(Context context, List<Feed> feedList)  {
        this.context=context;
        this.feedList=feedList;
    }

    @Override
    public FeedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_social, parent, false);
        FeedAdapter.MyViewHolder holder = new FeedAdapter.MyViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(FeedAdapter.MyViewHolder holder, int position) {
        holder.setData(feedList.get(getItemCount()-position-1));
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder    {

        CardView cardView;
        TextView title, author, time;
        SupportMapFragment fragment;

        public MyViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            title = (TextView) itemView.findViewById(R.id.title);
            author = (TextView) itemView.findViewById(R.id.author);
            time = (TextView) itemView.findViewById(R.id.timestamp);
        }
    }
}
