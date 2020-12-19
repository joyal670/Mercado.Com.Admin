package com.example.mercadocomadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mercadocomadmin.Adapter.ProductAdapter;
import com.example.mercadocomadmin.Model.ProductModel;
import com.example.mercadocomadmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewProducts_Activity extends AppCompatActivity implements ProductAdapter.OnitemClickListener {

    String ShopId;
    RecyclerView viewProductRecycler;
    Query databaseReference;
    public ProgressDialog progressDialog;
    ProductAdapter productAdapter;
    List<ProductModel> productModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_products);

        try{

            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading ...");

            ShopId = getIntent().getExtras().getString("userId");

            viewProductRecycler = findViewById(R.id.viewProductRecycler);

            progressDialog.show();
            databaseReference = FirebaseDatabase.getInstance().getReference("products").orderByChild("pUserKey").equalTo(ShopId);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    progressDialog.dismiss();
                    productModel.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        ProductModel upload = dataSnapshot1.getValue(ProductModel.class);
                        upload.setmKey(dataSnapshot1.getKey());
                        productModel.add(upload);
                    }
                    productAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    progressDialog.dismiss();
                    Toast.makeText(ViewProducts_Activity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            productModel = new ArrayList<>();
            productAdapter = new ProductAdapter(ViewProducts_Activity.this, productModel);
            viewProductRecycler.setAdapter(productAdapter);
            productAdapter.setOnClickListener(ViewProducts_Activity.this);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemClick(final int position) {
        final String selectedItem = productModel.get(position).getmKey();

        String[] items = {"Delete", "Edit"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(ViewProducts_Activity.this);
        dialog.setTitle("Select Options");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if(which == 0)
                {
                    Toast.makeText(ViewProducts_Activity.this, "This Option will available soon", Toast.LENGTH_SHORT).show();
                }
                if(which == 1)
                {
                    LayoutInflater li = LayoutInflater.from(ViewProducts_Activity.this);
                    View viewproducts = li.inflate(R.layout.editproducts, null);
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ViewProducts_Activity.this);
                    alertBuilder.setView(viewproducts);
                    alertBuilder.setCancelable(false);

                    final EditText productnewname, productnewdescription, productnewprice;
                    TextView productoldname, productolddescription, productoldprice;
                    Button productviewsave, productviewcancel;

                    productnewname = viewproducts.findViewById(R.id.productnewname);
                    productnewdescription = viewproducts.findViewById(R.id.productnewdescription);
                    productnewprice = viewproducts.findViewById(R.id.productnewprice);

                    productoldname = viewproducts.findViewById(R.id.productoldname);
                    productolddescription = viewproducts.findViewById(R.id.productolddescription);
                    productoldprice = viewproducts.findViewById(R.id.productoldprice);

                    productviewsave = viewproducts.findViewById(R.id.productviewsave);
                    productviewcancel = viewproducts.findViewById(R.id.productviewcancel);

                    final AlertDialog alertDialog = alertBuilder.create();

                    productoldname.setText(productModel.get(position).getpName());
                    productolddescription.setText(productModel.get(position).getmDescription());
                    productoldprice.setText(productModel.get(position).getpPrice());

                    productviewsave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                            String name = productnewname.getText().toString();
                            String desc = productnewdescription.getText().toString();
                            String price = productnewprice.getText().toString();

                            if (!name.isEmpty())
                            {
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("products").child(selectedItem);
                                reference.child("pName").setValue(name);
                            }

                            if (!desc.isEmpty())
                            {
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("products").child(selectedItem);
                                reference.child("mDescription").setValue(desc);
                            }

                            if (!price.isEmpty())
                            {
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("products").child(selectedItem);
                                reference.child("pPrice").setValue(price);
                            }

                            alertDialog.dismiss();
                        }
                    });


                    productviewcancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.cancel();
                        }
                    });


                    alertDialog.show();
                }
            }
        });
        dialog.create().show();
    }
}