package com.example.mercadocomadmin.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mercadocomadmin.Activity.Login_Activity;
import com.example.mercadocomadmin.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class Logout_Fragment extends Fragment {

    SharedPreferences sharedPreferences;
    public ProgressDialog progressDialog;

    public Logout_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_logout, container, false);

        try {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");

            progressDialog.show();

            sharedPreferences = getActivity().getSharedPreferences("data", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getContext(), Login_Activity.class);
            startActivity(intent);
            getActivity().finish();

            progressDialog.dismiss();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}
