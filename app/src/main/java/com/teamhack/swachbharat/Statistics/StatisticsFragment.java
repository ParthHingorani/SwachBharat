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

import com.teamhack.swachbharat.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

public class StatisticsFragment extends Fragment {

    public View rv;
    public static ArrayAdapter<String> usr_ad, loc_ad, loc_activity_ad, best_ngo_ad, best_usr_ad, task_ad, usr_active_ad;
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
        best_ngo_ad=setdata(getActivity(),R.layout.stats_item_best_ngo,R.id.txt_best_ngo_title,data_list);
        best_ngo_listView.setAdapter(best_ngo_ad);
        setListViewHeightBasedOnChildren(best_ngo_listView);
    }

    public void best_usr_setdata()
    {
        ListView best_usr_listView = (ListView) rv.findViewById(R.id.best_usr_list_stats);
        best_usr_ad=setdata(getActivity(),R.layout.stats_item_best_usr,R.id.txt_best_usr_title,data_list);
        best_usr_listView.setAdapter(best_usr_ad);
        setListViewHeightBasedOnChildren(best_usr_listView);
    }

    public void loc_setdata()
    {
        ListView loc_listView = (ListView) rv.findViewById(R.id.loc_list_stats);
        loc_ad=setdata(getActivity(),R.layout.stats_item_loc,R.id.txt_loc_title,data_list);
        loc_listView.setAdapter(loc_ad);
        setListViewHeightBasedOnChildren(loc_listView);
    }

    public void loc_activity_setdata()
    {
        ListView loc_activity_listView = (ListView) rv.findViewById(R.id.loc_activity_list_stats);
        loc_activity_ad=setdata(getActivity(),R.layout.stats_item_loc_activity,R.id.txt_loc_activity_title,data_list);
        loc_activity_listView.setAdapter(loc_activity_ad);
        setListViewHeightBasedOnChildren(loc_activity_listView);
    }

    public void task_setdata()
    {
        ListView task_listView = (ListView) rv.findViewById(R.id.task_list_stats);
        task_ad=setdata(getActivity(),R.layout.stats_item_task,R.id.txt_task_title,data_list);
        task_listView.setAdapter(task_ad);
        setListViewHeightBasedOnChildren(task_listView);
    }

    public void usr_setdata()
    {
        ListView usr_listView = (ListView) rv.findViewById(R.id.usr_list_stats);
        usr_ad=setdata(getActivity(),R.layout.stats_item_usr,R.id.txt_usr_title,data_list);
        usr_listView.setAdapter(usr_ad);
        setListViewHeightBasedOnChildren(usr_listView);
    }

    public void usr_active_setdata()
    {
        ListView usr_active_listview = (ListView) rv.findViewById(R.id.active_usr_list_stats);
        usr_active_ad=setdata(getActivity(),R.layout.stats_item_usr_active,R.id.txt_usr_active_title,data_list);
        usr_active_listview.setAdapter(usr_active_ad);
        setListViewHeightBasedOnChildren(usr_active_listview);
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
