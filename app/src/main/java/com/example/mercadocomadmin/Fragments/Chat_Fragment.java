package com.example.mercadocomadmin.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mercadocomadmin.Activity.Chat_Activity;
import com.example.mercadocomadmin.Adapter.ChatOrderAdapter;
import com.example.mercadocomadmin.Model.OrderModel;
import com.example.mercadocomadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Chat_Fragment extends Fragment {

    ListView chatOrderListView;
    public ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    SharedPreferences sharedPreferences;
    List<OrderModel> mCheckoutmodel;
    ChatOrderAdapter chatOrderAdapter;

    public Chat_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_, container, false);

        try{


        chatOrderListView = view.findViewById(R.id.chatOrderListView);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Your Orders...");

        progressDialog.show();
        databaseReference = FirebaseDatabase.getInstance().getReference("order_data");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                mCheckoutmodel.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    OrderModel model = dataSnapshot1.getValue(OrderModel.class);
                    mCheckoutmodel.add(model);
                }
                chatOrderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mCheckoutmodel = new ArrayList<>();
        chatOrderAdapter = new ChatOrderAdapter(getContext(), mCheckoutmodel);
        chatOrderListView.setAdapter(chatOrderAdapter);
        chatOrderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getContext(), Chat_Activity.class);
                intent.putExtra("orderid", mCheckoutmodel.get(position).getOrderId());
                intent.putExtra("userId", mCheckoutmodel.get(position).getUserId());
                startActivity(intent);
            }
        });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}