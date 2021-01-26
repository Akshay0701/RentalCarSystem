package com.example.rentalcarsystem.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.rentalcarsystem.Adapters.AdapterOrderPlaced;
import com.example.rentalcarsystem.Adapters.AdapterProducts;
import com.example.rentalcarsystem.Models.OrderPlaced;
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

public class View_AllPlacedOrder extends AppCompatActivity {

    String mUID;
    AdapterOrderPlaced adapterProduct;
    List<OrderPlaced> productsList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager  layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__all_placed_order);
        productsList=new ArrayList<>();
        //load recycleBook
        recyclerView =(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(View_AllPlacedOrder.this);
        recyclerView.setLayoutManager(layoutManager);
        loadSearch();
    }

    private void loadSearch() {
        Toast.makeText(this, "Searching...", Toast.LENGTH_SHORT).show();
        //path
        Query ref = FirebaseDatabase.getInstance().getReference("OrderPlaced");
        //get all data from this ref
        productsList.clear();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    OrderPlaced products = ds.getValue(OrderPlaced.class);
                    productsList.add(products);
                }

                //adapter
                adapterProduct= new AdapterOrderPlaced(View_AllPlacedOrder.this, productsList);
                recyclerView.setLayoutManager(new LinearLayoutManager(View_AllPlacedOrder.this, LinearLayoutManager.VERTICAL, false));

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