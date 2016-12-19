package com.teamhack.swachbharat.Statistics;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamhack.swachbharat.R;
import com.teamhack.swachbharat.Share.Share;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

public class StatisticsFragment extends Fragment {

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    public View rv;
    public static ArrayAdapter<String> loc_ad, loc_activity_ad, best_ngo_ad, best_usr_ad, task_ad, usr_active_ad;

    String[] dummy_data =
            {
                    "Dummy Data 1",
                    "Dummy Data 2",
                    "Dummy Data 3",
                    "Dummy Data 4"
            };

    List<String> data_list = new ArrayList<>(Arrays.asList(dummy_data));

    public StatisticsFragment() {
        // Required empty public constructor
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public ArrayAdapter<String> setdata(Context context, int xml, int id, List<String> list)
    {
        return new ArrayAdapter<String>
                (
                        context,
                        xml,
                        id,
                        list
                );
    }

    public void best_ngo_setdata()
    {
        ListView best_ngo_listView = (ListView) rv.findViewById(R.id.best_ngo_list_stats);
        best_ngo_listView.setAdapter(null);
        best_ngo_listView.deferNotifyDataSetChanged();
        best_ngo_ad=setdata(getActivity(),R.layout.stats_item_best_ngo,R.id.txt_best_ngo_title,data_list);
        setListViewHeightBasedOnChildren(best_ngo_listView);
        best_ngo_listView.setAdapter(best_ngo_ad);
    }

    public void best_usr_setdata()
    {
        ListView best_usr_listView = (ListView) rv.findViewById(R.id.best_usr_list_stats);
        best_usr_listView.setAdapter(null);
        best_usr_listView.deferNotifyDataSetChanged();
        best_usr_ad=setdata(getActivity(),R.layout.stats_item_best_usr,R.id.txt_best_usr_title,data_list);
        setListViewHeightBasedOnChildren(best_usr_listView);
        best_usr_listView.setAdapter(best_usr_ad);
    }

    public void loc_setdata()
    {
        ListView loc_listView = (ListView) rv.findViewById(R.id.loc_list_stats);
        loc_listView.setAdapter(null);
        loc_listView.deferNotifyDataSetChanged();

        ListAdapter adapter = new FirebaseListAdapter<Share>(getActivity(), Share.class, R.layout.stats_item_loc, databaseReference.child("Share").orderByChild("time"))
        {

            @Override
            protected void populateView(View view, Share share, int position) {

                TextView title= (TextView)view.findViewById(R.id.txt_loc_title);
                TextView time =(TextView)view.findViewById(R.id.txt_loc_timestamp);
                time.setText("Posted on : " + share.getTime());
                title.setText(share.getCategory());
            }
        };

        setListViewHeightBasedOnChildren(loc_listView);
        loc_listView.setAdapter(adapter);
    }

    public void loc_activity_setdata()
    {
        ListView loc_activity_listView = (ListView) rv.findViewById(R.id.loc_activity_list_stats);
        loc_activity_listView.setAdapter(null);
        loc_activity_listView.deferNotifyDataSetChanged();
        loc_activity_ad=setdata(getActivity(),R.layout.stats_item_loc_activity,R.id.txt_loc_activity_title,data_list);
        setListViewHeightBasedOnChildren(loc_activity_listView);
        loc_activity_listView.setAdapter(loc_activity_ad);
    }

    public void task_setdata()
    {
        ListView task_listView = (ListView) rv.findViewById(R.id.task_list_stats);
        task_listView.setAdapter(null);
        task_listView.deferNotifyDataSetChanged();

        ListAdapter adapter = new FirebaseListAdapter<Share>(getActivity(), Share.class, R.layout.stats_item_task, databaseReference.child("Share").orderByChild("time"))
        {

            @Override
            protected void populateView(View view, Share share, int position) {

                TextView title= (TextView)view.findViewById(R.id.txt_task_title);
                TextView time =(TextView)view.findViewById(R.id.txt_task_timestamp);
                time.setText("Posted on : " + share.getTime());
                title.setText(share.getCategory());
            }
        };

        setListViewHeightBasedOnChildren(task_listView);
        task_listView.setAdapter(adapter);
    }

    public void usr_active_setdata()
    {
        ListView usr_active_listview = (ListView) rv.findViewById(R.id.active_usr_list_stats);
        usr_active_listview.setAdapter(null);
        usr_active_listview.deferNotifyDataSetChanged();

        ListAdapter adapter = new ReverseFirebaseListAdapter<UserActive>(getActivity(), UserActive.class, R.layout.stats_item_usr_active, databaseReference.child("User").orderByChild("posts")) {

            @Override
            protected void populateView(View view, UserActive userActive, int position) {
                TextView title= (TextView)view.findViewById(R.id.txt_usr_active_title);
                TextView posts= (TextView)view.findViewById(R.id.txt_usr_active_posts);
                posts.setText("Posts : " + userActive.getPosts());
                title.setText(userActive.getName());
            }
        };

        setListViewHeightBasedOnChildren(usr_active_listview);
        usr_active_listview.setAdapter(adapter);
    }

    public void usr_setdata()
    {
        ListView usr_listView = (ListView) rv.findViewById(R.id.usr_list_stats);
        usr_listView.setAdapter(null);
        usr_listView.deferNotifyDataSetChanged();

        ListAdapter adapter = new FirebaseListAdapter<User>(getActivity(), User.class, R.layout.stats_item_usr, databaseReference.child("User").orderByKey())
        {

            @Override
            protected void populateView(View view, User user, int position) {

                TextView title= (TextView)view.findViewById(R.id.txt_usr_title);
                TextView taken=(TextView)view.findViewById(R.id.txt_usr_taken);
                TextView completed=(TextView)view.findViewById(R.id.txt_usr_completed);
                taken.setText("Tasks Taken : " + user.getTaken());
                completed.setText("Tasks Completed : " + user.getCompleted());
                title.setText(user.getName());
            }
        };

        setListViewHeightBasedOnChildren(usr_listView);
        usr_listView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_statistics, container, false);
        rv=rootView;

        best_ngo_setdata();
        best_usr_setdata();
        loc_setdata();
        loc_activity_setdata();
        task_setdata();
        usr_setdata();
        usr_active_setdata();

        return rootView;
    }

}
