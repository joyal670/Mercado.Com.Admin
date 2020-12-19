package com.example.mercadocomadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mercadocomadmin.Adapter.MedicalPrescriptionAdapter;
import com.example.mercadocomadmin.Interface.MedicalInterface;
import com.example.mercadocomadmin.Model.MedicalPrescriptionModel;
import com.example.mercadocomadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DeliverdPrescription_Activity extends AppCompatActivity implements MedicalInterface {

    RecyclerView DeliverdActivityRecyclerView;
    ImageView DeliverdNoOrdersImage;
    private DatabaseReference mdatabaseReference;
    List<MedicalPrescriptionModel> medicalPrescriptionModels;
    MedicalPrescriptionAdapter medicalPrescriptionAdapter;
    public ProgressDialog progressDialog;
    String deliveryStatus = "2";
    public static MedicalInterface medicalInterface;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliverd_prescription);

        try{

            DeliverdActivityRecyclerView = findViewById(R.id.DeliverdActivityRecyclerView);
            DeliverdNoOrdersImage = findViewById(R.id.DeliverdNoOrdersImage);

            progressDialog = new ProgressDialog(DeliverdPrescription_Activity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading Please Wait...");

            medicalInterface = this;

            progressDialog.show();
            mdatabaseReference = FirebaseDatabase.getInstance().getReference("medical_details");
            mdatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    progressDialog.dismiss();
                    medicalPrescriptionModels.clear();
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                    {
                        if(dataSnapshot1.getValue() == null)
                        {
                            DeliverdNoOrdersImage.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            if(deliveryStatus.equals(dataSnapshot1.child("status").getValue().toString()))
                            {
                                DeliverdNoOrdersImage.setVisibility(View.INVISIBLE);
                                MedicalPrescriptionModel model = dataSnapshot1.getValue(MedicalPrescriptionModel.class);
                                medicalPrescriptionModels.add(model);
                            }
                        }
                    }
                    medicalPrescriptionAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressDialog.dismiss();
                    Toast.makeText(DeliverdPrescription_Activity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            medicalPrescriptionModels = new ArrayList<>();
            medicalPrescriptionAdapter = new MedicalPrescriptionAdapter(DeliverdPrescription_Activity.this, medicalPrescriptionModels);
            DeliverdActivityRecyclerView.setAdapter(medicalPrescriptionAdapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void CallOption(String phone, String uploadId) {

    }

    @Override
    public void DeliveryOption(String uploadId) {

    }

    @Override
    public void ViewImage(String image) {
        try{
            LayoutInflater li = LayoutInflater.from(context);
            View viewPrescription = li.inflate(R.layout.fullscreenprescription, null);
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
            alertBuilder.setView(viewPrescription);
            alertBuilder.setCancelable(true);

            ImageView fullScreenPres;
            fullScreenPres = viewPrescription.findViewById(R.id.fullScreenPres);

            Picasso.get().load(image).into(fullScreenPres);

            final AlertDialog alertDialog = alertBuilder.create();
            alertDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}