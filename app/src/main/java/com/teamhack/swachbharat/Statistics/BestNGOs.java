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

class BestNGOs extends AsyncTask<Void, Void, ListAdapter>{

    private ListView best_ngo_listView;
    private Activity activity;
    private String ngo = "NGO";
    private View rv;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    BestNGOs(Activity activity, View rv)
    {
        this.activity = activity;
        this. best_ngo_listView = (ListView) rv.findViewById(R.id.best_ngo_list_stats);
        this.rv=rv;
    }

    @Override
    protected void onPreExecute() {

        best_ngo_listView.setAdapter(null);
        best_ngo_listView.deferNotifyDataSetChanged();

    }

    @Override
    protected ListAdapter doInBackground(Void... voids) {

        ListAdapter adapter = new ReverseFirebaseListAdapter<User>(activity, User.class, R.layout.stats_item_best_ngo, databaseReference.child("User").orderByChild("completed"))
        {

            @Override
            protected void populateView(View view, User user, int position) {

                if (user.getType().contentEquals(ngo))
                {
                    TextView title= (TextView)view.findViewById(R.id.txt_best_ngo_title);
                    TextView completed=(TextView)view.findViewById(R.id.txt_best_ngo_completed);
                    new TasksStatus(user,completed,null,rv).execute();
                    title.setText(user.getName());
                }
            }
        };

        return adapter;

    }

    @Override
    protected void onPostExecute(ListAdapter adapter) {

        setListViewHeightBasedOnChildren(best_ngo_listView);
        best_ngo_listView.setAdapter(adapter);

    }

}
