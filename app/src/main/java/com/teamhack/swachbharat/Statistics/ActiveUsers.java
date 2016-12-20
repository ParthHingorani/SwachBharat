package com.teamhack.swachbharat.Statistics;

import android.app.Activity;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamhack.swachbharat.R;

import static com.teamhack.swachbharat.Statistics.StatisticsFragment.setListViewHeightBasedOnChildren;

/**
 * Created by neptune on 21/12/16.
 */

class ActiveUsers {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private String individual = "Individual";

    void usr_active_setdata(Activity activity, View rv)
    {
        ListView usr_active_listview = (ListView) rv.findViewById(R.id.active_usr_list_stats);
        usr_active_listview.setAdapter(null);
        usr_active_listview.deferNotifyDataSetChanged();

        ListAdapter adapter = new ReverseFirebaseListAdapter<com.teamhack.swachbharat.Profile.User>(activity, com.teamhack.swachbharat.Profile.User.class, R.layout.stats_item_usr_active, databaseReference.child("User").orderByChild("posts"))
        {
            @Override
            protected void populateView(View view, com.teamhack.swachbharat.Profile.User userActive, int position) {
                if(userActive.type.contentEquals(individual))
                {
                    TextView title= (TextView)view.findViewById(R.id.txt_usr_active_title);
                    TextView posts= (TextView)view.findViewById(R.id.txt_usr_active_posts);
                    posts.setText("Posts : " + userActive.getPosts());
                    title.setText(userActive.getName());
                }
            }
        };

        setListViewHeightBasedOnChildren(usr_active_listview);
        usr_active_listview.setAdapter(adapter);
    }

}
