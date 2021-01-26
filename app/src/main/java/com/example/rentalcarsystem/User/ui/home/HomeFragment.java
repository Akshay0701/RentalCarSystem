package com.example.rentalcarsystem.User.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rentalcarsystem.Adapters.AdapterImageProduct;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView,recyclerView_more;
    RecyclerView.LayoutManager  layoutManager;
    TextView txtFullName;
//    RecyclerView recyclerViewTrendProduct;

    List<Products> productsList;

     List<Products> devicesList;
    AdapterImageProduct adapterImageProduct,adapterImageProductD;
    ImageView searchbtn,vehicleBtn,buildingBtn,deviceBtn,otherBtn;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        checkforuserlogin();
        productsList = new ArrayList<>();
        devicesList=new ArrayList<>();
        searchbtn =root.findViewById(R.id.searchbtn);
        vehicleBtn =root.findViewById(R.id.vehicleBtn);
        buildingBtn =root.findViewById(R.id.buildingBtn);
        deviceBtn =root.findViewById(R.id.deviceBtn);
        otherBtn =root.findViewById(R.id.otherBtn);
        //load recycleBook
        recyclerView =(RecyclerView)root.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        //load recycleBook
        recyclerView_more =(RecyclerView)root.findViewById(R.id.recyclerview_devices);
        recyclerView_more.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView_more.setLayoutManager(layoutManager);
        loadBooks();

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), SearchProducts.class);
                i.putExtra("search", "");
                startActivity(i);
            }
        });
        vehicleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), SearchProducts.class);
                i.putExtra("search", "vehicle");
                startActivity(i);
            }
        });
        buildingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), SearchProducts.class);
                i.putExtra("search", "building");
                startActivity(i);
            }
        });
        deviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), SearchProducts.class);
                i.putExtra("search", "device");
                startActivity(i);
            }
        });
        otherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), SearchProducts.class);
                i.putExtra("search", "others");
                startActivity(i);
            }
        });
        return root;
    }
    private void loadBooks() {
        //path
        Query ref = FirebaseDatabase.getInstance().getReference("Products");
        //get all data from this ref
        productsList.clear();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Products products = ds.getValue(Products.class);
//                    book.setbId(ds.getKey());
                    //adding each object
                    if(products.getpType().equals("vehicle"))
                        productsList.add(products);
                    else if(products.getpType().equals("device"))
                        devicesList.add(products);
                }

                //adapter
                 adapterImageProduct= new AdapterImageProduct(getActivity(), productsList);
                adapterImageProductD= new AdapterImageProduct(getActivity(), devicesList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                recyclerView_more.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

                //set adapter to recycle
                recyclerView.setAdapter(adapterImageProduct);
                recyclerView_more.setAdapter(adapterImageProductD);
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
            //  token=FirebaseInstanceId.getInstance().getToken();
            String mUID = user.getUid();
        }
    }
}