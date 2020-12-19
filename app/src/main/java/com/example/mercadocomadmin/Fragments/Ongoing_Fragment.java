package com.example.mercadocomadmin.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mercadocomadmin.Activity.OrderedShopDetails_Activity;
import com.example.mercadocomadmin.Adapter.AllOrdersAdapter;
import com.example.mercadocomadmin.Model.OrderModel;
import com.example.mercadocomadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Ongoing_Fragment extends Fragment implements AllOrdersAdapter.OnitemClickListener {

    RecyclerView onGoingrecycler;
    DatabaseReference databaseReference;
    List<OrderModel> mOrderModel;
    AllOrdersAdapter mAllordersAdapter;
    public ProgressDialog progressDialog;
    String status1 = "1";

    public Ongoing_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ongoing, container, false);

        try{

            progressDialog = new ProgressDialog(getContext());
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading Orders...");

            onGoingrecycler = view.findViewById(R.id.onGoingrecycler);

            progressDialog.show();
            databaseReference = FirebaseDatabase.getInstance().getReference("order_data");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    progressDialog.dismiss();
                    mOrderModel.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    {
                        if(status1.equals(dataSnapshot1.child("status").getValue().toString()))
                        {
                            OrderModel upload = dataSnapshot1.getValue(OrderModel.class);
                            mOrderModel.add(upload);
                        }

                    }
                    mAllordersAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            mOrderModel = new ArrayList<>();
            mAllordersAdapter = new AllOrdersAdapter(getContext(), mOrderModel);
            onGoingrecycler.setAdapter(mAllordersAdapter);
            mAllordersAdapter.setOnClickListener(Ongoing_Fragment.this);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), OrderedShopDetails_Activity.class);
        intent.putExtra("shopId", mOrderModel.get(position).getShopownerId());
        intent.putExtra("house", mOrderModel.get(position).getHouse());
        intent.putExtra("area", mOrderModel.get(position).getArea());
        intent.putExtra("pincode", mOrderModel.get(position).getPincode());
        intent.putExtra("name", mOrderModel.get(position).getContName());
        intent.putExtra("number", mOrderModel.get(position).getContNumber());
        intent.putExtra("altnumber", mOrderModel.get(position).getContAltNumber());
        intent.putExtra("orderdate", mOrderModel.get(position).getOrderDate());
        intent.putExtra("status", mOrderModel.get(position).getStatus());
        startActivity(intent);
    }
}