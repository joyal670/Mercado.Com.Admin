package com.example.mercadocomadmin.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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

public class ViewUsers_Activity extends AppCompatActivity
{
    ListView viewusersListView;
    DatabaseReference databaseReference;
    List<UserDataModel> mUserModel;
    UserDataAdapter userDataAdapter;
    FloatingActionButton floatingActionButton;
    private FirebaseAuth firebaseAuth;
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        try {

            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");

            firebaseAuth = FirebaseAuth.getInstance();

            viewusersListView = findViewById(R.id.viewusersListView);
            floatingActionButton = findViewById(R.id.floatingActionButton);

            progressDialog.show();
            databaseReference = FirebaseDatabase.getInstance().getReference("user_data");
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
                    Toast.makeText(ViewUsers_Activity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            mUserModel = new ArrayList<>();
            userDataAdapter = new UserDataAdapter(getApplicationContext(), mUserModel);
            viewusersListView.setAdapter(userDataAdapter);
            viewusersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    String[] items = {" Delete"};
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ViewUsers_Activity.this);
                    dialog.setTitle("Select Options");
                    dialog.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                Toast.makeText(ViewUsers_Activity.this, "Unable to Delete " + mUserModel.get(position).getUserEmail() + " Use Firebase Console to Delete Account", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    dialog.create().show();
                }
            });

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addNewUser();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addNewUser()
    {
        try {
            LayoutInflater li = LayoutInflater.from(ViewUsers_Activity.this);
            View addUser = li.inflate(R.layout.prompt, null);
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ViewUsers_Activity.this);
            alertBuilder.setView(addUser);
            alertBuilder.setCancelable(false);

            final EditText username, useremail, userpassword, userpassword1;
            Button savebtn, cancelbtn;

            username = addUser.findViewById(R.id.newusername);
            useremail = addUser.findViewById(R.id.newuseremail);
            userpassword = addUser.findViewById(R.id.newuserpassword);
            userpassword1 = addUser.findViewById(R.id.newuserpassword1);
            savebtn = addUser.findViewById(R.id.newaddsavebtn);
            cancelbtn = addUser.findViewById(R.id.newaddcancelbtn);

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
                                        databaseReference = FirebaseDatabase.getInstance().getReference("user_data").child(key);
                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("userId", key);
                                        hashMap.put("userName", s1);
                                        hashMap.put("userEmail", s2);
                                        databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(ViewUsers_Activity.this, "A New User is Added", Toast.LENGTH_SHORT).show();
                                                    alertDialog.dismiss();
                                                    progressDialog.dismiss();
                                                } else {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(ViewUsers_Activity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(ViewUsers_Activity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(ViewUsers_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
