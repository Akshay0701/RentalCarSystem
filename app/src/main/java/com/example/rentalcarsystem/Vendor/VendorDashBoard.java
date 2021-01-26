package com.example.rentalcarsystem.Vendor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.rentalcarsystem.Models.FeedBack;
import com.example.rentalcarsystem.R;

public class VendorDashBoard extends AppCompatActivity {
    CardView feedBack,placed_Orders,request_Order,remove_Products,update_Products,view_Products,add_Products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_dash_board);
        feedBack=findViewById(R.id.feedBack);
        placed_Orders=findViewById(R.id.placed_Orders);
        request_Order=findViewById(R.id.request_Order);
        remove_Products=findViewById(R.id.remove_Products);
        update_Products=findViewById(R.id.update_Products);
        view_Products=findViewById(R.id.view_Products);
        add_Products=findViewById(R.id.add_Products);

        feedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VendorDashBoard.this, VendorFeedBack.class));
            }
        });
        add_Products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VendorDashBoard.this, VendorAddProduct.class));
            }
        });
        view_Products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(VendorDashBoard.this, VendorViewProducts.class);
                i.putExtra("action", "View");
                startActivity(i);
            }
        });
        remove_Products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(VendorDashBoard.this, VendorViewProducts.class);
                i.putExtra("action", "Remove");
                startActivity(i);
            }
        });
        update_Products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(VendorDashBoard.this, VendorViewProducts.class);
                i.putExtra("action", "Update");
                startActivity(i);
            }
        });
        request_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(VendorDashBoard.this, VendorRequestOrder.class);
                startActivity(i);
            }
        });
        placed_Orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(VendorDashBoard.this, VendorPlacedOrders.class);
                startActivity(i);
            }
        });

    }
}