package com.teamhack.swachbharat.Statistics;

import android.app.Activity;
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

class BestNGOs {

    private String ngo = "NGO";
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    void best_ngo_setdata(Activity activity, View rv)
    {
        ListView best_ngo_listView = (ListView) rv.findViewById(R.id.best_ngo_list_stats);
        best_ngo_listView.setAdapter(null);
        best_ngo_listView.deferNotifyDataSetChanged();

        ListAdapter adapter = new ReverseFirebaseListAdapter<User>(activity, User.class, R.layout.stats_item_best_ngo, databaseReference.child("User").orderByChild("completed"))
        {

            @Override
            protected void populateView(View view, User user, int position) {

                if (user.getType().contentEquals(ngo))
                {
                    TextView title= (TextView)view.findViewById(R.id.txt_best_ngo_title);
                    TextView completed=(TextView)view.findViewById(R.id.txt_best_ngo_completed);
                    new TasksStatus(user,completed,null).execute();
                    title.setText(user.getName());
                }
            }
        };

        setListViewHeightBasedOnChildren(best_ngo_listView);
        best_ngo_listView.setAdapter(adapter);
    }

}
