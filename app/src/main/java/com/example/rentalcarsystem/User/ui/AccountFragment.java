package com.example.rentalcarsystem.User.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.rentalcarsystem.Models.Users;
import com.example.rentalcarsystem.R;
import com.example.rentalcarsystem.User.UserRegister;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AccountFragment extends Fragment {

    EditText editText_name,editText_area,editText_phone,editText_email,editText_password;
    Button button_update;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String mUid;


    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_account, container, false);
        editText_name=view.findViewById(R.id.editText_name);
        editText_area=view.findViewById(R.id.editText_area);
        editText_phone=view.findViewById(R.id.editText_phone);
        editText_email=view.findViewById(R.id.editText_email);
        editText_password=view.findViewById(R.id.editText_password);
        button_update=view.findViewById(R.id.button_update);
        //init firebase
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Users");

        getData();
        return view;
    }

    private void getData() {
        //path
        Query ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Users user = ds.getValue(Users.class);
                    if(user.getuId().equals(mUid)){
                        editText_name.setText(user.getuName());
                        editText_phone.setText(user.getPhoneNo());
                        editText_password.setText(user.getPassword());
                        editText_email.setText(user.getEmail());
                        editText_area.setText(user.getAddress());

                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //     Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null) {
            mUid=mAuth.getUid();
        }else{
            startActivity(new Intent(getContext(), UserRegister.class));
            getActivity().finish();
        }
    }

}