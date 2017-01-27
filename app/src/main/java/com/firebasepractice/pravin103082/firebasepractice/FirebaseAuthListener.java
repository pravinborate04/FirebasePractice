package com.firebasepractice.pravin103082.firebasepractice;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by pravin103082 on 17-11-2016.
 */

public class FirebaseAuthListener implements FirebaseAuth.AuthStateListener {
    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        Log.e("Firebase Auth",firebaseAuth.getCurrentUser().getEmail());
    }
}
