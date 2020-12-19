package com.example.mercadocomadmin.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mercadocomadmin.Activity.DeliverdPrescription_Activity;
import com.example.mercadocomadmin.Activity.OnGoingPrescription_Activity;
import com.example.mercadocomadmin.Activity.UnDeliverdPrescription_Activity;
import com.example.mercadocomadmin.R;


public class Medical_Fragment extends Fragment {

    CardView Deliverd, Undeliverd, OnGoing;

    public Medical_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medical, container, false);

        try{

            Deliverd = view.findViewById(R.id.one);
            Undeliverd  = view.findViewById(R.id.two);
            OnGoing = view.findViewById(R.id.three);

            Deliverd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(getContext(), DeliverdPrescription_Activity.class);
                    startActivity(intent);
                }
            });

            Undeliverd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(getContext(), UnDeliverdPrescription_Activity.class);
                    startActivity(intent);
                }
            });

            OnGoing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), OnGoingPrescription_Activity.class);
                    startActivity(intent);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }
}