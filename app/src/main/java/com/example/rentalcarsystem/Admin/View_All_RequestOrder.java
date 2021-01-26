package com.example.rentalcarsystem.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.rentalcarsystem.Adapters.AdapterOrderPlaced;
import com.example.rentalcarsystem.Adapters.AdapterProducts;
import com.example.rentalcarsystem.Adapters.AdapterRequest;
import com.example.rentalcarsystem.Models.OrderRequest;
import com.example.rentalcarsystem.Models.Products;
import com.example.rentalcarsystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class View_All_RequestOrder extends AppCompatActivity {

    String mUID;
    AdapterRequest adapterProduct;
    List<OrderRequest> productsList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager  layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__all__request_order);
        productsList=new ArrayList<>();
        //load recycleBook
        recyclerView =(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(View_All_RequestOrder.this);
        recyclerView.setLayoutManager(layoutManager);
        loadSearch();
    }
    private void loadSearch() {
        Toast.makeText(this, "Searching...", Toast.LENGTH_SHORT).show();
        //path
        Query ref = FirebaseDatabase.getInstance().getReference("OrderRequest");
        //get all data from this ref
        productsList.clear();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    OrderRequest products = ds.getValue(OrderRequest.class);
                    productsList.add(products);
                }
                //adapter
                adapterProduct= new AdapterRequest(View_All_RequestOrder.this, productsList,0);
                recyclerView.setLayoutManager(new LinearLayoutManager(View_All_RequestOrder.this, LinearLayoutManager.VERTICAL, false));

                //set adapter to recycle
                recyclerView.setAdapter(adapterProduct);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //     Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}