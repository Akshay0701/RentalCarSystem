package com.example.rentalcarsystem.Vendor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rentalcarsystem.Adapters.AdapterProducts;
import com.example.rentalcarsystem.MainActivity;
import com.example.rentalcarsystem.Models.Products;
import com.example.rentalcarsystem.R;
import com.example.rentalcarsystem.User.SearchProducts;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VendorViewProducts extends AppCompatActivity {
    TextView txt;
    String Action="";
    int process=0;
    String mUID;
    AdapterProducts adapterProduct;
    List<Products> productsList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager  layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_view_products);
        checkforuserlogin();
        txt=findViewById(R.id.txt);
        Intent intent = getIntent();
        Action = intent.getExtras().getString("action");
        if (Action.equals("View")){
            process=0;
            txt.setText("View Products");
        }
        else if(Action.equals("Remove")) {
            process = 2;
            txt.setText("Remove Products");
        }
        else  if(Action.equals("Update")) {
            process = 1;
            txt.setText("Update Products");
        }

        productsList=new ArrayList<>();
        //load recycleBook
        recyclerView =(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(VendorViewProducts.this);
        recyclerView.setLayoutManager(layoutManager);

        loadSearch();
    }

    private void loadSearch() {
        Toast.makeText(this, "Searching...", Toast.LENGTH_SHORT).show();
        //path
        Query ref = FirebaseDatabase.getInstance().getReference("Products");
        //get all data from this ref
        productsList.clear();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Products products = ds.getValue(Products.class);
                    if(products.getVendorId().equals(mUID))
                        productsList.add(products);
                    }
                //adapter
                adapterProduct= new AdapterProducts(VendorViewProducts.this, productsList,process);
                recyclerView.setLayoutManager(new LinearLayoutManager(VendorViewProducts.this, LinearLayoutManager.VERTICAL, false));
                //set adapter to recycle
                recyclerView.setAdapter(adapterProduct);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //     Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void checkforuserlogin() {
        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            mUID = user.getUid();
        }else{
            startActivity(new Intent(VendorViewProducts.this, MainActivity.class));
            finish();
        }
    }
}