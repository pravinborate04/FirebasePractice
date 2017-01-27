package com.firebasepractice.pravin103082.firebasepractice.services;

import android.content.SharedPreferences;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.firebasepractice.pravin103082.firebasepractice.Utils.Utils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by pravin103082 on 18-11-2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getData()!=null){
            Log.e("data",remoteMessage.getData().size()+"");
            for(String s:remoteMessage.getData().keySet()){
                Log.e("KEY",s);
                Log.e("Value",remoteMessage.getData().get(s));
            }
        }

        String inAppMessaging=remoteMessage.getData().get("in_app");
        String activity=remoteMessage.getData().get("act");
     //   Log.e("inAppMeassage",inAppMessaging);

        if(!TextUtils.isEmpty(inAppMessaging)){
            SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("myInAppMessage",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("url",true);
            editor.putString("text",inAppMessaging);
            editor.putString("act",activity);
            editor.commit();
        }

    }
}
