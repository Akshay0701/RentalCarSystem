package com.example.rentalcarsystem.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rentalcarsystem.R;

public class AdminLogin extends AppCompatActivity {

    EditText user_login_email,user_login_password;
    Button user_login_signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        user_login_email= findViewById(R.id.user_login_email);
        user_login_password=findViewById(R.id.user_login_password);
        user_login_signIn=findViewById(R.id.user_login_signIn);

        user_login_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user_login_email.getText().toString().equals("admin") && user_login_password.getText().toString().equals("admin"))
                    startActivity(new Intent(AdminLogin.this,AdminDashBoard.class));
                else
                    Toast.makeText(AdminLogin.this, "Wrong Admin Name And Password!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}