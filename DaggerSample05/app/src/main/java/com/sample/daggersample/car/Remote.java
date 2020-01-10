package com.sample.daggersample.car;

import android.util.Log;

import javax.inject.Inject;

public class Remote {
    private static final String TAG = "Car";

    @Inject
    public Remote(){

    }

    public void setListener(Car car){
        Log.d(TAG, "Remote connected ...");
    }
}
