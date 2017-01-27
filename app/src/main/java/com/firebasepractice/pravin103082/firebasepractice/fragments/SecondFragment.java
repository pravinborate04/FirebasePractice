package com.firebasepractice.pravin103082.firebasepractice.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.firebasepractice.pravin103082.firebasepractice.App;
import com.firebasepractice.pravin103082.firebasepractice.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class SecondFragment extends Fragment {
    Button btnGallery;
    private int PICK_IMAGE_REQUEST = 1;
    ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = (ImageView) view.findViewById(R.id.imageView);
        btnGallery=(Button)view.findViewById(R.id.btnGallery);
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
// Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            final ProgressDialog progressdialog = new ProgressDialog(getActivity());
            progressdialog.setIndeterminate(false);
            progressdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressdialog.setCancelable(false);
            progressdialog.setMax(100);
            progressdialog.show();

            Uri uri = data.getData();
            Log.e("URI",uri.toString());

            StorageReference storageReference= App.getStorageReference();
            Log.e("uriname",uri.getLastPathSegment());
            StorageReference spaceRef = storageReference.child(App.getmAuth().getCurrentUser().getUid());
            UploadTask uploadTask=spaceRef.putFile(uri);
            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                    int status= (int) (100 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    Log.e("hello",100 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount()+"");
                    progressdialog.setProgress(status);

                    if(status == 100){
                        progressdialog.dismiss();
                    }
                }
            });
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
