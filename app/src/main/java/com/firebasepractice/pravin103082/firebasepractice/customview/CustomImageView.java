package com.firebasepractice.pravin103082.firebasepractice.customview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Build;
import android.renderscript.Allocation;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.firebasepractice.pravin103082.firebasepractice.R;

/**
 * Created by pravin103082 on 29-11-2016.
 */

public class CustomImageView extends RelativeLayout {

    View view;
    int startPosX,StartPostY;
    ImageView image1,image2,image3,image4,imageView2;


    public CustomImageView(Context context) {
        super(context);
        inflate(context, R.layout.custom_layout,null);
        image1= (ImageView) findViewById(R.id.image1);
        image2= (ImageView) findViewById(R.id.image2);
        image3= (ImageView) findViewById(R.id.image3);
        image4= (ImageView) findViewById(R.id.image4);
        imageView2=(ImageView)findViewById(R.id.imageView2);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
       /* String android_schemas = "http://schemas.android.com/apk/res/android";
        int srcId = attrs.getAttributeResourceValue(android_schemas, "src", -1);*/
        inflate(context, R.layout.custom_layout,this);
        image1= (ImageView) findViewById(R.id.image1);
        image2= (ImageView) findViewById(R.id.image2);
        image3= (ImageView) findViewById(R.id.image3);
        image4= (ImageView) findViewById(R.id.image4);
        imageView2=(ImageView)findViewById(R.id.imageView2);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.custom_layout,null);
        image1= (ImageView) findViewById(R.id.image1);
        image2= (ImageView) findViewById(R.id.image2);
        image3= (ImageView) findViewById(R.id.image3);
        image4= (ImageView) findViewById(R.id.image4);
        imageView2=(ImageView)findViewById(R.id.imageView2);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflate(context, R.layout.custom_layout,null);
        image1= (ImageView) findViewById(R.id.image1);
        image2= (ImageView) findViewById(R.id.image2);
        image3= (ImageView) findViewById(R.id.image3);
        image4= (ImageView) findViewById(R.id.image4);
        imageView2=(ImageView)findViewById(R.id.imageView2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        startPosX= (int) imageView2.getX();
        StartPostY=(int)imageView2.getY();
        Log.e("StartX",""+startPosX);
        Log.e("StartY",""+StartPostY);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        if(event.getAction()==MotionEvent.ACTION_UP){
            Log.e("UP","UP");

        }if (event.getAction()==MotionEvent.ACTION_DOWN){
            Log.e("DOWN","DOWN");
        }if(event.getAction()==MotionEvent.ACTION_MOVE){
            /*imageView2.setX(X);
            imageView2.setY(Y);*/

            float cx1 = this.getWidth() / 2.f;
            float cy1 = this.getHeight() / 2.f;
            float x1 = event.getX();
            float y1 = event.getY();
            float w1 = image1.getWidth();
            float h1 = image1.getHeight();

            double r1 = Math.min(cx1, cy1) / 2.;
            double dx1 = x1 - cx1;
            double dy1 = y1 - cy1;
            double hypot1 = Math.hypot(dx1, dy1);
            double cos1 = dx1 / hypot1;
            double sin1 = dy1 / hypot1;
            double rdx1 = hypot1 < 1. ? 0. : r1 * cos1;
            double rdy1 = hypot1 < 1. ? 0. : r1 * sin1;

            /*Animation animation=new MyAnimation(image1,(float) r1);
            animation.setDuration(3000);
            image1.startAnimation(animation);*/
            image1.setX((float) (cx1 + rdx1 - w1 / 2.));
            image1.setY((float) (cy1 + rdy1 - h1 / 2.));

   //========================================================================

          /*  float cx2 = this.getWidth() / 2.f;
            float cy2 = this.getHeight() / 2.f;
            float x2 = image1.getX();
            float y2 = image1.getY();
            float w2 = image2.getWidth();
            float h2 = image2.getHeight();

            double r2 = Math.min(cx2, cy2) / 2.;
            double dx2 = x2 - cx2;
            double dy2 = y2 - cy2;
            double hypot2 = Math.hypot(dx2, dy2);
            double cos2 = dx2 / hypot2;
            double sin2 = dy2 / hypot2;
            double rdx2 = hypot2 < 1. ? 0. : r2 * cos2;
            double rdy2 = hypot2 < 1. ? 0. : r2 * sin2;

            image2.setX((float) (cx2 + rdx2 - w2 / 2.));
            image2.setY((float) (cy2 + rdy2 - h2 / 2.));*/
   //=================================================================================

/*
            float cx3 = this.getWidth() / 2.f;
            float cy3 = this.getHeight() / 2.f;
            float x3 = event.getX();
            float y3 = event.getY();
            float w3 = image3.getWidth();
            float h3 = image3.getHeight();

            double r3 = Math.min(cx3, cy3) / 2.;
            double dx3 = x3 - cx3;
            double dy3 = y3 - cy3;
            double hypot3 = Math.hypot(dx3, dy3);
            double cos3 = dx3 / hypot3;
            double sin3 = dy3 / hypot3;
            double rdx3 = hypot3 < 1. ? 0. : r3 * cos3;
            double rdy3 = hypot3 < 1. ? 0. : r3 * sin3;

            image3.setX((float) (cx3 + rdx3 - w3 / 2.));
            image3.setY((float) (cy3 + rdy3 - h3 / 2.));
    //================================================================================

            float cx4 = this.getWidth() / 2.f;
            float cy4 = this.getHeight() / 2.f;
            float x4 = event.getX();
            float y4 = event.getY();
            float w4 = image4.getWidth();
            float h4 = image4.getHeight();

            double r4 = Math.min(cx4, cy4) / 2.;
            double dx4 = x4 - cx4;
            double dy4 = y4 - cy4;
            double hypot4 = Math.hypot(dx4, dy4);
            double cos4 = dx4 / hypot4;
            double sin4 = dy4 / hypot4;
            double rdx4 = hypot4 < 1. ? 0. : r4 * cos4;
            double rdy4 = hypot4 < 1. ? 0. : r4 * sin4;

            image4.setX((float) (cx4 + rdx4 - w4 / 2.));
            image4.setY((float) (cy4 + rdy4 - h4 / 2.));*/

        }
        return true;

    }
}
