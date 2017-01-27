package com.firebasepractice.pravin103082.firebasepractice.Utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;


/**
 * Created by pravin103082 on 07-12-2016.
 */

public class FirebaseActivityTracker implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = FirebaseActivityTracker.class.getSimpleName();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated: " + activity.getLocalClassName());
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.d(TAG, "onActivityStarted: ");
        SharedPreferences prfs = activity.getSharedPreferences("myInAppMessage", Context.MODE_PRIVATE);
        boolean b = prfs.getBoolean("url", false);
        String inAppMessaging = prfs.getString("text", "");
        String act = prfs.getString("act", "");
        prfs.registerOnSharedPreferenceChangeListener(new Utils());
        Log.e("inApp", inAppMessaging);
        if (b) {
            if (!TextUtils.isEmpty(inAppMessaging)) {

                if (!TextUtils.isEmpty(act)) {
                    if (activity.getLocalClassName().equals(act)) {
                        Utils.showInAppMessaging(activity, inAppMessaging);
                    }
                } else {
                    Utils.showInAppMessaging(activity, inAppMessaging);
                }

                prfs.edit().clear().commit();
            }
        } else {
            Log.e("Bool", "" + b);
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.d(TAG, "onActivityResumed: " + activity.getLocalClassName());
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.d(TAG, "onActivityPaused: " + activity.getLocalClassName());
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Log.d(TAG, "onActivityStopped: " + activity.getLocalClassName());
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.d(TAG, "onActivitySaveInstanceState: " + activity.getLocalClassName());
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Log.d(TAG, "onActivityDestroyed: " + activity.getLocalClassName());
    }
}
