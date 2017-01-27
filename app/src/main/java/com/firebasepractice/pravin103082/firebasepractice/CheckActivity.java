package com.firebasepractice.pravin103082.firebasepractice;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import com.firebasepractice.pravin103082.firebasepractice.Utils.Utils;

public class CheckActivity extends AppCompatActivity {
    WebView webView;
    Button button;
    String path="http://www.cbu.edu.zm/downloads/pdf-sample.pdf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);

       // Utils.showInAppMessaging(this,"My Text");
/*
        webView= (WebView) findViewById(R.id.webView);
        button=(Button)findViewById(R.id.btn);
        webView.loadUrl("https://docs.google.com/viewer?url="+path.replaceAll(" ","%20"));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://docs.google.com/viewer?url="+path.replaceAll(" ","%20")));
                startActivity(intent);
            }
        });*/
    }
}
