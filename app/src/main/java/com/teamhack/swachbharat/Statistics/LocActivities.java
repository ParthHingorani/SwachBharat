package com.teamhack.swachbharat.Statistics;

import android.app.Activity;
import android.os.AsyncTask;
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

class LocActivities extends AsyncTask<Void, Void, ListAdapter>{

    private Activity activity;
    private ListView loc_activity_listView;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    LocActivities(Activity activity, View rv)
    {
        this.activity = activity;
        this.loc_activity_listView = (ListView) rv.findViewById(R.id.loc_activity_list_stats);
    }

    @Override
    protected void onPreExecute() {

        loc_activity_listView.setAdapter(null);
        loc_activity_listView.deferNotifyDataSetChanged();

    }

    @Override
    protected ListAdapter doInBackground(Void... voids) {

        ListAdapter adapter = new FirebaseListAdapter<Social>(activity, Social.class, R.layout.stats_item_loc_activity, databaseReference.child("Social").orderByChild("time"))
        {
            @Override
            protected void populateView(View view, Social social, int position) {
                if(social!=null)
                {
                    TextView title = (TextView)view.findViewById(R.id.txt_loc_activity_title);
                    TextView info = (TextView) view.findViewById(R.id.txt_loc_activity_info);
                    TextView time = (TextView) view.findViewById(R.id.txt_loc_activity_timestamp);
                    TextView by = (TextView) view.findViewById(R.id.txt_loc_activity_by);
                    title.setText(social.getCategory());
                    info.setText(social.getSocialDetail());
                    time.setText("Posted on : " + social.getTime());
                    by.setText(" by : " + social.getUser().getName());
                }
            }
        };

        return adapter;
    }

    @Override
    protected void onPostExecute(ListAdapter adapter) {

        setListViewHeightBasedOnChildren(loc_activity_listView);
        loc_activity_listView.setAdapter(adapter);

    }

}
