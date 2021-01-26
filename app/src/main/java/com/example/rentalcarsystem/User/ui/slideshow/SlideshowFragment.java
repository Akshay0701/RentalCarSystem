package com.example.rentalcarsystem.User.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rentalcarsystem.MainActivity;
import com.example.rentalcarsystem.Models.FeedBack;
import com.example.rentalcarsystem.R;
import com.example.rentalcarsystem.User.ProductsDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    Button feedBtn;
    EditText editText;
    String mUID;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        checkforuserlogin();
        feedBtn=root.findViewById(R.id.feedBtn);
        editText=root.findViewById(R.id.editText);
        //init firebase
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("FeedBack");
        feedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editText.getText().toString().isEmpty()){
                    FeedBack feedBack=new FeedBack(mUID,editText.getText().toString());
                    databaseReference.child(mUID).setValue(feedBack);
                    Toast.makeText(getContext(), "FeedBack Sended", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Fill FeedBack", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
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