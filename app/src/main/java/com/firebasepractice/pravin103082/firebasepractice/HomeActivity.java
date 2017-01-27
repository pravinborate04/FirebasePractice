package com.firebasepractice.pravin103082.firebasepractice;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebasepractice.pravin103082.firebasepractice.Utils.Utils;
import com.firebasepractice.pravin103082.firebasepractice.fragments.ChildListFragment;
import com.firebasepractice.pravin103082.firebasepractice.fragments.FirebaseDatabaseFragment;
import com.firebasepractice.pravin103082.firebasepractice.fragments.FirstFragment;
import com.firebasepractice.pravin103082.firebasepractice.fragments.ImageViewAbTestingFragment;
import com.firebasepractice.pravin103082.firebasepractice.fragments.ScreenShotFragment;
import com.firebasepractice.pravin103082.firebasepractice.fragments.SecondFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mastek.appengage.MA;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity  {


    TextView txtUserName;
    //Button btnScreen;
  //  FirebaseStorage storage;
    TabLayout tabLayout;
    ViewPager viewPager;
    List<Fragment> fragments;
    FirstFragment firstFragment;
    SecondFragment secondFragment;
    ChildListFragment childListFragment;
    FirebaseDatabaseFragment firebaseDatabaseFragment;
    ScreenShotFragment screenShotFragment;
    ImageViewAbTestingFragment testingFragment;
    List<String> tabs;
    //ImageView imgScreen;
    int i=0;
    boolean screenShotClicked=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

       // imgScreen=(ImageView)findViewById(R.id.imgScreen);
       // btnScreen=(Button)findViewById(R.id.btnScreen);
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.pager);
      /*  storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://fir-practice-c5ef6.appspot.com/");
        final StorageReference imagesRef = storageRef.child("Tulips.jpg");

        imagesRef.getDownloadUrl().addOnSuccessListener(this, new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.e("URI",uri.toString());

                    }
                });
*/

      /*  btnScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] imgBytesData = android.util.Base64.decode(Utils.base64EncodeScreenShotScreen(HomeActivity.this), android.util.Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(imgBytesData, 0, imgBytesData.length);
                imgScreen.setImageBitmap(decodedByte);
            }
        });*/

        fragments=new ArrayList<>();
        tabs=new ArrayList<>();

        firstFragment=new FirstFragment();
        secondFragment=new SecondFragment();
        childListFragment=new ChildListFragment();
        firebaseDatabaseFragment=new FirebaseDatabaseFragment();
        screenShotFragment=new ScreenShotFragment();
        testingFragment=new ImageViewAbTestingFragment();
        fragments.add(firstFragment);
        tabs.add("First");

        fragments.add(secondFragment);
        tabs.add("second");

        fragments.add(childListFragment);
        tabs.add("third");

        fragments.add(firebaseDatabaseFragment);
        tabs.add("Forth");

        fragments.add(screenShotFragment);
        tabs.add("Screen Shot");

        fragments.add(testingFragment);
        tabs.add("ImageView testing");
        viewPager.setAdapter(new FragmentViewPagerAdapter(getSupportFragmentManager(),fragments,tabs));
        tabLayout.setupWithViewPager(viewPager);
        txtUserName=(TextView)findViewById(R.id.txtUserName);
        viewPager.setOffscreenPageLimit(fragments.size());

        Utils.forAbTesting(this,tabLayout,"tabs","tabs");

        txtUserName.setText(App.getmAuth().getCurrentUser().getEmail());
        //Utils.forAbTesting(this,txtUserName,"userName","NAME");
        if(App.getmAuth().getCurrentUser().getPhotoUrl()!=null)
        Log.e("USer photo",App.getmAuth().getCurrentUser().getPhotoUrl().toString());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                MA.removeCustomAppUser();
                App.getmAuth().signOut();
                App.getInstance().clearUser();

                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }




}
