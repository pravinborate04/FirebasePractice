package com.firebasepractice.pravin103082.firebasepractice;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firebasepractice.pravin103082.firebasepractice.Utils.FirebaseActivityTracker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mastek.appengage.ActivityTracker;
import com.mastek.appengage.MA;
import com.mastek.appengage.exchandler.ExceptionHandler;

/**
 * Created by pravin103082 on 17-11-2016.
 */

public class App extends Application implements FirebaseAuth.AuthStateListener {

    private static final String TAG =App.class.getName() ;
    SharedPreferences sharedPreferences;
    private static App app;
    private static FirebaseAuth mAuth;
    private static StorageReference storageReference;
    private static FirebaseAuth.AuthStateListener authStateListener;
    private static FirebaseDatabase firebaseDatabase;

    public static FirebaseDatabase getFirebaseDatabase() {
        return firebaseDatabase;
    }

    public static void setFirebaseDatabase(FirebaseDatabase firebaseDatabase) {
        App.firebaseDatabase = firebaseDatabase;
    }

    public static StorageReference getStorageReference() {
        return storageReference;
    }

    public static void setStorageReference(StorageReference storageReference) {
        App.storageReference = storageReference;
    }

    public static FirebaseAuth getmAuth() {
        return mAuth;
    }

    public static void setmAuth(FirebaseAuth mAuth) {
        App.mAuth = mAuth;
    }


    public void setUser(String id) {
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userId", id);
        editor.commit();
    }

    public String getUser() {
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        return sharedPreferences.getString("userId", "0");
    }

    public void clearUser() {
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }

    public static App getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        registerActivityLifecycleCallbacks(new ActivityTracker());
        MA.init(this,"http://52.87.24.173/api/","4170b44d6459bba992acaa857ac5b25d7fac6cc1");
        mAuth = FirebaseAuth.getInstance();
        authStateListener = this;
        mAuth.addAuthStateListener(authStateListener);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReferenceFromUrl("gs://fir-practice-c5ef6.appspot.com/");
        firebaseDatabase = FirebaseDatabase.getInstance();

        Log.e("TOken",""+ FirebaseInstanceId.getInstance().getToken());
    }


    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            // User is signed in
            Log.e("App", "onAuthStateChanged:signed_in:" + user.getUid());
        } else {
            // User is signed out
            Log.e("App", "onAuthStateChanged:signed_out");
        }
    }

}
