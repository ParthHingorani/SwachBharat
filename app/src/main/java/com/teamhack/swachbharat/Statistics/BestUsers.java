package com.teamhack.swachbharat.Statistics;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamhack.swachbharat.Profile.User;
import com.teamhack.swachbharat.R;

import static com.teamhack.swachbharat.Statistics.StatisticsFragment.setListViewHeightBasedOnChildren;

/**
 * Created by neptune on 21/12/16.
 */

class BestUsers extends AsyncTask<Void, Void, ListAdapter>{

    private Activity activity;
    private ListView best_usr_listView;
    private View rv;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private String individual = "Individual";

    BestUsers(Activity activity, View rv)
    {
        this.best_usr_listView = (ListView) rv.findViewById(R.id.best_usr_list_stats);
        this.activity = activity;
        this.rv=rv;
    }

    protected void onPreExecute() {

        best_usr_listView.setAdapter(null);
        best_usr_listView.deferNotifyDataSetChanged();

    }

    protected ListAdapter doInBackground(Void... params) {

        ListAdapter adapter = new ReverseFirebaseListAdapter<User>(activity, User.class, R.layout.stats_item_best_usr, databaseReference.child("User").orderByChild("completed"))
        {
            @Override
            protected void populateView(View view, User user, int position) {

                if(user.type!=null)
                {
                    if (user.getType().contentEquals(individual))
                    {
                        TextView title= (TextView)view.findViewById(R.id.txt_best_usr_title);
                        TextView completed=(TextView)view.findViewById(R.id.txt_best_usr_completed);
                        new TasksStatus(user,completed,null,rv).execute();
                        title.setText(user.getName());
                    }
                }
            }
        };

        return adapter;

    }

    protected void onPostExecute(ListAdapter adapter) {

        setListViewHeightBasedOnChildren(best_usr_listView);
        best_usr_listView.setAdapter(adapter);

    }

}
