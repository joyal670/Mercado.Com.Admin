package com.example.mercadocomadmin.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mercadocomadmin.Activity.ShopOrders_Activity;
import com.example.mercadocomadmin.Activity.ViewShopOwners_Activity;
import com.example.mercadocomadmin.Adapter.UserDataAdapter;
import com.example.mercadocomadmin.Model.UserDataModel;
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
public class ShopFragment extends Fragment {

    ListView shopfragmentstatisticslistview;
    DatabaseReference databaseReference;
    List<UserDataModel> mUserModel;
    UserDataAdapter userDataAdapter;
    public ProgressDialog progressDialog;

    public ShopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        try {

            progressDialog = new ProgressDialog(getContext());
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");

            shopfragmentstatisticslistview = view.findViewById(R.id.shopfragmentstatisticslistview);

            progressDialog.show();
            databaseReference = FirebaseDatabase.getInstance().getReference("shop_owner");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    progressDialog.dismiss();
                    mUserModel.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        UserDataModel model = dataSnapshot1.getValue(UserDataModel.class);
                        mUserModel.add(model);
                    }
                    userDataAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            mUserModel = new ArrayList<>();
            userDataAdapter = new UserDataAdapter(getContext(), mUserModel);
            shopfragmentstatisticslistview.setAdapter(userDataAdapter);
            shopfragmentstatisticslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getContext(), ShopOrders_Activity.class);
                    intent.putExtra("shopid", mUserModel.get(position).getUserId());
                    startActivity(intent);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
}
