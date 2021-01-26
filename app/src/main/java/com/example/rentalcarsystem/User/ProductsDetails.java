package com.example.rentalcarsystem.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rentalcarsystem.Models.OrderRequest;
import com.example.rentalcarsystem.Models.Products;
import com.example.rentalcarsystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class ProductsDetails extends AppCompatActivity {

    ImageView img;
    TextView vendorId,address,productType,outofstock,pName,price;
    EditText noofDays;
    Button user_requestBtn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String mUid,mEmail;
    private FirebaseAuth mAuth;

    LinearLayout availableBox;

    Products products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_details);
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        assert bundle != null;
        products = (Products) bundle.getSerializable("BookObject");
        vendorId=findViewById(R.id.vendorId);
        pName=findViewById(R.id.pName);
        price=findViewById(R.id.price);
        address=findViewById(R.id.address);
        productType=findViewById(R.id.productType);
        img=findViewById(R.id.img);
        noofDays=findViewById(R.id.noofDays);
        user_requestBtn=findViewById(R.id.user_requestBtn);
        availableBox=findViewById(R.id.availableBox);
        availableBox=findViewById(R.id.availableBox);
        outofstock=findViewById(R.id.outofstock);
//        imgLocation=findViewById(R.id.imgLocation);

        if(products.getpAvailable().equals("0")){
            availableBox.setVisibility(View.GONE);
            outofstock.setVisibility(View.VISIBLE);
        }

        //set
        vendorId.setText("Id : "+products.getVendorId());
        address.setText(""+ products.getpAddress());
        productType.setText("Type : "+products.getpType());
        price.setText("$ "+products.getPricePerDay());
        pName.setText(products.getpName());
        Picasso.get().load(products.getpImageUrl()).into(img);
//        //setting location img
//        Resources res = getResources();
//        String mDrawableName = "location"+book.getLocation();
//        int resID = res.getIdentifier(mDrawableName , "drawable", getPackageName());
//        Drawable drawable = res.getDrawable(resID );
//        imgLocation.setImageDrawable(drawable);

        //set girdview to show location of book
//        setlocation();//

        //init firebase
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("OrderRequest");

        user_requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numDays=noofDays.getText().toString();
                if(numDays.isEmpty()){
                    Toast.makeText(ProductsDetails.this, "Fill Number Of Days", Toast.LENGTH_SHORT).show();
                }else{
                    OrderRequest orderRequest=new OrderRequest(products.getpAddress(),products.getpAvailable(),products.getpId()
                            ,products.getpImageUrl(),products.getpName(),products.getpType(),numDays,products.getPricePerDay()
                            ,mEmail,mUid,products.getVendorId());
                    databaseReference.child(mUid+products.getpId()).setValue(orderRequest);
                    Toast.makeText(ProductsDetails.this, "Request Sended", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null) {
            mUid=mAuth.getUid();
            mEmail=mAuth.getCurrentUser().getEmail().toString();
        }else{
            startActivity(new Intent(ProductsDetails.this, UserRegister.class));
            finish();
        }
    }
}