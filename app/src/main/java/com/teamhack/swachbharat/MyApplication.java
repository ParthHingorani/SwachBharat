package com.teamhack.swachbharat;

import android.app.Application;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Rishi on 09-12-2016.
 */

public class MyApplication extends Application {
    //public static GoogleApiClient mGoogleApiClient;
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
