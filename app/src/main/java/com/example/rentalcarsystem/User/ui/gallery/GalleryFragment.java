package com.example.rentalcarsystem.User.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.rentalcarsystem.Adapters.AdapterImageProduct;
import com.example.rentalcarsystem.Adapters.AdapterOrderPlaced;
import com.example.rentalcarsystem.Adapters.AdapterProducts;
import com.example.rentalcarsystem.Adapters.AdapterRequest;
import com.example.rentalcarsystem.MainActivity;
import com.example.rentalcarsystem.Models.OrderPlaced;
import com.example.rentalcarsystem.Models.OrderRequest;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    Button ORequestBtn,OPlacedBtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView,recyclerView1;
    RecyclerView.LayoutManager  layoutManager;
    TextView txtFullName;
//    RecyclerView recyclerViewTrendProduct;

    List<OrderRequest> requestList;

    List<OrderPlaced> placedList;
    AdapterRequest adapterProductRequest;
    AdapterOrderPlaced adapterProductPlaced;
    String mUID;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        recyclerView=root.findViewById(R.id.recyclerview);
        recyclerView1=root.findViewById(R.id.recyclerview1);
        ORequestBtn=root.findViewById(R.id.ORequestBtn);
        OPlacedBtn=root.findViewById(R.id.OPlacedBtn);
        checkforuserlogin();
        placedList = new ArrayList<>();
        requestList=new ArrayList<>();
        //load recycleBook
        recyclerView =(RecyclerView)root.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        //load recycleBook
        recyclerView1 =(RecyclerView)root.findViewById(R.id.recyclerview1);
        recyclerView1.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView1.setLayoutManager(layoutManager);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView1.setVisibility(View.GONE);

        ORequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView1.setVisibility(View.GONE);
            }
        });
        OPlacedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerView.setVisibility(View.GONE);
                recyclerView1.setVisibility(View.VISIBLE);
            }
        });

        loadRequest();
        loadPlaced();
        return root;
    }

    private void loadPlaced() {
        //path
        Query ref = FirebaseDatabase.getInstance().getReference("OrderPlaced");
        //get all data from this ref
        placedList.clear();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    OrderPlaced products = ds.getValue(OrderPlaced.class);
//                    book.setbId(ds.getKey());
                    //adding each object
                    if(products.getUserId().equals(mUID))
                        placedList.add(products);
                }

                //adapter
                adapterProductPlaced= new AdapterOrderPlaced(getActivity(), placedList);
                recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

                //set adapter to recycle
                recyclerView1.setAdapter(adapterProductPlaced);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //     Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadRequest() {
        //path
        Query ref = FirebaseDatabase.getInstance().getReference("OrderRequest");
        //get all data from this ref
        requestList.clear();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    OrderRequest products = ds.getValue(OrderRequest.class);
//                    book.setbId(ds.getKey());
                    //adding each object
                    if(products.getUserId().equals(mUID))
                        requestList.add(products);
                }

                //adapter
                adapterProductRequest= new AdapterRequest(getActivity(), requestList,0);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

                //set adapter to recycle
                recyclerView.setAdapter(adapterProductRequest);
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
            mUID = user.getUid();
        }else{
            startActivity(new Intent(getContext(), MainActivity.class));
            getActivity().finish();
        }
    }
}