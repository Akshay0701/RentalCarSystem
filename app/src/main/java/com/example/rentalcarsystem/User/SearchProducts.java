package com.example.rentalcarsystem.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rentalcarsystem.Adapters.AdapterOrderPlaced;
import com.example.rentalcarsystem.Adapters.AdapterProducts;
import com.example.rentalcarsystem.MainActivity;
import com.example.rentalcarsystem.Models.OrderPlaced;
import com.example.rentalcarsystem.Models.Products;
import com.example.rentalcarsystem.R;
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

public class SearchProducts extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager  layoutManager;
    TextView txtFullName;
    EditText searchtxt;
    Button vehicleBtn,buildingBtn,deviceBtn,otherBtn;
    ImageView searchbtn;
    AdapterProducts adapterProduct;
    List<Products> productsList;
    String mUID;
    String preSearch="";
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);
        checkforuserlogin();
        productsList=new ArrayList<>();
        searchtxt =findViewById(R.id.searchtxt);
        searchbtn =findViewById(R.id.searchbtn);
        vehicleBtn =findViewById(R.id.vehicleBtn);
        buildingBtn =findViewById(R.id.buildingBtn);
        deviceBtn =findViewById(R.id.deviceBtn);
        otherBtn =findViewById(R.id.otherBtn);
        //load recycleBook
        recyclerView =(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(SearchProducts.this);
        recyclerView.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        preSearch = intent.getExtras().getString("search");
        if(!preSearch.isEmpty())
            loadSearch(preSearch);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!searchtxt.getText().toString().isEmpty()){
                    loadSearch(searchtxt.getText().toString());
                }
            }
        });
        vehicleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadSearch("vehicle");
            }
        });
        buildingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadSearch("building");
            }
        });
        deviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadSearch("device");
            }
        });
        otherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadSearch("others");
            }
        });
    }

    private void loadSearch(String preSearch) {
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
                    if(products.getpType().toLowerCase().contains(preSearch.toLowerCase()) || products.getpName().toLowerCase().contains(preSearch.toLowerCase()))
                        productsList.add(products);
                }

                //adapter
                adapterProduct= new AdapterProducts(SearchProducts.this, productsList,0);
                recyclerView.setLayoutManager(new LinearLayoutManager(SearchProducts.this, LinearLayoutManager.VERTICAL, false));

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
            startActivity(new Intent(SearchProducts.this, MainActivity.class));
            finish();
        }
    }
}