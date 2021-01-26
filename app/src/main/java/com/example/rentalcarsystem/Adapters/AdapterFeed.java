package com.example.rentalcarsystem.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rentalcarsystem.MainActivity;
import com.example.rentalcarsystem.Models.FeedBack;
import com.example.rentalcarsystem.Models.Vendors;
import com.example.rentalcarsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterFeed extends RecyclerView.Adapter<AdapterFeed.MyHolder>  {

    Context context;
    List<FeedBack> feedBackList;
    public AdapterFeed(Context context, List<FeedBack> feedBackList) {
        this.context = context;
        this.feedBackList = feedBackList;
    }

    @NonNull
    @Override
    public AdapterFeed.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_feed,parent,false);
        return new AdapterFeed.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFeed.MyHolder holder, final int position) {
        final String vId= feedBackList.get(position).getiD();
        final String message= feedBackList.get(position).getMessage();

        //setdata
        holder.name.setText(""+message);
        holder.id.setText(""+ vId);
        //handle click

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(feedBackList.get(position));
            }
        });
    }
    private void showDialog(final FeedBack feedBack) {
        //this function is for removing book from database
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Do You Want To Delete This FeedBack");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Query fquery= FirebaseDatabase.getInstance().getReference("FeedBack")
                                .orderByChild("iD").equalTo(feedBack.getiD());
                        fquery.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot ds:dataSnapshot.getChildren()){
                                    ds.getRef().removeValue();
                                }
                                Toast.makeText(context, "Deleted ", Toast.LENGTH_SHORT).show();
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
        return feedBackList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{
        TextView name,id;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            id=itemView.findViewById(R.id.id);
        }
    }

}