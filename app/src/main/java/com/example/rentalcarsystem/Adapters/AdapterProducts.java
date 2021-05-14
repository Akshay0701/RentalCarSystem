package com.example.rentalcarsystem.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rentalcarsystem.MainActivity;
import com.example.rentalcarsystem.Models.Products;
import com.example.rentalcarsystem.R;
import com.example.rentalcarsystem.User.ProductsDetails;
import com.example.rentalcarsystem.Vendor.VendorUpdateProductDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterProducts extends RecyclerView.Adapter<AdapterProducts.MyHolder>  {

    Context context;
    List<Products> productsList;
    boolean isUser;
    int action;//0 view 1 update 2 remove
    public AdapterProducts(Context context, List<Products> productsList,int action) {
        this.context = context;
        this.productsList = productsList;
        this.action=action;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_product,parent,false);
        return new AdapterProducts.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        final String vId= productsList.get(position).getVendorId();
        final String name= productsList.get(position).getpName();
        final String address= productsList.get(position).getpAddress();
        final String price= productsList.get(position).getPricePerDay();
        final String imageUrl= productsList.get(position).getpImageUrl();

        //setdata
        Picasso.get().load(imageUrl).into(holder.img);
        holder.pName.setText(""+name);
        holder.totalPrice.setText("Total Price : "+ price);
        holder.vendorId.setText("Vendor Id : "+vId);
        holder.address.setText("Address : "+address);
        //handle click

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(action==0){
                       Intent yourIntent = new Intent(context, ProductsDetails.class);
                       Bundle b = new Bundle();
                       b.putSerializable("BookObject",  productsList.get(position));
                       yourIntent.putExtras(b); //pass bundle to your intent
                       context.startActivity(yourIntent);
                   }else if(action==1){
                       //update
                       Intent yourIntent = new Intent(context, VendorUpdateProductDetails.class);
                       Bundle b = new Bundle();
                       b.putSerializable("BookObject",  productsList.get(position));
                       yourIntent.putExtras(b); //pass bundle to your intent
                       context.startActivity(yourIntent);

                   }else if(action ==2){
                       //remove
                       showDialog(productsList.get(position));
                   }
                }
            });
    }
    private void showDialog(final Products book) {
        //this function is for removing book from database
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Do You Want To Delete This Product?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        final ProgressDialog pd=new ProgressDialog(context);
                        pd.setMessage("Deleting..");

                        StorageReference picRef= FirebaseStorage.getInstance().getReferenceFromUrl(book.getpImageUrl());
                        picRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                //image
                                Query fquery= FirebaseDatabase.getInstance().getReference("Products").orderByChild("pId").equalTo(book.getpId());
                                fquery.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                                            ds.getRef().removeValue();
                                        }
                                        Toast.makeText(context, "Deleted Book", Toast.LENGTH_SHORT).show();
                                        pd.dismiss();
                                        context.startActivity(new Intent(context, MainActivity.class));
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pd.dismiss();
                                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView pName,vendorId,totalPrice,address;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
            pName=itemView.findViewById(R.id.pName);
            vendorId=itemView.findViewById(R.id.vendorId);
            totalPrice=itemView.findViewById(R.id.totalPrice);
            address=itemView.findViewById(R.id.address);
        }
    }

}