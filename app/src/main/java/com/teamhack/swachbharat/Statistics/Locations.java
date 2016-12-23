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
import com.teamhack.swachbharat.Share.Share;

import static com.teamhack.swachbharat.Statistics.StatisticsFragment.setListViewHeightBasedOnChildren;

/**
 * Created by neptune on 21/12/16.
 */

class Locations extends AsyncTask<Void, Void, ListAdapter>
{

    private ListView loc_listView;
    private Activity activity;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    Locations(Activity activity, View rv)
    {
        this.loc_listView = (ListView) rv.findViewById(R.id.loc_list_stats);
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {

        loc_listView.setAdapter(null);
        loc_listView.deferNotifyDataSetChanged();

    }

    @Override
    protected ListAdapter doInBackground(Void... params) {

        ListAdapter adapter = new ReverseFirebaseListAdapter<Share>(activity, Share.class, R.layout.stats_item_loc, databaseReference.child("Share").orderByChild("time"))
        {
            @Override
            protected void populateView(View view, Share share, int position) {
                if(share!=null)
                {
                    TextView title= (TextView)view.findViewById(R.id.txt_loc_title);
                    TextView time =(TextView)view.findViewById(R.id.txt_loc_timestamp);
                    TextView status =(TextView)view.findViewById(R.id.txt_loc_status);
                    status.setText("Status : " + share.getStatus());
                    time.setText("Posted on : " + share.getTime());
                    title.setText(share.getCategory());
                }
            }
        };

        return adapter;
    }

    @Override
    protected void onPostExecute(ListAdapter adapter)
    {
        setListViewHeightBasedOnChildren(loc_listView);
        loc_listView.setAdapter(adapter);

    }

}
