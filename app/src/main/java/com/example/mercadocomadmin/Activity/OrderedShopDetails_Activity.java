package com.example.mercadocomadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mercadocomadmin.Adapter.ShopAdapter;
import com.example.mercadocomadmin.Model.ShopModel;
import com.example.mercadocomadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderedShopDetails_Activity extends AppCompatActivity {

    String ShopId, house, area, pincode, name, number, altnumber;
    ListView shopDataListView;
    List<ShopModel> mShopModel;
    ShopAdapter shopAdapter;
    DatabaseReference databaseReference;
    public ProgressDialog progressDialog;

    TextView current_house, current_area, current_pincode, current_cname, current_cnumber, current_caltnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered_shop_details);

        try {

            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");

            ShopId = getIntent().getExtras().getString("shopId");
            house = getIntent().getExtras().getString("house");
            area = getIntent().getExtras().getString("area");
            pincode = getIntent().getExtras().getString("pincode");
            name = getIntent().getExtras().getString("name");
            number = getIntent().getExtras().getString("number");
            altnumber = getIntent().getExtras().getString("altnumber");

            shopDataListView = findViewById(R.id.shopDataListView);
            current_house = findViewById(R.id.current_house);
            current_area = findViewById(R.id.current_area);
            current_pincode = findViewById(R.id.current_pincode);
            current_cname = findViewById(R.id.current_cname);
            current_cnumber = findViewById(R.id.current_cnumber);
            current_caltnumber = findViewById(R.id.current_caltnumber);

            current_house.setText(house);
            current_area.setText(area);
            current_pincode.setText(pincode);
            current_cname.setText(name);
            current_cnumber.setText(number);
            current_caltnumber.setText(altnumber);

            progressDialog.show();
            databaseReference = FirebaseDatabase.getInstance().getReference("shop_owner").child(ShopId).child("shop_details");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    progressDialog.dismiss();
                    mShopModel.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        ShopModel model = dataSnapshot1.getValue(ShopModel.class);
                        mShopModel.add(model);
                    }
                    shopAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressDialog.dismiss();
                    Toast.makeText(OrderedShopDetails_Activity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            mShopModel = new ArrayList<>();
            shopAdapter = new ShopAdapter(OrderedShopDetails_Activity.this, mShopModel);
            shopDataListView.setAdapter(shopAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
