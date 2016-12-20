package com.teamhack.swachbharat.Statistics;

import android.app.Activity;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamhack.swachbharat.R;
import com.teamhack.swachbharat.Social.Social;

import static com.teamhack.swachbharat.Statistics.StatisticsFragment.setListViewHeightBasedOnChildren;

/**
 * Created by neptune on 21/12/16.
 */

class LocActivities {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    void loc_activity_setdata(Activity activity, View rv)
    {
        ListView loc_activity_listView = (ListView) rv.findViewById(R.id.loc_activity_list_stats);
        loc_activity_listView.setAdapter(null);
        loc_activity_listView.deferNotifyDataSetChanged();

        ListAdapter adapter = new FirebaseListAdapter<Social>(activity, Social.class, R.layout.stats_item_loc_activity, databaseReference.child("Social").orderByChild("time"))
        {
            @Override
            protected void populateView(View view, Social social, int position) {

                TextView title = (TextView)view.findViewById(R.id.txt_loc_activity_title);
                TextView info = (TextView) view.findViewById(R.id.txt_loc_activity_info);
                TextView time = (TextView) view.findViewById(R.id.txt_loc_activity_timestamp);
                TextView by = (TextView) view.findViewById(R.id.txt_loc_activity_by);
                title.setText(social.getCategory());
                info.setText(social.getSocialDetail());
                time.setText("Posted on : " + social.getTime());
                by.setText(" by : " + social.getUser().getName());
            }
        };

        setListViewHeightBasedOnChildren(loc_activity_listView);
        loc_activity_listView.setAdapter(adapter);
    }

}
