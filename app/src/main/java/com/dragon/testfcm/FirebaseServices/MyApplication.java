package com.dragon.testfcm.FirebaseServices;

import android.app.Application;
import android.content.Context;

import com.google.firebase.FirebaseApp;

/**
 * Created by admin on 3/13/2018.
 */

public class MyApplication extends Application {

    private static MyApplication mInstance;
    private static Context myApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        try {
            myApplicationContext=this;
            FirebaseApp.initializeApp(this);
        } catch (Exception e) {
        }
    }

    public static Context getMyApplicationContext() {
        return myApplicationContext;
    }
}
