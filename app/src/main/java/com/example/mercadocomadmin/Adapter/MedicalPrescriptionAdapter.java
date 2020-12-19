package com.example.mercadocomadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mercadocomadmin.Activity.DeliverdPrescription_Activity;
import com.example.mercadocomadmin.Activity.OnGoingPrescription_Activity;
import com.example.mercadocomadmin.Activity.UnDeliverdPrescription_Activity;
import com.example.mercadocomadmin.Model.MedicalPrescriptionModel;
import com.example.mercadocomadmin.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MedicalPrescriptionAdapter extends RecyclerView.Adapter<MedicalPrescriptionAdapter.ImageHolder>
{
    Context context;
    List<MedicalPrescriptionModel> medicalPrescriptionModels;

    public MedicalPrescriptionAdapter(Context context, List<MedicalPrescriptionModel> medicalPrescriptionModels) {
        this.context = context;
        this.medicalPrescriptionModels = medicalPrescriptionModels;
    }

    @NonNull
    @Override
    public MedicalPrescriptionAdapter.ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.prescription, parent, false);
        return new MedicalPrescriptionAdapter.ImageHolder(view);

    }

    @Override
    public int getItemCount() {
        return medicalPrescriptionModels.size();
    }

    public class ImageHolder extends RecyclerView.ViewHolder
    {
        ImageView medicalPrsImage;
        TextView medicalPrsQty, medicalPrsStatus;
        Button medicalPrsViewImage;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);

            medicalPrsImage = itemView.findViewById(R.id.medicalPrsImage);
            medicalPrsQty = itemView.findViewById(R.id.medicalPrsQty);
            medicalPrsStatus = itemView.findViewById(R.id.medicalPrsStatus);
            medicalPrsViewImage = itemView.findViewById(R.id.medicalPrsViewImage);

        }

    }

    @Override
    public void onBindViewHolder(@NonNull MedicalPrescriptionAdapter.ImageHolder holder, int position)
    {

        final MedicalPrescriptionModel medicalPrescriptionModel = medicalPrescriptionModels.get(position);

        Picasso.get()
                .load(medicalPrescriptionModel.getImage())
                .placeholder(R.mipmap.loader_png)
                .into(holder.medicalPrsImage);

        holder.medicalPrsQty.setText(medicalPrescriptionModel.getQty());

        String temp = medicalPrescriptionModel.getStatus();
        if(temp.equals("0"))
        {
            holder.medicalPrsStatus.setText("Pending, Not yet Delivered");
            holder.medicalPrsViewImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    UnDeliverdPrescription_Activity.medicalInterface.ViewImage(medicalPrescriptionModel.getImage());
                }
            });

        }
        else if(temp.equals("1"))
        {
            holder.medicalPrsStatus.setText("Order is Picked up by the Delivery Agent");
            holder.medicalPrsViewImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    OnGoingPrescription_Activity.medicalInterface.ViewImage(medicalPrescriptionModel.getImage());
                }
            });
        }
        else if(temp.equals("2"))
        {
            holder.medicalPrsStatus.setText("Delivered");
            holder.medicalPrsViewImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DeliverdPrescription_Activity.medicalInterface.ViewImage(medicalPrescriptionModel.getImage());
                }
            });
        }
    }

}
