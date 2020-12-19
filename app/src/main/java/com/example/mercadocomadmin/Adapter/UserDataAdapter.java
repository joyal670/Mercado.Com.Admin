package com.example.mercadocomadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mercadocomadmin.Model.UserDataModel;
import com.example.mercadocomadmin.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserDataAdapter extends BaseAdapter
{
    Context context;
    List<UserDataModel> model;

    public UserDataAdapter(Context context, List<UserDataModel> model)
    {
        this.context = context;
        this.model =model;
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
        convertView = inflater.inflate(R.layout.user_view, null);

        TextView userviewname, userviewemail, userviewuserid;

        userviewname = convertView.findViewById(R.id.userviewname);
        userviewemail = convertView.findViewById(R.id.userviewemail);
        userviewuserid = convertView.findViewById(R.id.userviewuserid);

        userviewname.setText(model.get(position).getUserName());
        userviewemail.setText(model.get(position).getUserEmail());
        userviewuserid.setText(model.get(position).getUserId());

        return convertView;
    }
}
