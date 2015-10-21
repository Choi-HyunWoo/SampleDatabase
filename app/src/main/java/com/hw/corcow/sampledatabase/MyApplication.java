package com.hw.corcow.sampledatabase;

import android.app.Application;
import android.content.Context;

/**
 * Created by Tacademy on 2015-10-21.
 */
public class MyApplication extends Application {
    // Context를 얻기 위한 MyApplication class

    private static Context mContext;
    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
