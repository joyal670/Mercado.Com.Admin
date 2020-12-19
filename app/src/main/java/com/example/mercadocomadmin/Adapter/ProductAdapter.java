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

import com.example.mercadocomadmin.Model.ProductModel;
import com.example.mercadocomadmin.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ImageHolder> {

    Context context;
    List<ProductModel> mProduct;
    OnitemClickListener mlistener;

    public ProductAdapter(Context context, List<ProductModel> mProduct) {
        this.context = context;
        this.mProduct = mProduct;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.product_items, parent, false);
        return new ImageHolder(view);

    }

    @Override
    public int getItemCount() {
        return mProduct.size();
    }

    public class ImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView productitemname, productitemdescription, productitemprice, productitemstoke;
        ImageView productitemimage;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);

            productitemimage = itemView.findViewById(R.id.productitemimage);
            productitemname = itemView.findViewById(R.id.productitemname);
            productitemdescription = itemView.findViewById(R.id.productitemdescription);
            productitemprice = itemView.findViewById(R.id.productitemprice);
            productitemstoke = itemView.findViewById(R.id.productitemstoke);

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

    public interface OnitemClickListener{
        void onItemClick(int position);
    }

    public void setOnClickListener(OnitemClickListener listener)
    {
        mlistener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, final int position) {

        final ProductModel currentUpload = mProduct.get(position);

        holder.productitemname.setText(currentUpload.getpName());
        holder.productitemdescription.setText(currentUpload.getmDescription());
        holder.productitemprice.setText(currentUpload.getpPrice());
        holder.productitemstoke.setText(currentUpload.getpStoke());
        Picasso.get()
                .load(currentUpload.getpImageUrl())
                .placeholder(R.mipmap.loader_png)
                .into(holder.productitemimage);


    }


}
