package com.example.mercadocomadmin.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mercadocomadmin.Activity.ViewDeliveryBoys_Activity;
import com.example.mercadocomadmin.Activity.ViewShopOwners_Activity;
import com.example.mercadocomadmin.Activity.ViewUsers_Activity;
import com.example.mercadocomadmin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Users_Fragment extends Fragment
{
    CardView viewusers, viewshopowners, viewdeliveryboy;

    public Users_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_, container, false);

        viewusers = view.findViewById(R.id.viewusers);
        viewshopowners = view.findViewById(R.id.viewshopowners);
        viewdeliveryboy = view.findViewById(R.id.viewdeliveryboy);

        viewusers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), ViewUsers_Activity.class);
                startActivity(intent);
            }
        });

        viewshopowners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViewShopOwners_Activity.class);
                startActivity(intent);
            }
        });

        viewdeliveryboy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViewDeliveryBoys_Activity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
