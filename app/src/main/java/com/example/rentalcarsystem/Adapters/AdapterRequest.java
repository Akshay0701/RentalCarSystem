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
import com.example.rentalcarsystem.Models.FeedBack;
import com.example.rentalcarsystem.Models.OrderPlaced;
import com.example.rentalcarsystem.Models.OrderRequest;
import com.example.rentalcarsystem.Models.Products;
import com.example.rentalcarsystem.R;
import com.example.rentalcarsystem.User.ProductsDetails;
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
import com.squareup.picasso.Request;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterRequest extends RecyclerView.Adapter<AdapterRequest.MyHolder>  {

    Context context;
    List<OrderRequest> productsList;
    boolean isUser;
    int action;
    public AdapterRequest(Context context, List<OrderRequest> productsList,int action) {
        this.context = context;
        this.productsList = productsList;
        this.action=action;//for admin 1 for user 0
    }

    @NonNull
    @Override
    public AdapterRequest.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_product,parent,false);
        return new AdapterRequest.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRequest.MyHolder holder, final int position) {
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
                if(action==0){
                    Intent yourIntent = new Intent(context, ProductsDetails.class);
                    Bundle b = new Bundle();
                    OrderRequest p=productsList.get(position);
                    Products products =new Products(p.getpAddress(),p.getpAvailable(),p.getpId(),p.getpImageUrl(),p.getpName(),p.getpType(),p.getTotalPrice(),p.getVendorId(),"325235");
                    b.putSerializable("BookObject",  products);
                    yourIntent.putExtras(b); //pass bundle to your intent
                    context.startActivity(yourIntent);
                }else{
                    showDialog(productsList.get(position));
                }
            }
        });
    }
    private void showDialog(final OrderRequest book) {
        //this function is for removing book from database
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Do You Accept Order Request ?");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        final ProgressDialog pd=new ProgressDialog(context);
                        pd.setMessage("Accepting..");

                        String dueDateStr,issueDateStr;
                        //set issued Date
                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = new Date();
                        issueDateStr=dateFormat.format(date);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Calendar c = Calendar.getInstance();
                        try{
                            //Setting the date to the given date
                            c.setTime(sdf.parse(issueDateStr));
                        }catch(ParseException e){
                            e.printStackTrace();
                        }
                        //Number of Days to add
                        c.add(Calendar.DAY_OF_MONTH, Integer.parseInt(book.getRentForNumberOfDays()));
                        //Date after adding the days to the given date
                        dueDateStr = sdf.format(c.getTime());
                        OrderPlaced orderPlaced =new OrderPlaced( dueDateStr,issueDateStr,book.getpAddress(),book.getpAvailable(),book.getpId(),book.getpImageUrl(),book.getpName(),book.getpType(),book.getTotalPrice(),book.getUserEmail(),book.getUserId(),book.getVendorId());
                        FirebaseDatabase.getInstance().getReference("OrderPlaced").child(book.getUserId()+book.getpId()).setValue(orderPlaced);
                        //image
                        Query fquery= FirebaseDatabase.getInstance().getReference("OrderRequest").orderByChild("pId").equalTo(book.getpId());
                        fquery.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot ds:dataSnapshot.getChildren()){
                                    ds.getRef().removeValue();
                                }
                                Toast.makeText(context, "Accepted Book", Toast.LENGTH_SHORT).show();
                                pd.dismiss();
                                context.startActivity(new Intent(context, MainActivity.class));
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
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
