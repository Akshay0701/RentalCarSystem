package com.example.rentalcarsystem.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.rentalcarsystem.Adapters.AdapterUsers;
import com.example.rentalcarsystem.Adapters.AdapterVendors;
import com.example.rentalcarsystem.Models.Users;
import com.example.rentalcarsystem.Models.Vendors;
import com.example.rentalcarsystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class View_Vendors extends AppCompatActivity {

    String mUID;
    AdapterVendors adapterUsers;
    List<Vendors> usersList;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager  layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__vendors);
        usersList=new ArrayList<>();
        //load recycleBook
        recyclerView =(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(View_Vendors.this);
        recyclerView.setLayoutManager(layoutManager);
        loadSearch();
    }

    private void loadSearch() {
        Toast.makeText(this, "Searching...", Toast.LENGTH_SHORT).show();
        //path
        Query ref = FirebaseDatabase.getInstance().getReference("Vendors");
        //get all data from this ref
        usersList.clear();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Vendors vendors = ds.getValue(Vendors.class);

                    usersList.add(vendors);
                }

                //adapter
                adapterUsers= new AdapterVendors(View_Vendors.this, usersList,"Vendors");
                recyclerView.setLayoutManager(new LinearLayoutManager(View_Vendors.this, LinearLayoutManager.VERTICAL, false));

                //set adapter to recycle
                recyclerView.setAdapter(adapterUsers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //     Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}