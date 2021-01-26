package com.example.rentalcarsystem.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rentalcarsystem.Models.OrderPlaced;
import com.example.rentalcarsystem.Models.OrderRequest;
import com.example.rentalcarsystem.Models.Products;
import com.example.rentalcarsystem.R;
import com.example.rentalcarsystem.User.ProductsDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterOrderPlaced extends RecyclerView.Adapter<AdapterOrderPlaced.MyHolder>  {

    Context context;
    List<OrderPlaced> productsList;
    boolean isUser;
    String action;
    public AdapterOrderPlaced(Context context, List<OrderPlaced> productsList) {
        this.context = context;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public AdapterOrderPlaced.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_product,parent,false);
        return new AdapterOrderPlaced.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOrderPlaced.MyHolder holder, final int position) {
        final String vId= productsList.get(position).getVendorId();
        final String name= productsList.get(position).getpName();
        final String address= productsList.get(position).getpAddress();
        final String price= productsList.get(position).getTotalPrice();
        final String imageUrl= productsList.get(position).getpImageUrl();

        //setdata
        Picasso.get().load(imageUrl).into(holder.img);
        holder.pName.setText(""+name);
        holder.totalPrice.setText("Total Price : "+ price);
        holder.vendorId.setText("Vendor Id : "+vId);
        holder.address.setText(address);
        //handle click

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handle when click on user
                //goto posts pages
                    Intent yourIntent = new Intent(context, ProductsDetails.class);
                    Bundle b = new Bundle();
                    OrderPlaced p=productsList.get(position);
                    Products products =new Products(p.getpAddress(),p.getpAvailable(),p.getpId(),p.getpImageUrl(),p.getpName(),p.getpType(),p.getTotalPrice(),p.getVendorId(),"325235");
                    b.putSerializable("BookObject",  products);
                    yourIntent.putExtras(b); //pass bundle to your intent
                    context.startActivity(yourIntent);
            }
        });
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
