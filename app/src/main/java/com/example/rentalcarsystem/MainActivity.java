package com.example.rentalcarsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.rentalcarsystem.Admin.AdminLogin;
import com.example.rentalcarsystem.User.UserRegister;
import com.example.rentalcarsystem.Vendor.VendorRegister;

public class MainActivity extends AppCompatActivity {
    CardView user_login,vendor_login,admin_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user_login=findViewById(R.id.user_login);
        vendor_login=findViewById(R.id.vendor_login);
        admin_login=findViewById(R.id.admin_login);
        user_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UserRegister.class));
            }
        });
        vendor_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, VendorRegister.class));
            }
        });
        admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AdminLogin.class));
            }
        });
    }
}