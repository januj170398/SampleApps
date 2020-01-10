package com.sample.daggersample.car;

import android.util.Log;

public class Tires {
    //we don't own this class so we can't annotate it with @Inject

    private static final String TAG = "Car";

    public void inflate() {
        Log.d(TAG, "Tires inflated");
    }
}
