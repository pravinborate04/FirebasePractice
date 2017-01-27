package com.firebasepractice.pravin103082.firebasepractice.Utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.firebasepractice.pravin103082.firebasepractice.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import static android.view.Gravity.CENTER;

/**
 * Created by pravin103082 on 18-11-2016.
 */

public class Utils implements SharedPreferences.OnSharedPreferenceChangeListener {


    public static String base64EncodeScreenShotScreen(Activity activity) {
        View view = activity.getWindow().getDecorView().getRootView();
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap1 = view.getDrawingCache();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
        String encodedfile = Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT);


        return encodedfile;
    }


    public static void forAbTesting(final Activity activity, final View view, final String keyParameter, final String defaultString) {

       /* final ProgressDialog progressDialog=new ProgressDialog(activity);
        progressDialog.setMessage("Please wait");
        progressDialog.show();*/
        final FirebaseRemoteConfig mRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings remoteConfigSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(true)
                .build();
        mRemoteConfig.setConfigSettings(remoteConfigSettings);

        final HashMap<String, Object> defaults = new HashMap<>();


        // cache expiration in seconds
        long cacheExpiration = 3600;

//expire the cache immediately for development mode.
        if (mRemoteConfig.getInfo().getConfigSettings().isDeveloperModeEnabled()) {
            cacheExpiration = 0;
        }
        mRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(activity, new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(Task<Void> task) {
                        //  progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            // task successful. Activate the fetched data
                            mRemoteConfig.activateFetched();
                            //update views?
                            //updateViews();
                        } else {
                            //task failed
                        }

                        if (view instanceof TextInputLayout) {
                            String text, text_color, background;
                            Log.e("TextInputLayout", "TextInputLayout");
                            text = mRemoteConfig.getString(keyParameter + "_text");
                            text_color = mRemoteConfig.getString(keyParameter + "_text_color");
                            background = mRemoteConfig.getString(keyParameter + "_background");

                            Log.e("text", text);
                            Log.e("text_color", text_color);
                            Log.e("background", background);

                            if (!TextUtils.isEmpty(text))
                                ((TextInputLayout) view).setHint(text);

                            if (!TextUtils.isEmpty(text_color)) {

                                if (text_color.startsWith("#")) {
                                    setInputTextLayoutColor(((TextInputLayout) view), Color.parseColor(text_color));
                                }
                            }

                            if (!TextUtils.isEmpty(background)) {
                                if (background.startsWith("http")) {
                                    new LoadBackgroundImageUrl(view, false).execute((mRemoteConfig.getString(keyParameter + "_background")));
                                } else if (background.startsWith("#")) {
                                    ((TextInputLayout) view).setBackgroundColor(Color.parseColor(mRemoteConfig.getString(keyParameter + "_background")));
                                }
                            }

                        } else if (view instanceof RadioGroup) {
                            Log.e("RadioGroup", "RadioGroup");
                            String background;
                            background = mRemoteConfig.getString(keyParameter + "_background");
                            Log.e("RadioGroup background", background);

                            if (!TextUtils.isEmpty(background)) {
                                if (background.startsWith("http")) {
                                    new LoadBackgroundImageUrl(view, false).execute((mRemoteConfig.getString(keyParameter + "_background")));
                                } else if (background.startsWith("#")) {
                                    ((RadioGroup) view).setBackgroundColor(Color.parseColor(mRemoteConfig.getString(keyParameter + "_background")));
                                }
                            }
                        } else if (view instanceof LinearLayout) {
                            Log.e("LinearLayout", "LinearLayout");




                            /*if (!TextUtils.isEmpty(mRemoteConfig.getString(keyParameter + "_image"))) {
                                int res = Integer.parseInt(mRemoteConfig.getString(keyParameter + "_image"));

                                Log.e("Res", "" + res);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && TextUtils.isEmpty(imageUrl)) {
                                    switch (res) {
                                        case 0:
                                            view.setBackground(activity.getResources().getDrawable(R.drawable.tulips));
                                            break;
                                        case 1:
                                            view.setBackground(activity.getResources().getDrawable(R.drawable.lighthouse));
                                            break;
                                        case 11:
                                            Log.e("png", mRemoteConfig.getString("image_url"));
                                            new LoadBackgroundImageUrl(view,false).execute(mRemoteConfig.getString("image_url"));
                                            break;

                                    }

                                } else {
                                    new LoadBackgroundImageUrl(view,false).execute(imageUrl);
                                }
                            }*/
                            /*for (int i = 0; i < ((LinearLayout) view).getChildCount(); i++) {

                                View view1 = ((LinearLayout) view).getChildAt(i);
                                if (view1 instanceof TextInputLayout) {
                                    Log.e("inside lin" + i, "TextInputLayout");

                                } else if (view1 instanceof RadioGroup) {

                                    Log.e("inside lin " + i, "RadioGroup");

                                } else if (view1 instanceof LinearLayout) {

                                    Log.e("inside lin " + i, "LinearLayout");

                                } else if (view1 instanceof RelativeLayout) {

                                    Log.e("inside lin " + i, "RelativeLayout");

                                } else if (view1 instanceof RadioButton) {

                                    Log.e("inside lin " + i, "RadioButton");

                                } else if (view1 instanceof Button) {

                                    Log.e("inside lin " + i, "Button");

                                } else if (view1 instanceof EditText) {

                                    Log.e("inside lin " + i, "EditText");

                                } else if (view1 instanceof TextView) {

                                    Log.e("inside lin " + i, "TextView");

                                } else if (view1 instanceof ImageView) {

                                    Log.e("inside lin " + i, "ImageView");

                                } else if (view1 instanceof TabLayout) {

                                    Log.e("inside lin " + i, "TabLayout");

                                }

                            }*/
                          /*  View view2=((LinearLayout)view).getChildAt(1);
                            ((LinearLayout)view).removeViewAt(1);
                            ((LinearLayout)view).addView(view2,2);*/

                            String background;
                            background = mRemoteConfig.getString(keyParameter + "_background");

                            if (!TextUtils.isEmpty(background)) {
                                if (background.startsWith("http")) {
                                    new LoadBackgroundImageUrl(view, false).execute((mRemoteConfig.getString(keyParameter + "_background")));
                                } else if (background.startsWith("#")) {
                                    ((LinearLayout) view).setBackgroundColor(Color.parseColor(mRemoteConfig.getString(keyParameter + "_background")));
                                }
                            }

                        } else if (view instanceof RelativeLayout) {
                            String background;
                            background = mRemoteConfig.getString(keyParameter + "_background");

                            if (!TextUtils.isEmpty(background)) {
                                if (background.startsWith("http")) {
                                    new LoadBackgroundImageUrl(view, false).execute((mRemoteConfig.getString(keyParameter + "_background")));
                                } else if (background.startsWith("#")) {
                                    ((RelativeLayout) view).setBackgroundColor(Color.parseColor(mRemoteConfig.getString(keyParameter + "_background")));
                                }
                            }

                           /* for (int i = 0; i < ((RelativeLayout) view).getChildCount(); i++) {

                                View view1 = ((RelativeLayout) view).getChildAt(i);
                                if (view1 instanceof TextInputLayout) {
                                    Log.e("inside rel " + i, "TextInputLayout");

                                } else if (view1 instanceof RadioGroup) {

                                    Log.e("inside rel " + i, "RadioGroup");

                                } else if (view1 instanceof LinearLayout) {

                                    Log.e("inside rel " + i, "LinearLayout");

                                } else if (view1 instanceof RelativeLayout) {

                                    Log.e("inside rel " + i, "RelativeLayout");

                                } else if (view1 instanceof RadioButton) {

                                    Log.e("inside rel " + i, "RadioButton");

                                } else if (view1 instanceof Button) {

                                    Log.e("inside rel " + i, "Button");

                                } else if (view1 instanceof EditText) {

                                    Log.e("inside rel " + i, "EditText");

                                } else if (view1 instanceof TextView) {

                                    Log.e("inside rel " + i, "TextView");

                                } else if (view1 instanceof ImageView) {

                                    Log.e("inside rel " + i, "ImageView");

                                } else if (view1 instanceof TabLayout) {

                                    Log.e("inside rel " + i, "TabLayout");

                                }

                            }*/


                        } else if (view instanceof CheckBox) {
                            Log.e("CheckBox", "CheckBox");
                            String text, text_color, text_size, background;
                            text = mRemoteConfig.getString(keyParameter + "_text");
                            text_color = mRemoteConfig.getString(keyParameter + "_text_color");
                            text_size = mRemoteConfig.getString(keyParameter + "_text_size");
                            background = mRemoteConfig.getString(keyParameter + "_background");
                            Log.e("text", text);
                            Log.e("text_color", text_color);
                            Log.e("text_size", text_size);
                            Log.e("background", background);
                            if (!TextUtils.isEmpty(text)) {
                                ((CheckBox) view).setText(text);
                            }

                            if (!TextUtils.isEmpty(text_size)) {
                                if (isNumeric(text_size)) {
                                    int size = Integer.parseInt(text_size);
                                    ((CheckBox) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
                                }
                            }

                            if (!TextUtils.isEmpty(text_color)) {
                                if (text_color.startsWith("#")) {
                                    ((CheckBox) view).setTextColor(Color.parseColor(text_color));
                                }
                            }
                            if (!TextUtils.isEmpty(background)) {
                                if (background.startsWith("http")) {
                                    new LoadBackgroundImageUrl(view, false).execute((mRemoteConfig.getString(keyParameter + "_background")));
                                } else if (background.startsWith("#")) {
                                    ((CheckBox) view).setBackgroundColor(Color.parseColor(mRemoteConfig.getString(keyParameter + "_background")));
                                }
                            }


                        } else if (view instanceof RadioButton) {
                            Log.e("RadioButton", "RadioButton");
                            String text, text_color, background, text_size;
                            text = mRemoteConfig.getString(keyParameter + "_text");
                            text_color = mRemoteConfig.getString(keyParameter + "_text_color");
                            background = mRemoteConfig.getString(keyParameter + "_background");
                            text_size = mRemoteConfig.getString(keyParameter + "_text_size");
                            Log.e("Radio text", text);
                            Log.e("Radio Color", text_color);
                            Log.e("Radio bacground", background);
                            Log.e("Radio text Size", text_size);

                            if (!TextUtils.isEmpty(text)) {
                                ((RadioButton) view).setText(text);
                            }

                            if (!TextUtils.isEmpty(text_color)) {
                                if (text_color.startsWith("#")) {
                                    ((RadioButton) view).setTextColor(Color.parseColor(text_color));
                                }
                            }

                            if (!TextUtils.isEmpty(background)) {
                                if (background.startsWith("http")) {
                                    new LoadBackgroundImageUrl(view, false).execute((mRemoteConfig.getString(keyParameter + "_background")));
                                } else if (background.startsWith("#")) {
                                    ((RadioButton) view).setBackgroundColor(Color.parseColor(mRemoteConfig.getString(keyParameter + "_background")));
                                }
                            }
                            if (!TextUtils.isEmpty(text_size)) {
                                if (isNumeric(text_size)) {
                                    int size = Integer.parseInt(text_size);
                                    ((RadioButton) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
                                }
                            }
                        } else if (view instanceof Button) {

                            String text, text_color, text_size, background, style;
                            text = mRemoteConfig.getString(keyParameter + "_text");
                            text_color = mRemoteConfig.getString(keyParameter + "_text_color");
                            text_size = mRemoteConfig.getString(keyParameter + "_text_size");
                            style = mRemoteConfig.getString(keyParameter + "_text_style");
                            background = mRemoteConfig.getString(keyParameter + "_background");
                            Log.e("Button", "Button");
                            Log.e("text", text);
                            Log.e("text_color", text_color);
                            Log.e("text_size", text_size);
                            Log.e("style ", style);
                            Log.e("background", background);


                            if (!TextUtils.isEmpty(background)) {
                                if (background.startsWith("http")) {
                                    new LoadBackgroundImageUrl(view, false).execute((mRemoteConfig.getString(keyParameter + "_background")));
                                } else if (background.startsWith("#")) {
                                    ((Button) view).setBackgroundColor(Color.parseColor(mRemoteConfig.getString(keyParameter + "_background")));
                                }
                            }

                            if (!TextUtils.isEmpty(text_size)) {
                                if (isNumeric(text_size)) {
                                    int size = Integer.parseInt(text_size);
                                    ((Button) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
                                }
                            }

                            if (!TextUtils.isEmpty(text)) {
                                ((Button) view).setText(text);
                            }
                            if (!TextUtils.isEmpty(text_color)) {
                                if (text_color.startsWith("#")) {
                                    ((Button) view).setTextColor(Color.parseColor(text_color));
                                }
                            }


                            if (!TextUtils.isEmpty(style)) {
                                if (isNumeric(style)) {
                                    int textStyle;
                                    textStyle = Integer.parseInt(mRemoteConfig.getString(keyParameter + "_text_style")) < 4 ?
                                            Integer.parseInt(mRemoteConfig.getString(keyParameter + "_text_style")) : 0;
                                    switch (textStyle) {
                                        case 0:
                                            Log.e("textStyle", "NORMAL");
                                            ((Button) view).setTypeface(null, Typeface.NORMAL);
                                            break;
                                        case 1:
                                            Log.e("textStyle", "BOLD");
                                            ((Button) view).setTypeface(null, Typeface.BOLD);
                                            break;
                                        case 2:
                                            Log.e("textStyle", "ITALIC");
                                            ((Button) view).setTypeface(null, Typeface.ITALIC);
                                            break;
                                        case 3:
                                            Log.e("textStyle", "BOLD_ITALIC");
                                            ((Button) view).setTypeface(null, Typeface.BOLD_ITALIC);
                                            break;
                                    }
                                }

                            }
                        } else if (view instanceof EditText) {
                            Log.e("EditText", "Editext");
                            String hint_text, hint_text_color, background, text, text_color, text_size;
                            hint_text = mRemoteConfig.getString(keyParameter + "_hint_text");
                            hint_text_color = mRemoteConfig.getString(keyParameter + "_hint_text_color");
                            background = mRemoteConfig.getString(keyParameter + "_background");
                            text = mRemoteConfig.getString(keyParameter + "_text");
                            text_color = mRemoteConfig.getString(keyParameter + "_text_color");
                            text_size = mRemoteConfig.getString(keyParameter + "_text_size");


                            Log.e("hint_text", hint_text);
                            Log.e("hint_text_color", hint_text_color);
                            Log.e("background", background);
                            Log.e("text", text);
                            Log.e("text_color", text_color);
                            Log.e("text_size", text_size);

                            if (!TextUtils.isEmpty(hint_text)) {
                                ((EditText) view).setHint(hint_text);
                            }
                            if (!TextUtils.isEmpty(hint_text_color)) {
                                if (hint_text_color.startsWith("#")) {
                                    ((EditText) view).setHintTextColor(Color.parseColor(hint_text_color));
                                }
                            }
                            if (!TextUtils.isEmpty(background)) {
                                if (background.startsWith("http")) {
                                    new LoadBackgroundImageUrl(view, false).execute(background);
                                } else if (background.startsWith("#")) {
                                    ((EditText) view).setBackgroundColor(Color.parseColor(background));
                                }
                            }
                            if (!TextUtils.isEmpty(text)) {
                                ((EditText) view).setText(text);
                            }
                            if (!TextUtils.isEmpty(text_color)) {
                                if (text_color.startsWith("#")) {
                                    ((EditText) view).setTextColor(Color.parseColor(text_color));
                                }
                            }

                            if (!TextUtils.isEmpty(text_size)) {
                                if (isNumeric(text_size)) {
                                    int size = Integer.parseInt(text_size);
                                    ((EditText) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
                                }
                            }


                        } else if (view instanceof TextView) {

                            Log.e("TextVIew", "TExtVIew");
                            Log.e("key", keyParameter + "_text");
                            String text, text_color, text_size, background, style;
                            text = mRemoteConfig.getString(keyParameter + "_text");
                            text_color = mRemoteConfig.getString(keyParameter + "_text_color");
                            text_size = mRemoteConfig.getString(keyParameter + "_text_size");
                            background = mRemoteConfig.getString(keyParameter + "_background");
                            style = mRemoteConfig.getString(keyParameter + "_style");

                            Log.e("text", text);
                            Log.e("text_color", text_color);
                            Log.e("text_size", text_size);
                            Log.e("background", background);
                            Log.e("style", style);

                            if (!TextUtils.isEmpty(text)) {
                                ((TextView) view).setText(text);
                            }
                            if (!TextUtils.isEmpty(text_color)) {
                                if (text_color.startsWith("#")) {
                                    ((TextView) view).setTextColor(Color.parseColor(text_color));
                                }
                            }

                            if (!TextUtils.isEmpty(text_size)) {
                                if (isNumeric(text_size)) {
                                    int size = Integer.parseInt(text_size);
                                    ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
                                }
                            }

                            if (!TextUtils.isEmpty(background)) {
                                if (background.startsWith("http")) {
                                    new LoadBackgroundImageUrl(view, false).execute(background);
                                } else if (background.startsWith("#")) {
                                    ((TextView) view).setBackgroundColor(Color.parseColor(background));
                                }
                            }

                            if (!TextUtils.isEmpty(style)) {
                                if (isNumeric(style)) {
                                    int textStyle;
                                    textStyle = Integer.parseInt(style) < 4 ?
                                            Integer.parseInt(style) : 0;
                                    switch (textStyle) {
                                        case 0:
                                            Log.e("textStyle", "NORMAL");
                                            ((TextView) view).setTypeface(null, Typeface.NORMAL);
                                            break;
                                        case 1:
                                            Log.e("textStyle", "BOLD");
                                            ((TextView) view).setTypeface(null, Typeface.BOLD);
                                            break;
                                        case 2:
                                            Log.e("textStyle", "ITALIC");
                                            ((TextView) view).setTypeface(null, Typeface.ITALIC);
                                            break;
                                        case 3:
                                            Log.e("textStyle", "BOLD_ITALIC");
                                            ((TextView) view).setTypeface(null, Typeface.BOLD_ITALIC);
                                            break;
                                    }
                                }
                            }
                        } else if (view instanceof ImageView) {
                            Log.e("ImageView", "ImageView");
                            String background, resource;
                            background = mRemoteConfig.getString(keyParameter + "_background");
                            resource = mRemoteConfig.getString(keyParameter + "_resource");
                            Log.e("background", background);
                            Log.e("resource", resource);
                            if (!TextUtils.isEmpty(background)) {
                                if (background.startsWith("http")) {
                                    new LoadBackgroundImageUrl(view, false).execute(background);
                                } else if (background.startsWith("#")) {
                                    ((ImageView) view).setBackgroundColor(Color.parseColor(background));
                                }
                            }

                            if (!TextUtils.isEmpty(resource)) {
                                if (resource.startsWith("http")) {
                                    new LoadBackgroundImageUrl(view, true).execute(resource);
                                } else if (resource.startsWith("#")) {
                                    Bitmap image = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
                                    image.eraseColor(Color.parseColor(resource));
                                    ((ImageView) view).setImageBitmap(image);
                                }
                            }


                        } else if (view instanceof FrameLayout) {
                            Log.e("FrameLayout", "FrameLayout");
                            String background;
                            background = mRemoteConfig.getString(keyParameter + "_background");
                            Log.e("FrameLayout back", background);
                            if (!TextUtils.isEmpty(background)) {
                                if (background.startsWith("http")) {
                                    new LoadBackgroundImageUrl(view, false).execute((mRemoteConfig.getString(keyParameter + "_background")));
                                } else if (background.startsWith("#")) {
                                    ((FrameLayout) view).setBackgroundColor(Color.parseColor(mRemoteConfig.getString(keyParameter + "_background")));
                                }
                            }
                        } else {
                            Log.e("View", "View");
                            String background;
                            background = mRemoteConfig.getString(keyParameter + "_background");
                            Log.e("View", background);

                            if (!TextUtils.isEmpty(background)) {
                                if (background.startsWith("http")) {
                                    new LoadBackgroundImageUrl(view, false).execute((mRemoteConfig.getString(keyParameter + "_background")));
                                } else if (background.startsWith("#")) {
                                    view.setBackgroundColor(Color.parseColor(mRemoteConfig.getString(keyParameter + "_background")));
                                }
                            }
                        }
                    }

                });
    }

    public static void setInputTextLayoutColor(TextInputLayout editText, @ColorInt int color) {
        TextInputLayout til = editText;
        try {
            Field fDefaultTextColor = TextInputLayout.class.getDeclaredField("mDefaultTextColor");
            fDefaultTextColor.setAccessible(true);
            fDefaultTextColor.set(til, new ColorStateList(new int[][]{{0}}, new int[]{color}));

            Field fFocusedTextColor = TextInputLayout.class.getDeclaredField("mFocusedTextColor");
            fFocusedTextColor.setAccessible(true);
            fFocusedTextColor.set(til, new ColorStateList(new int[][]{{0}}, new int[]{color}));

            Field fDefaultTextSize = TextInputLayout.class.getDeclaredField("mTmpPaint");
            fDefaultTextSize.setAccessible(true);

            Paint paint = new Paint();
            paint.setTypeface(Typeface.DEFAULT);
            paint.setTextSize(500);
            fDefaultTextSize.set(til, paint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.e("Shread","Shred");
    }


    public static class LoadBackgroundImageUrl extends AsyncTask<String, Void, Bitmap> {


        View view;
        boolean isImageViewResource;

        public LoadBackgroundImageUrl(View view, boolean isImageViewResource) {
            this.view = view;
            this.isImageViewResource = isImageViewResource;
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

            if (isImageViewResource) {
                ((ImageView) view).setImageBitmap(result);
            } else {
                BitmapDrawable bitmapDrawable = new BitmapDrawable(result);
                this.view.setBackground(bitmapDrawable);
            }

        }
    }


    public static boolean isNumeric(String str) {
        try {
            int d = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }




    public static void showInAppMessagingDialog(Activity activity){

        LinearLayout linearLayout=new LinearLayout(activity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ImageView imageView=new ImageView(activity);
        LinearLayout.LayoutParams ImgLayoutParam=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        imageView.setImageResource(R.mipmap.ic_launcher);
        linearLayout.addView(imageView,ImgLayoutParam);
        activity.setContentView(linearLayout,linLayoutParam);


        //  WindowManager.LayoutParams dialogParams=new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public static void showInAppMessaging(Activity activity, String text){
        Dialog dialog=new Dialog(activity);

        LinearLayout linearLayout=new LinearLayout(activity);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(CENTER);

        ImageView imageView=new ImageView(activity);

        TextView textView=new TextView(activity);
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setGravity(CENTER);
        TableLayout.LayoutParams tvLayoutParams=new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 0, 1f);
        Resources r = activity.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                80,
                r.getDisplayMetrics()
        );
        Log.e("PX",""+px);
        tvLayoutParams.setMargins(0,px,0,px);
        textView.setLayoutParams(tvLayoutParams);

        Button button=new Button(activity);
        button.setAllCaps(false);
        button.setText("Ok");
        button.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ViewGroup.LayoutParams ImgLayoutParam=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

      //  new LoadBackgroundImageUrl(linearLayout,false).execute("https://firebasestorage.googleapis.com/v0/b/fir-practice-c5ef6.appspot.com/o/180UFGf7yIREwdS2fQJFn7U8vDx2?alt=media&token=a42b8e3e-dc3f-4e30-a565-69a8ee39659e");
       // linearLayout.setBackgroundColor(Color.parseColor("#9999ff"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            linearLayout.setBackground(activity.getResources().getDrawable(R.drawable.tulips));
        }
        linearLayout.addView(textView);
        linearLayout.addView(button);

        dialog.setContentView(linearLayout);

        /*LinearLayout activity_check = new LinearLayout(activity);
        activity_check.setId(R.id.activity_check);

        activity_check.setGravity(CENTER);
        activity_check.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layout_97 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layout_97.width = LinearLayout.LayoutParams.MATCH_PARENT;
        layout_97.height = LinearLayout.LayoutParams.MATCH_PARENT;
        activity_check.setLayoutParams(layout_97);

        TextView textView_237 = new TextView(activity);
        textView_237.setText("My Text");
        textView_237.setGravity(CENTER);
        ViewGroup.LayoutParams layout_647 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layout_647.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layout_647.height = ViewGroup.LayoutParams.WRAP_CONTENT;
      //  layout_647.weight = 1;
        textView_237.setLayoutParams(layout_647);
        activity_check.addView(textView_237);

        Button button_966 = new Button(activity);
        button_966.setText("ok");
        ViewGroup.LayoutParams layout_611 = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layout_611.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layout_611.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        button_966.setLayoutParams(layout_611);
        activity_check.addView(button_966);
        dialog.setContentView(activity_check);*/
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

}
