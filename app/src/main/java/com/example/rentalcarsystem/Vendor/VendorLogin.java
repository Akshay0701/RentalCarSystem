package com.example.rentalcarsystem.Vendor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.rentalcarsystem.R;
import com.example.rentalcarsystem.User.UserLogin;
import com.example.rentalcarsystem.User.UserRegister;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class VendorLogin extends AppCompatActivity {

    Button user_login_signIn,gotoRegister;
    TextView user_login_password,user_login_email;

    LinearLayout linearLayout;
    LottieAnimationView loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_login);
        user_login_signIn=findViewById(R.id.user_login_signIn);
        user_login_password=findViewById(R.id.user_login_password);
        user_login_email=findViewById(R.id.user_login_email);
        loading=findViewById(R.id.loading);
        linearLayout=findViewById(R.id.linearLayout);
        gotoRegister=findViewById(R.id.gotoRegister);
        loading.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);


        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VendorLogin.this, VendorRegister.class));
            }
        });
        user_login_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sign
                String email,password;
                email=user_login_email.getText().toString();
                password=user_login_password.getText().toString();
                Toast.makeText(VendorLogin.this, "sadas", Toast.LENGTH_SHORT).show();
                if(!email.equals("")||!password.equals("")){
                    SharedPreferences.Editor editor;
                    editor= PreferenceManager.getDefaultSharedPreferences(VendorLogin.this).edit();
                    editor.putString("vendor_username", email.trim());
                    editor.putString("vendor_password", password.trim());
                    editor.apply();
                    signIn(email,password);
                }
            }
        });
    }

    private void signIn(String email, String password) {
        final ProgressDialog progressDialog;
        //  progressDialog=new ProgressDialog(this,R.style.Theme_AppCompat_DayNight_DarkActionBar);
        // progressDialog.setMessage("Logging...");
        //  progressDialog.show();

        //to show loading screen
        loading.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);

        final FirebaseAuth mAuth;
        mAuth= FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //    progressDialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information

                            loading.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(VendorLogin.this, VendorDashBoard.class));
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            //    progressDialog.dismiss();

                            loading.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                            Toast.makeText(VendorLogin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // progressDialog.dismiss();
                loading.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
                Toast.makeText(VendorLogin.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}