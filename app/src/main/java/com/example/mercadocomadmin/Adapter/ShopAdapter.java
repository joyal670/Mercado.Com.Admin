package com.example.mercadocomadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mercadocomadmin.Model.ShopModel;
import com.example.mercadocomadmin.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShopAdapter extends BaseAdapter
{

    Context context;
    List<ShopModel> model;

    public ShopAdapter(Context context, List<ShopModel> model) {
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
        convertView = inflater.inflate(R.layout.shop_view, null);

        ImageView shopDataimage;
        TextView shopDataname, shopDataplace, shopDataphone, shopDatatype;

        shopDataimage = convertView.findViewById(R.id.shopDataimage);
        shopDataname = convertView.findViewById(R.id.shopDataname);
        shopDataplace = convertView.findViewById(R.id.shopDataplace);
        shopDataphone = convertView.findViewById(R.id.shopDataphone);
        shopDatatype = convertView.findViewById(R.id.shopDatatype);

        Picasso.get().load(model.get(position).getmImageUrl()).into(shopDataimage);
        shopDataname.setText(model.get(position).getmName());
        shopDataplace.setText(model.get(position).getmPlace());
        shopDataphone.setText(model.get(position).getmPhone());
        shopDatatype.setText(model.get(position).getmType());

        return convertView;
    }
}
