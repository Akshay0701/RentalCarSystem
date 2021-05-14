package com.example.rentalcarsystem.User;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rentalcarsystem.MainActivity;
import com.example.rentalcarsystem.R;
import com.google.firebase.auth.FirebaseAuth;

public class logout_user extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_logout_user, container, false);

        view.findViewById(R.id.logoutBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutDialog();
            }
        });

        return view;
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(getContext()).setTitle("User Logout")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // logout
                        logout();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create()
                .show();
    }


    private void logout() {

        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
        editor.remove("username");
        editor.remove("password");
        editor.apply();
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getContext(), MainActivity.class));
        getActivity().finish();
    }

}