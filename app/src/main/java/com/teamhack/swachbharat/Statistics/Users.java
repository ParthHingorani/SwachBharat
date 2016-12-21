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
import com.teamhack.swachbharat.Profile.User;
import com.teamhack.swachbharat.R;

import static com.teamhack.swachbharat.Statistics.StatisticsFragment.setListViewHeightBasedOnChildren;

/**
 * Created by neptune on 21/12/16.
 */

class Users extends AsyncTask<Void, Void, ListAdapter>
{
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private String individual = "Individual";
    private View rv;
    private ListView usr_listView;
    private Activity activity;

    public Users(Activity activity, View rv)
    {
        this.usr_listView = (ListView) rv.findViewById(R.id.usr_list_stats);
        this.activity = activity;
        this.rv = rv;
    }

    @Override
    protected void onPreExecute()
    {
        usr_listView.setAdapter(null);
        usr_listView.deferNotifyDataSetChanged();
    }

    @Override
    protected ListAdapter doInBackground(Void... params)
    {

        ListAdapter adapter = new FirebaseListAdapter<User>(activity, User.class, R.layout.stats_item_usr, databaseReference.child("User").orderByKey())
        {
            @Override
            protected void populateView(View view, User user, int position) {
                if(user.getType().contentEquals(individual))
                {
                    TextView title= (TextView)view.findViewById(R.id.txt_usr_title);
                    TextView taken=(TextView)view.findViewById(R.id.txt_usr_taken);
                    TextView completed=(TextView)view.findViewById(R.id.txt_usr_completed);
                    new TasksStatus(user,completed,taken,rv).execute();
                    title.setText(user.getName());
                }
            }
        };

        return adapter;
    }

    @Override
    protected void onPostExecute(ListAdapter adapter)
    {
        setListViewHeightBasedOnChildren(usr_listView);
        usr_listView.setAdapter(adapter);
    }
}
