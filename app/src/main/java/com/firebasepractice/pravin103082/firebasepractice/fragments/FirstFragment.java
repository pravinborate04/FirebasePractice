package com.firebasepractice.pravin103082.firebasepractice.fragments;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.firebasepractice.pravin103082.firebasepractice.App;
import com.firebasepractice.pravin103082.firebasepractice.R;
import com.firebasepractice.pravin103082.firebasepractice.Utils.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FirstFragment extends Fragment {



    ImageView imgDown;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgDown=(ImageView)view.findViewById(R.id.imgDown);
        StorageReference storageRef = App.getStorageReference();


        final StorageReference imagesRef = storageRef.child(App.getmAuth().getCurrentUser().getUid());

        imagesRef.getDownloadUrl().addOnSuccessListener(getActivity(), new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.e("URI",uri.toString());
                new generatePictureStyleNotification().execute(uri.toString());

            }
        });

        //Utils.forAbTesting(getActivity(),imgDown,"image_down","");

    }


    public class generatePictureStyleNotification extends AsyncTask<String, Void, Bitmap> {

        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(progressDialog==null){
                progressDialog=new ProgressDialog(getActivity());
                progressDialog.setMessage("Please wait");
            }
            if(!progressDialog.isShowing()){
                progressDialog.show();
            }
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            InputStream in;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                in = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(in);
                return myBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            imgDown.setImageBitmap(result);
        }
    }
}
