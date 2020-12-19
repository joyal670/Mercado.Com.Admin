package com.example.mercadocomadmin.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mercadocomadmin.Adapter.UserDataAdapter;
import com.example.mercadocomadmin.Model.UserDataModel;
import com.example.mercadocomadmin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ViewDeliveryBoys_Activity extends AppCompatActivity {
    ListView viewdeliveryboyListView;
    DatabaseReference databaseReference;
    List<UserDataModel> mUserModel;
    UserDataAdapter userDataAdapter;
    FloatingActionButton floatingActionButtondeliveryboy;
    private FirebaseAuth firebaseAuth;
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_delivery_boys);

        try {

            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");

            firebaseAuth = FirebaseAuth.getInstance();

            viewdeliveryboyListView = findViewById(R.id.viewdeliveryboyListView);
            floatingActionButtondeliveryboy = findViewById(R.id.floatingActionButtondeliveryboy);

            progressDialog.show();
            databaseReference = FirebaseDatabase.getInstance().getReference("delivery_boy");
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
                    Toast.makeText(ViewDeliveryBoys_Activity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            mUserModel = new ArrayList<>();
            userDataAdapter = new UserDataAdapter(getApplicationContext(), mUserModel);
            viewdeliveryboyListView.setAdapter(userDataAdapter);
            viewdeliveryboyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    String[] items = {" Delete"};
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ViewDeliveryBoys_Activity.this);
                    dialog.setTitle("Select Options");
                    dialog.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                Toast.makeText(ViewDeliveryBoys_Activity.this, "Unable to Delete " + mUserModel.get(position).getUserEmail() + " Use Firebase Console to Delete Account", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    dialog.create().show();
                }
            });

            floatingActionButtondeliveryboy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addDeliveryBoy();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addDeliveryBoy() {
        try {
            LayoutInflater li = LayoutInflater.from(ViewDeliveryBoys_Activity.this);
            View addUser = li.inflate(R.layout.deliveryboy_prompt, null);
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ViewDeliveryBoys_Activity.this);
            alertBuilder.setView(addUser);
            alertBuilder.setCancelable(false);

            final EditText username, useremail, userpassword, userpassword1;
            Button savebtn, cancelbtn;

            username = addUser.findViewById(R.id.newdeliveryboyname);
            useremail = addUser.findViewById(R.id.newdeliveryboyemail);
            userpassword = addUser.findViewById(R.id.newdeliveryboypassword);
            userpassword1 = addUser.findViewById(R.id.newdeliveryboypassword1);
            savebtn = addUser.findViewById(R.id.newdeliveryboysavebtn);
            cancelbtn = addUser.findViewById(R.id.newdeliveryboycancelbtn);

            final AlertDialog alertDialog = alertBuilder.create();

            savebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String s1, s2, s3, s4;
                    s1 = username.getText().toString();
                    s2 = useremail.getText().toString();
                    s3 = userpassword.getText().toString();
                    s4 = userpassword1.getText().toString();

                    if (s1.isEmpty() || s2.isEmpty() || s3.isEmpty() || s4.isEmpty()) {
                        if (s1.isEmpty()) {
                            username.setError("Name Required");
                        }
                        if (s2.isEmpty()) {
                            useremail.setError("Email Required");
                        }
                        if (s3.isEmpty()) {
                            userpassword.setError("Password Required");
                        }
                        if (s4.isEmpty()) {
                            userpassword1.setError("Password Required");
                        }
                    } else {
                        if (s3.equals(s4)) {
                            progressDialog.show();
                            firebaseAuth.createUserWithEmailAndPassword(s2, s3).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        String key = task.getResult().getUser().getUid();
                                        databaseReference = FirebaseDatabase.getInstance().getReference("delivery_boy").child(key);
                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("userId", key);
                                        hashMap.put("userName", s1);
                                        hashMap.put("userEmail", s2);
                                        databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(ViewDeliveryBoys_Activity.this, "A New Account is Added", Toast.LENGTH_SHORT).show();
                                                    alertDialog.dismiss();
                                                    progressDialog.dismiss();
                                                } else {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(ViewDeliveryBoys_Activity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(ViewDeliveryBoys_Activity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(ViewDeliveryBoys_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            userpassword1.setError("Password Not Matched");
                        }
                    }
                }
            });

            cancelbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.cancel();
                }
            });
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
