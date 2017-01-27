package com.firebasepractice.pravin103082.firebasepractice.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebasepractice.pravin103082.firebasepractice.App;
import com.firebasepractice.pravin103082.firebasepractice.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.DocumentType;

public class FirebaseDatabaseFragment extends Fragment {


    TextView txtContactId,txtCountry,txtDocumentType;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_firebase_database, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        txtContactId=(TextView)view.findViewById(R.id.txtContactId);
        txtCountry=(TextView)view.findViewById(R.id.txtCountry);
        txtDocumentType=(TextView)view.findViewById(R.id.txtDocumentType);


        DatabaseReference myRef = App.getFirebaseDatabase().getReference("ContactID");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
               /* Log.e("Value", "Value is: " + dataSnapshot.toString());
                String value = dataSnapshot.getValue(String.class);
                Log.e("Value", "Value is: " + value);*/
                Log.e("Conta Value",dataSnapshot.getValue()+"");
                txtContactId.setText(""+dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("FAILED VA", "Failed to read value.", error.toException());
            }
        });

        DatabaseReference countryRef=App.getFirebaseDatabase().getReference("Country");
        countryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Cont Value",dataSnapshot.getValue()+"");
                txtCountry.setText(""+dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference docType= App.getFirebaseDatabase().getReference("DocumentType");
        docType.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                txtDocumentType.setText(""+dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
