package com.example.mercadocomadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mercadocomadmin.Model.OrderModel;
import com.example.mercadocomadmin.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderDataAdapter extends BaseAdapter
{
    Context context;
    List<OrderModel> model;

    public OrderDataAdapter(Context context, List<OrderModel> model) {
        this.context = context;
        this.model = model;
    }

    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.order_view, null);

        TextView current_name, current_price, current_qty, current_status, current_orderddate, current_deliverydate;
        ImageView image;

        current_name = convertView.findViewById(R.id.current_name);
        current_price = convertView.findViewById(R.id.current_price);
        current_qty = convertView.findViewById(R.id.current_qty);
        current_status = convertView.findViewById(R.id.current_status);
        current_orderddate = convertView.findViewById(R.id.current_orderddate);
        current_deliverydate = convertView.findViewById(R.id.current_deliverydate);
        image = convertView.findViewById(R.id.current_image);

        current_name.setText(model.get(position).getProductName());
        current_price.setText(model.get(position).getProductPrice());
        current_qty.setText(model.get(position).getProductQty());
        current_orderddate.setText(model.get(position).getOrderDate());

        if(model.get(position).getStatus().equalsIgnoreCase("0"))
        {
            current_status.setText("Pending, Not yet Delivered");
            current_deliverydate.setText("Not Delivered");
        }
        else if (model.get(position).getStatus().equalsIgnoreCase("1"))
        {
            current_status.setText("Order is Picked up by the Delivery Agent");
            current_deliverydate.setText("Not Delivered");
        }
        else if(model.get(position).getStatus().equalsIgnoreCase("2"))
        {
            current_status.setText("Delivered");
            current_deliverydate.setText(model.get(position).getOrderDeliveryDate());
        }
        else
        {
            current_status.setText("Order Cancelled");
        }

        Picasso.get().load(model.get(position).getProductImageUrl()).into(image);

        return convertView;
    }
}
