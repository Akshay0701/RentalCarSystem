package com.example.rentalcarsystem.Vendor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.rentalcarsystem.Adapters.AdapterOrderPlaced;
import com.example.rentalcarsystem.Adapters.AdapterProducts;
import com.example.rentalcarsystem.Adapters.AdapterRequest;
import com.example.rentalcarsystem.MainActivity;
import com.example.rentalcarsystem.Models.OrderPlaced;
import com.example.rentalcarsystem.Models.OrderRequest;
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

public class VendorPlacedOrders extends AppCompatActivity {
    String mUID;
    AdapterOrderPlaced adapterOrderPlaced;
    List<OrderPlaced> productsList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager  layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_placed_orders);
        checkforuserlogin();
        productsList=new ArrayList<>();
        //load recycleBook
        recyclerView =(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(VendorPlacedOrders.this);
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
                    if(products.getVendorId().equals(mUID))
                        productsList.add(products);
                }

                //adapter
                adapterOrderPlaced= new AdapterOrderPlaced(VendorPlacedOrders.this, productsList);
                recyclerView.setLayoutManager(new LinearLayoutManager(VendorPlacedOrders.this, LinearLayoutManager.VERTICAL, false));

                //set adapter to recycle
                recyclerView.setAdapter(adapterOrderPlaced);
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
            startActivity(new Intent(VendorPlacedOrders.this, MainActivity.class));
            finish();
        }
    }
}