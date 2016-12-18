package com.teamhack.swachbharat.Statistics;

import android.app.Activity;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by neptune on 18/12/16.
 */

public abstract class ReverseFirebaseListAdapter<T> extends FirebaseListAdapter<T> {

    public ReverseFirebaseListAdapter(Activity activity, Class<T> modelClass, int modelLayout, Query ref) {
        super(activity, modelClass, modelLayout, ref);
    }

    public ReverseFirebaseListAdapter(Activity activity, Class<T> modelClass, int modelLayout, DatabaseReference ref) {
        super(activity, modelClass, modelLayout, ref);
    }

    @Override
    public T getItem(int position) {
        return super.getItem(getCount() - (position + 1));
    }
}