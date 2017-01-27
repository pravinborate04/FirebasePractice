package com.firebasepractice.pravin103082.firebasepractice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebasepractice.pravin103082.firebasepractice.Utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mastek.appengage.MA;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    EditText edtPasword, edtUserName;
    Button btnLogin, btnSignup;
    private FirebaseAuth mAuth;
    TextInputLayout txtInputLayoutEmail,txtInputLayoutPassword;
    RadioGroup radioGrp;
    RadioButton radioOne,radioTwo,radioThree;
    TextView txtHead;
    CheckBox check;
    LinearLayout activity_main;
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(App.getInstance().getUser()!=null){
            String userID=App.getInstance().getUser();
            if(!userID.equals("0")){
                startActivity(new Intent(LoginActivity.this, HomeActivity.class).setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK ));

            }
        }

        check=(CheckBox)findViewById(R.id.check);
        txtHead=(TextView)findViewById(R.id.txtHead);

        radioGrp=(RadioGroup)findViewById(R.id.radioGrp);

        radioOne=(RadioButton)findViewById(R.id.radioOne);
        radioTwo=(RadioButton)findViewById(R.id.radioTwo);
        radioThree=(RadioButton)findViewById(R.id.radioThree);

        mAuth = App.getmAuth();
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtPasword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button) findViewById(R.id.btnSignup);
        txtInputLayoutEmail=(TextInputLayout)findViewById(R.id.txtInputLayoutEmail);
        txtInputLayoutPassword=(TextInputLayout)findViewById(R.id.txtInputLayoutPassword);
        activity_main=(LinearLayout)findViewById(R.id.activity_main);


      //  Log.e("COUNT",activity_main.getChildCount()+"");
    /*    for(int i=0;i<activity_main.getChildCount();i++){

            View view=activity_main.getChildAt(i);
            if(view instanceof TextInputLayout){
                Log.e("TextInputLayout", "TextInputLayout");
            }
            else if (view instanceof LinearLayout) {
                Log.e("LinearLayout","LinearLayout");
            } else if (view instanceof RelativeLayout) {
                Log.e("RelativeLayout","RelativeLayout");

            } else if (view instanceof Button) {
                Log.e("Button","Button");

            } else if (view instanceof EditText) {
                Log.e("EditText", "Editext");
            } else if (view instanceof TextView) {
                Log.e("TextVIew", "TExtVIew");
            }



        }*/
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.e("validate ", validate() + "");
                if (!validate()) {
                    return;
                }
                mAuth.signInWithEmailAndPassword(edtUserName.getText().toString(), edtPasword.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Successfully Auth",
                                            Toast.LENGTH_SHORT).show();
                                    App.getInstance().setUser(mAuth.getCurrentUser().getUid());
                                    MA.setCustomAppUser(edtUserName.getText().toString()+""+mAuth.getCurrentUser().getUid());
                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));
                                } else {
                                    Toast.makeText(LoginActivity.this, "Failed Auth",
                                            Toast.LENGTH_SHORT).show();
                                    btnSignup.setVisibility(View.VISIBLE);

                                }
                            }
                        });
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                    return;
                }

                mAuth.createUserWithEmailAndPassword(edtUserName.getText().toString(), edtPasword.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Successfully Created",
                                            Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK));

                                } else {
                                    Toast.makeText(LoginActivity.this, "Failed",
                                            Toast.LENGTH_SHORT).show();
                                    Log.e("Failed", task.getException().getMessage());
                                }
                            }
                        });
            }
        });

        //Utils.forAbTesting(this,txtHead,"head","");
        //Utils.forAbTesting(this,btnLogin,"login","LOGIN");
      //  Utils.forAbTesting(this,btnSignup,"signup","SIGN UP");
       // Utils.forAbTesting(this,edtUserName,"username","");


        Utils.forAbTesting(this,txtInputLayoutEmail,"email_hint","Email");
      //  Utils.forAbTesting(this,txtInputLayoutPassword,"password_hint","Password");
      //  Utils.forAbTesting(this,activity_main,"background","0");
       // Utils.forAbTesting(this,radioGrp,"background","0");
      //  Utils.forAbTesting(this,radioOne,"radio_one","0");
       // Utils.forAbTesting(this,radioTwo,"radio_two","0");

        //Utils.forAbTesting(this,check,"check","");
    }


    public boolean validate() {
        if (TextUtils.isEmpty(edtUserName.getText().toString())) {
            edtUserName.setError("Please provide Email address");
            edtUserName.requestFocus();
            return false;
        }
        if (!checkEmail(edtUserName.getText().toString())) {
            edtUserName.setError("Please provide proper Email format");
            edtUserName.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edtPasword.getText().toString())) {
            edtPasword.setError("Please provide Password");
            edtPasword.requestFocus();
            return false;
        }
        if (edtPasword.getText().toString().length() < 6) {
            edtPasword.setError("Password should be at least 6 characters");
            edtPasword.requestFocus();
            return false;
        }

        return true;


    }

    private boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (mAuth.getCurrentUser() != null)
            Log.e("Current user", mAuth.getCurrentUser().getEmail());
        mAuth.signOut();
    }
}
