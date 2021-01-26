package com.example.rentalcarsystem.Vendor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rentalcarsystem.MainActivity;
import com.example.rentalcarsystem.Models.Users;
import com.example.rentalcarsystem.Models.Vendors;
import com.example.rentalcarsystem.R;
import com.example.rentalcarsystem.User.UserRegister;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class VendorAddProduct extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner spinner;
    String SelectedType="";
    String vendorId,vendorPhone;
    String mUid;
    private FirebaseAuth mAuth;

    EditText addProduct_pName,addProduct_address,addProduct_availble,addProduct_Price;
    Button addBook_selectImageBtn,add_productBtn;
    ImageView selected_img;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    //image picked will be saved in this
    Uri image_rui=null;

    //permission constants
    private static final int CAMERA_REQUEST_CODE =100;
    private static final int STORAGE_REQUEST_CODE =200;


    //permission constants
    private static final int IMAGE_PICK_CAMERA_CODE =300;
    private static final int IMAGE_PICK_GALLERY_CODE=400;

    //permission array
    String[] cameraPermessions;
    String[] storagePermessions;

    //progresses bar
    ProgressDialog pd;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null) {
            mUid=mAuth.getUid();
            getData();
        }else{
            startActivity(new Intent(VendorAddProduct.this, MainActivity.class));
            finish();
        }
    }
    private void getData() {
        //path
        Query ref = FirebaseDatabase.getInstance().getReference("Vendors");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Vendors vendors = ds.getValue(Vendors.class);
                    if(vendors.getvId().equals(mUid)){
                        vendorPhone=vendors.getPhoneNo();
                        Toast.makeText(VendorAddProduct.this, ""+vendorPhone, Toast.LENGTH_SHORT).show();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_add_product);

        //init sippner
        spinner = findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        addProduct_pName=findViewById(R.id.addProduct_pName);
        addProduct_address=findViewById(R.id.addProduct_address);
        addProduct_availble=findViewById(R.id.addProduct_availble);
        addProduct_Price=findViewById(R.id.addProduct_Price);
        add_productBtn=findViewById(R.id.add_productBtn);
        selected_img=findViewById(R.id.selected_img);

        pd= new ProgressDialog(this);

        //init firebase
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Books");

        //init permissions
        cameraPermessions=new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermessions=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //image
        selected_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show image dialog
                //    Toast.makeText(AddPostActivity.this, "hey", Toast.LENGTH_SHORT).show();

                showImageDialog();
                //showDialogtoSelect();
            }
        });
        add_productBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkValidation();
            }
        });
    }
    void checkValidation(){
        String Name,Address,PricePerDay,Available;
        Name=addProduct_pName.getText().toString();
        Address=addProduct_address.getText().toString();
        PricePerDay=addProduct_Price.getText().toString();
        Available=addProduct_availble.getText().toString();
        if(Name.isEmpty()){
            Toast.makeText(this, "name is empty", Toast.LENGTH_SHORT).show();
        }
        else if(SelectedType.isEmpty()){
            Toast.makeText(this, "type is empty", Toast.LENGTH_SHORT).show();
        }
        else if(Address.isEmpty()){
            Toast.makeText(this, "Location is empty", Toast.LENGTH_SHORT).show();
        }else if(PricePerDay.isEmpty()){
            Toast.makeText(this, "Author name is empty", Toast.LENGTH_SHORT).show();
        }else if(Available.isEmpty()){
            Toast.makeText(this, "Available must be Yes or No", Toast.LENGTH_SHORT).show();
        }else if(image_rui==null){
            Toast.makeText(this, "Select Image", Toast.LENGTH_SHORT).show();
        }
        else{
            startAddingBook(Name,Address,PricePerDay,Available);
        }
    }

    private void startAddingBook(final String name, final String address, final String priceperday, final String available) {
        pd.setMessage("publishing post...");
        pd.setCancelable(false);
        pd.show();
        final String timestamp= String.valueOf(System.currentTimeMillis());
        String filePathName="Posts/"+"post_"+timestamp;


        Bitmap bitmap=((BitmapDrawable)selected_img.getDrawable()).getBitmap();

        ByteArrayOutputStream bout=new ByteArrayOutputStream();
        //image compress
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bout);
        byte[] data=bout.toByteArray();

        StorageReference ref= FirebaseStorage.getInstance().getReference().child(filePathName);


        ref.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isSuccessful());

                String downloadUri=uriTask.getResult().toString();

                if(uriTask.isSuccessful()){
                    //uri is received upload post to firebase database
                    DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Products");

                    String bId=ref.push().getKey();
                    Map<String, Object> hashMap=new HashMap<>();
                    //put info
                    hashMap.put("pName",name);
                    hashMap.put("pAddress",address);
                    hashMap.put("pAvailable",available);
                    hashMap.put("pId",bId);
                    hashMap.put("vendorId",mUid);
                    hashMap.put("vendorNo",vendorPhone);
                    hashMap.put("pType",SelectedType);
                    hashMap.put("pImageUrl",downloadUri);
                    hashMap.put("pricePerDay",priceperday);

                    ref.child(bId).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            pd.dismiss();
                            Toast.makeText(VendorAddProduct.this, "Product Uploaded", Toast.LENGTH_SHORT).show();

                            //reset view
                            selected_img.setImageURI(null);
                            image_rui=null;

                            startActivity(new Intent(VendorAddProduct.this,VendorDashBoard.class));

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(VendorAddProduct.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            pd.dismiss();

                        }
                    });

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(VendorAddProduct.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String text = adapterView.getItemAtPosition(i).toString();
            SelectedType=text;
            Toast.makeText(this, ""+text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void showImageDialog() {


        String[] options={"Camera","Gallery"};

        //dialog box
        AlertDialog.Builder builder=new AlertDialog.Builder(VendorAddProduct.this);

        builder.setTitle("Choose Action");



        Toast.makeText(this, " reached", Toast.LENGTH_SHORT).show();
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which==0){
                    //camera clicked
                    if(!checkCameraPermission()){
                        requestCameraPermission();
                    }
                    else {
                        pickFromCamera();
                    }
                }
                if(which==1){
                    //camera clicked

                    if(!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else {
                        pickFromGallery();
                    }
                }
            }
        });
        builder.create().show();
    }


    private void pickFromCamera() {

        ContentValues cv=new ContentValues();
        cv.put(MediaStore.Images.Media.TITLE,"Temp Pick");
        cv.put(MediaStore.Images.Media.DESCRIPTION,"Temp Descr");
        image_rui=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,cv);


        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,image_rui);
        startActivityForResult(intent,IMAGE_PICK_CAMERA_CODE);
    }

    private void pickFromGallery() {

        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_GALLERY_CODE);
    }

    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result;
    }


    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this,storagePermessions,STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);

        return result&&result1;
    }


    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this,cameraPermessions,CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{

                if(grantResults.length>0){
                    boolean cameraAccepted=grantResults[0]== PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted=grantResults[1]== PackageManager.PERMISSION_GRANTED;

                    if(cameraAccepted&&storageAccepted){

                        pickFromCamera();
                    }
                    else {
                        Toast.makeText(this, "camera  & gallery both permission needed", Toast.LENGTH_SHORT).show();
                    }
                }
                else{

                }
            }
            break;
            case STORAGE_REQUEST_CODE:{
                if(grantResults.length>1){
                    boolean storageAccepted=false;
                    try {
                        storageAccepted=grantResults[1]== PackageManager.PERMISSION_GRANTED;
                    }catch (ArrayIndexOutOfBoundsException e){
                        Toast.makeText(this, ""+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                    if(storageAccepted){

                        pickFromGallery();
                    }
                    else {
                        //Toast.makeText(this, "gallery both permission needed", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    boolean storageAccepted=false;
                    try {
                        storageAccepted=grantResults[0]== PackageManager.PERMISSION_GRANTED;
                    }catch (ArrayIndexOutOfBoundsException e){
                        Toast.makeText(this, ""+e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                    if(storageAccepted){

                        pickFromGallery();
                    }
                    else {
                        //Toast.makeText(this, "gallery both permission needed", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;
        }



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==IMAGE_PICK_GALLERY_CODE){
                image_rui=data.getData();

                selected_img.setImageURI(image_rui);
            }
            else if(requestCode==IMAGE_PICK_CAMERA_CODE){

                selected_img.setImageURI(image_rui);

            }
        }
    }

}