package com.example.mercadocomadmin.Fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mercadocomadmin.Adapter.HomeAdapter;
import com.example.mercadocomadmin.Adapter.OrderDataAdapter;
import com.example.mercadocomadmin.Model.OrderModel;
import com.example.mercadocomadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home_Fragment extends Fragment implements HomeAdapter.OnitemClickListener{

    RecyclerView myOrderListView;
    DatabaseReference databaseReference;
    SharedPreferences sharedPreferences;
    List<OrderModel> mOrderModel;
    HomeAdapter mHomeAdapter;
    public ProgressDialog progressDialog;
    private ValueEventListener mDBListener;
    TextView totalorders, totalprice;
    int count = 0;
    int total = 0;


    public Home_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        myOrderListView = view.findViewById(R.id.homerecyclerview);
        totalorders = view.findViewById(R.id.totalorders);
        totalprice = view.findViewById(R.id.totalprice);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Hang On...");

        progressDialog.show();
        final String test = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        databaseReference = FirebaseDatabase.getInstance().getReference("order_data");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                progressDialog.dismiss();
                mOrderModel.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    if (test.equals(dataSnapshot1.child("orderDeliveryDate").getValue().toString()))
                    {
                        OrderModel upload = dataSnapshot1.getValue(OrderModel.class);
                        mOrderModel.add(upload);
                            try
                            {
                                int qty = 0;
                                int price = 0;

                                qty = qty + Integer.parseInt(dataSnapshot1.child("productQty").getValue().toString());
                                price = price + Integer.parseInt(dataSnapshot1.child("productPrice").getValue().toString());
                                int temp = qty * price;
                                total = total + temp;
                                totalprice.setText(total + "");

                                count = count + 1;
                                totalorders.setText(count + "");
                            } catch (NumberFormatException e)
                            {
                                e.printStackTrace();
                            }

                        }

                }
                mHomeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mOrderModel = new ArrayList<>();
        mHomeAdapter = new HomeAdapter(getContext(), mOrderModel);
        myOrderListView.setAdapter(mHomeAdapter);

        return view;
    }

    @Override
    public void onItemClick(int position) {

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
