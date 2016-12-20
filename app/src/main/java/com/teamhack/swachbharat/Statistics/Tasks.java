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
import com.teamhack.swachbharat.Share.Share;

import static com.teamhack.swachbharat.Statistics.StatisticsFragment.setListViewHeightBasedOnChildren;

/**
 * Created by neptune on 21/12/16.
 */

class Tasks {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    void task_setdata(Activity activity, View rv)
    {
        ListView task_listView = (ListView) rv.findViewById(R.id.task_list_stats);
        task_listView.setAdapter(null);
        task_listView.deferNotifyDataSetChanged();

        ListAdapter adapter = new FirebaseListAdapter<Share>(activity, Share.class, R.layout.stats_item_task, databaseReference.child("Share").orderByChild("time"))
        {
            @Override
            protected void populateView(View view, Share share, int position) {

                TextView title= (TextView)view.findViewById(R.id.txt_task_title);
                TextView time =(TextView)view.findViewById(R.id.txt_task_timestamp);
                TextView takenby=(TextView)view.findViewById(R.id.txt_task_taken_by);
                if(share.getTakenBy()!=null)
                {
                    takenby.setText("Taken By : " + share.getTakenBy().getName());
                }
                else
                {
                    takenby.setText("Taken By : " + share.getTakenBy());
                }
                time.setText("Posted on : " + share.getTime());
                title.setText(share.getCategory());
            }
        };

        setListViewHeightBasedOnChildren(task_listView);
        task_listView.setAdapter(adapter);
    }

}
