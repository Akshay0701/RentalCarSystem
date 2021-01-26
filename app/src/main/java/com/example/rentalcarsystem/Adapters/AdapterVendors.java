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
import com.example.rentalcarsystem.Models.Users;
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

public class AdapterVendors extends RecyclerView.Adapter<AdapterVendors.MyHolder>  {

    Context context;
    List<Vendors> usersList;
    String action;
    public AdapterVendors(Context context, List<Vendors> usersList,String action) {
        this.context = context;
        this.usersList = usersList;
        this.action=action;
    }

    @NonNull
    @Override
    public AdapterVendors.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.row_users,parent,false);
        return new AdapterVendors.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVendors.MyHolder holder, final int position) {
        final String vId= usersList.get(position).getvId();
        final String name= usersList.get(position).getvName();
        final String address= usersList.get(position).getAddress();
        final String email= usersList.get(position).getEmail();

        //setdata
        holder.name.setText(""+name);
        holder.id.setText(""+ vId);
        holder.email.setText("Email : "+email);
        holder.address.setText("Address : "+address);
        //handle click

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, email+" "+usersList.get(position).getPassword(), Toast.LENGTH_SHORT).show();
                signIn(email,usersList.get(position).getPassword().toString());
                showDialog(usersList.get(position));
            }
        });
    }
    private void signIn(String email, String password) {
        final ProgressDialog progressDialog;
        final FirebaseAuth mAuth;
        mAuth= FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                        } else {
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // progressDialog.dismiss();
                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showDialog(final Vendors users) {
        String id=action.equals("Users")?"uId":"vId";
        //this function is for removing book from database
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Do You Want To Delete User");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        final ProgressDialog pd=new ProgressDialog(context);
                        pd.setMessage("Deleting..");
                        //image
                        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        AuthCredential credential = EmailAuthProvider
                                .getCredential(users.getEmail(), users.getPassword());

                        // Prompt the user to re-provide their sign-in credentials
                        user.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Query fquery= FirebaseDatabase.getInstance().getReference("Vendors")
                                                            .orderByChild("vId").equalTo(user.getUid());
                                                    fquery.addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            for(DataSnapshot ds:dataSnapshot.getChildren()){
                                                                ds.getRef().removeValue();
                                                            }
                                                            Toast.makeText(context, "Deleted "+action, Toast.LENGTH_SHORT).show();
                                                            pd.dismiss();
                                                            context.startActivity(new Intent(context, MainActivity.class));
                                                        }
                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                                        }
                                                    });

                                                }
                                            }
                                        });

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
        return usersList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{
        TextView name,id,email,address;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            id=itemView.findViewById(R.id.id);
            email=itemView.findViewById(R.id.email);
            address=itemView.findViewById(R.id.address);
        }
    }

}