package com.example.rentalcarsystem.Vendor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rentalcarsystem.MainActivity;
import com.example.rentalcarsystem.Models.FeedBack;
import com.example.rentalcarsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VendorFeedBack extends AppCompatActivity {
    Button feedBtn;
    EditText editText;
    String mUID;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_feed_back);
        checkforuserlogin();
        feedBtn=findViewById(R.id.feedBtn);
        editText=findViewById(R.id.editText);
        //init firebase
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("FeedBack");
        feedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editText.getText().toString().isEmpty()){
                    FeedBack feedBack=new FeedBack(mUID,editText.getText().toString());
                    databaseReference.child(mUID).setValue(feedBack);
                    Toast.makeText(VendorFeedBack.this, "FeedBack Sended", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(VendorFeedBack.this, "Fill FeedBack", Toast.LENGTH_SHORT).show();
                }
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
            startActivity(new Intent(VendorFeedBack.this, MainActivity.class));
            finish();
        }
    }
}