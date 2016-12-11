package com.teamhack.swachbharat;

import com.google.firebase.database.FirebaseDatabase;
import com.teamhack.swachbharat.Profile.User;

/**
 * Created by Rishi on 09-12-2016.
 */

public class MyApplication extends android.app.Application {
    //public static GoogleApiClient mGoogleApiClient;
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
