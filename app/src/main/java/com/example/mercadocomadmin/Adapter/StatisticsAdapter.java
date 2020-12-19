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

public class StatisticsAdapter extends BaseAdapter
{
    Context context;
    List<OrderModel> model;

    public StatisticsAdapter(Context context, List<OrderModel> model) {
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
        convertView = inflater.inflate(R.layout.statistics_view, null);

        TextView statistics_view_name, statistics_view_orderddate, statistics_view_deliverydate, statistics_view_productname;
        TextView statistics_view_productprice, statistics_view_productqty, statistics_view_total;
        ImageView statistics_view_productimage;

        statistics_view_name = convertView.findViewById(R.id.statistics_view_name);
        statistics_view_orderddate = convertView.findViewById(R.id.statistics_view_orderddate);
        statistics_view_deliverydate = convertView.findViewById(R.id.statistics_view_deliverydate);
        statistics_view_productname = convertView.findViewById(R.id.statistics_view_productname);
        statistics_view_productprice = convertView.findViewById(R.id.statistics_view_productprice);
        statistics_view_productqty = convertView.findViewById(R.id.statistics_view_productqty);
        statistics_view_total = convertView.findViewById(R.id.statistics_view_total);
        statistics_view_productimage = convertView.findViewById(R.id.statistics_view_productimage);


        statistics_view_name.setText(model.get(position).getContName());
        statistics_view_orderddate.setText(model.get(position).getOrderDate());

        if(model.get(position).getOrderDeliveryDate().isEmpty())
        {
            statistics_view_deliverydate.setText("Ongoing");
        }
        else
        {
            statistics_view_deliverydate.setText(model.get(position).getOrderDeliveryDate());
        }

        statistics_view_productname.setText(model.get(position).getProductName());
        statistics_view_productprice.setText(model.get(position).getProductPrice());
        statistics_view_productqty.setText(model.get(position).getProductQty());

        Picasso.get().load(model.get(position).getProductImageUrl()).into(statistics_view_productimage);

        int price = Integer.parseInt(statistics_view_productprice.getText().toString());
        int qty = Integer.parseInt(statistics_view_productqty.getText().toString());
        int total = qty * price;
        String tot = String.valueOf(total);

        statistics_view_total.setText(tot);

        return convertView;
    }
}
