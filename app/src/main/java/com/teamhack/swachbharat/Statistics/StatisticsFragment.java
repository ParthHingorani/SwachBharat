package com.teamhack.swachbharat.Statistics;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.teamhack.swachbharat.R;

/**
 * A simple {@link Fragment} subclass.
 */

public class StatisticsFragment extends Fragment {

    /*BestNGOs bestNGOs = new BestNGOs();
    BestUsers bestUsers = new BestUsers();
    LocActivities locActivities = new LocActivities();
    Locations locations = new Locations();
    Tasks tasks = new Tasks();
    ActiveUsers activeUsers = new ActiveUsers();
    */
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_statistics, container, false);

        /*
        bestNGOs.best_ngo_setdata(getActivity(), rootView);
        bestUsers.best_usr_setdata(getActivity(), rootView);
        locations.loc_setdata(getActivity(), rootView);
        locActivities.loc_activity_setdata(getActivity(), rootView);
        tasks.task_setdata(getActivity(), rootView);

        activeUsers.usr_active_setdata(getActivity(), rootView);
        */

        new Users(getActivity(),rootView).execute();

        return rootView;
    }

}
