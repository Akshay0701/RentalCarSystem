package com.example.rentalcarsystem.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rentalcarsystem.Models.Products;
import com.example.rentalcarsystem.R;
import com.example.rentalcarsystem.User.ProductsDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterImageProduct extends RecyclerView.Adapter<AdapterImageProduct.MyHolder>  {

    Context context;
    List<Products> productsList;
    boolean isUser;
    String action;
    public AdapterImageProduct(Context context, List<Products> productsList) {
        this.context = context;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public AdapterImageProduct.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_image_product,parent,false);
        return new AdapterImageProduct.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterImageProduct.MyHolder holder, final int position) {
        final String vId= productsList.get(position).getpId();
        final String imageUrl= productsList.get(position).getpImageUrl();

        //setdata
        Picasso.get().load(imageUrl).placeholder(R.drawable.productlogo).into(holder.img);
        //handle click

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handle when click on user
                //goto posts pages
                    Intent yourIntent = new Intent(context, ProductsDetails.class);
                    Bundle b = new Bundle();
                    b.putSerializable("BookObject",  productsList.get(position));
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
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
        }
    }

}