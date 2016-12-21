package com.teamhack.swachbharat.Statistics;

import android.app.Activity;
import android.os.AsyncTask;
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

class ActiveUsers extends AsyncTask<Void, Void, ListAdapter>{

    private Activity activity;
    private ListView usr_active_listView;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private String individual = "Individual";

    ActiveUsers(Activity activity, View rv)
    {
        this.activity = activity;
        this.usr_active_listView = (ListView) rv.findViewById(R.id.active_usr_list_stats);
    }

    @Override
    protected void onPreExecute() {

        usr_active_listView.setAdapter(null);
        usr_active_listView.deferNotifyDataSetChanged();

    }

    @Override
    protected ListAdapter doInBackground(Void... voids) {

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

        return adapter;
    }

    @Override
    protected void onPostExecute(ListAdapter adapter) {

        setListViewHeightBasedOnChildren(usr_active_listView);
        usr_active_listView.setAdapter(adapter);

    }

}
