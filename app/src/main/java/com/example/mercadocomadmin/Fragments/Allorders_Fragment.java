package com.example.mercadocomadmin.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mercadocomadmin.Activity.OrderedShopDetails_Activity;
import com.example.mercadocomadmin.Adapter.AllOrdersAdapter;
import com.example.mercadocomadmin.Adapter.OrderDataAdapter;
import com.example.mercadocomadmin.Model.OrderModel;
import com.example.mercadocomadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Allorders_Fragment extends Fragment implements AllOrdersAdapter.OnitemClickListener {

    RecyclerView allorderrecycler;
    DatabaseReference databaseReference;
    SharedPreferences sharedPreferences;
    List<OrderModel> mOrderModel;
    AllOrdersAdapter mAllordersAdapter;
    public ProgressDialog progressDialog;
    String status1 = "0";
    private ValueEventListener mDBListener;

    public Allorders_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_allorders, container, false);

        try {

            progressDialog = new ProgressDialog(getContext());
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading Orders...");

            allorderrecycler = view.findViewById(R.id.allorderrecycler);

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
            allorderrecycler.setAdapter(mAllordersAdapter);
            mAllordersAdapter.setOnClickListener(Allorders_Fragment.this);
//            {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Intent intent = new Intent(getContext(), OrderedShopDetails_Activity.class);
//                    intent.putExtra("shopId", mOrderModel.get(position).getShopownerId());
//                    intent.putExtra("house", mOrderModel.get(position).getHouse());
//                    intent.putExtra("area", mOrderModel.get(position).getArea());
//                    intent.putExtra("pincode", mOrderModel.get(position).getPincode());
//                    intent.putExtra("name", mOrderModel.get(position).getContName());
//                    intent.putExtra("number", mOrderModel.get(position).getContNumber());
//                    intent.putExtra("altnumber", mOrderModel.get(position).getContAltNumber());
//                    startActivity(intent);
//                }
//            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onItemClick(int position)
    {
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            databaseReference.removeEventListener(mDBListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
