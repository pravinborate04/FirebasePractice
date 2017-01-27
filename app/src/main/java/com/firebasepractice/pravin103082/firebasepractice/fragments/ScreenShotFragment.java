package com.firebasepractice.pravin103082.firebasepractice.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.firebasepractice.pravin103082.firebasepractice.R;
import com.firebasepractice.pravin103082.firebasepractice.Utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class ScreenShotFragment extends Fragment {


    Button btnScreenShot;
    ImageView decodedImg;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_screen_shot, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnScreenShot=(Button)view.findViewById(R.id.btnScreenShot);
        decodedImg=(ImageView)view.findViewById(R.id.decodedImg);

        btnScreenShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Bitmap bitmap=getBitmapOFRootView(btnScreenShot);
               // createImage(bitmap);
                byte[] imgBytesData = android.util.Base64.decode(Utils.base64EncodeScreenShotScreen(getActivity()), android.util.Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(imgBytesData, 0, imgBytesData.length);
                decodedImg.setImageBitmap(decodedByte);
            }
        });
    }


    public Bitmap getBitmapOFRootView(View v) {
        View rootview = v.getRootView();
        rootview.setDrawingCacheEnabled(true);
        Bitmap bitmap1 = rootview.getDrawingCache();
        return bitmap1;
    }
    public void createImage(Bitmap bmp) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
        File file = new File(Environment.getExternalStorageDirectory() +
                "/capturedscreenandroid.jpg");
        try {
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(bytes.toByteArray());

            Log.e("FIle",file.getAbsolutePath());

            String encodedfile = Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT);

            Log.e("endcoded",encodedfile);



            //to decode
            byte[] imgBytesData = android.util.Base64.decode(encodedfile, android.util.Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(imgBytesData, 0, imgBytesData.length);
            decodedImg.setImageBitmap(decodedByte);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
