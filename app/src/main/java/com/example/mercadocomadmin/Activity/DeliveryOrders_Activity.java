package com.example.mercadocomadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mercadocomadmin.Adapter.StatisticsAdapter;
import com.example.mercadocomadmin.Model.OrderModel;
import com.example.mercadocomadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeliveryOrders_Activity extends AppCompatActivity {

    ListView deliveryOrders_listview;
    String deliveryboyid;
    DatabaseReference databaseReference;
    List<OrderModel> mOrderModel;
    String status = "2";
    int total = 0;
    int cout = 0;
    TextView totalorders, totalprice;
    StatisticsAdapter mStatisticsAdapter;
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_orders);

        try {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");

            deliveryboyid = getIntent().getExtras().getString("deliveyboyid");

            deliveryOrders_listview = findViewById(R.id.deliveryOrders_listview);
            totalorders = findViewById(R.id.totalorders);
            totalprice = findViewById(R.id.totalprice);

            progressDialog.show();
            databaseReference = FirebaseDatabase.getInstance().getReference("order_data");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    progressDialog.dismiss();
                    mOrderModel.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        if (deliveryboyid.equals(dataSnapshot1.child("deliveryBoyId").getValue().toString())) {
                            if (status.equals(dataSnapshot1.child("status").getValue().toString())) {
                                try {

                                    int qty = 0;
                                    int price = 0;

                                    qty = qty + Integer.parseInt(dataSnapshot1.child("productQty").getValue().toString());
                                    price = price + Integer.parseInt(dataSnapshot1.child("productPrice").getValue().toString());
                                    int temp = qty * price;
                                    total = total + temp;
                                    totalprice.setText(total + "");

                                    cout = cout + 1;
                                    totalorders.setText(cout + "");

                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                                OrderModel upload = dataSnapshot1.getValue(OrderModel.class);
                                mOrderModel.add(upload);
                            }


                        }
                    }
                    mStatisticsAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressDialog.dismiss();
                    Toast.makeText(DeliveryOrders_Activity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            mOrderModel = new ArrayList<>();
            mStatisticsAdapter = new StatisticsAdapter(DeliveryOrders_Activity.this, mOrderModel);
            deliveryOrders_listview.setAdapter(mStatisticsAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
