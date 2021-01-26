package com.example.rentalcarsystem.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.rentalcarsystem.R;

public class AdminDashBoard extends AppCompatActivity {
    CardView view_Users,placed_Orders,request_Order,view_Vendors,view_FeedBack,view_Products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash_board);
        view_Users=findViewById(R.id.view_Users);
        placed_Orders=findViewById(R.id.placed_Orders);
        request_Order=findViewById(R.id.request_Order);
        view_Vendors=findViewById(R.id.view_Vendors);
        view_FeedBack=findViewById(R.id.view_FeedBack);
        view_Products=findViewById(R.id.view_Products);

        view_Users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashBoard.this,View_Users.class));
            }
        });
        placed_Orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashBoard.this,View_AllPlacedOrder.class));
            }
        });
        request_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashBoard.this,View_All_RequestOrder.class));
            }
        });
        view_Vendors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashBoard.this,View_Vendors.class));
            }
        });
        view_FeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashBoard.this,View_All_FeedBack.class));
            }
        });
        view_Products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminDashBoard.this,View_All_Products.class));
            }
        });


    }
}