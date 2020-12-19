package com.example.mercadocomadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mercadocomadmin.Model.OrderModel;
import com.example.mercadocomadmin.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ImageHolder>
{
    Context context;
    List<OrderModel> model;
    OnitemClickListener mlistener;

    public HomeAdapter(Context context, List<OrderModel> model) {
        this.context = context;
        this.model = model;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.homegird_view, parent, false);
        return new ImageHolder(view);

    }
    @Override
    public int getItemCount() {
        return model.size();
    }

    public class ImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView current_name, current_price, current_qty, current_date, current_shopname,  current_deliveryboyname;
        ImageView image;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);

            current_name = itemView.findViewById(R.id.homename);
            current_price = itemView.findViewById(R.id.homeprice);
            current_qty = itemView.findViewById(R.id.homeqty);
            image = itemView.findViewById(R.id.homeimage);
            current_date = itemView.findViewById(R.id.homedate);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mlistener != null)
            {
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION)
                {
                    mlistener.onItemClick(position);
                }
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {

        OrderModel currentUpload = model.get(position);

        holder.current_name.setText(currentUpload.getProductName());
        holder.current_price.setText(currentUpload.getProductPrice());
        holder.current_qty.setText(currentUpload.getProductQty());
        holder.current_date.setText(currentUpload.getOrderDate());
        Picasso.get()
                .load(currentUpload.getProductImageUrl())
                .placeholder(R.mipmap.loader_png)
                .into(holder.image);
    }
    public interface OnitemClickListener{
        void onItemClick(int position);
    }
    public void setOnClickListener(OnitemClickListener listener)
    {
        mlistener = listener;
    }
}