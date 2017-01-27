package com.firebasepractice.pravin103082.firebasepractice.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.firebasepractice.pravin103082.firebasepractice.R;
import com.firebasepractice.pravin103082.firebasepractice.Utils.Utils;

public class ImageViewAbTestingFragment extends Fragment {
    ImageView imgAbTesting;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_view_ab_testing, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgAbTesting=(ImageView)view.findViewById(R.id.imgAbTesting);

        Utils.forAbTesting(getActivity(),imgAbTesting,"img","");
    }
}
