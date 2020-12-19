package com.example.mercadocomadmin.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mercadocomadmin.Adapter.UserDataAdapter;
import com.example.mercadocomadmin.Model.ShopModel;
import com.example.mercadocomadmin.Model.UserDataModel;
import com.example.mercadocomadmin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ViewShopOwners_Activity extends AppCompatActivity {
    ListView viewshopownersListView;
    DatabaseReference databaseReference;
    List<UserDataModel> mUserModel;
    UserDataAdapter userDataAdapter;
    FloatingActionButton floatingActionButtonShopOwner;
    private StorageTask mUploadTask;
    private StorageReference mstorageReference;
    private Uri mImageUri;
    private FirebaseAuth firebaseAuth;
    private static int image_pic_request = 1;
    ImageView shopimage;
    public ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_shop_owners);

        try {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");

            viewshopownersListView = findViewById(R.id.viewshopownersListView);
            floatingActionButtonShopOwner = findViewById(R.id.floatingActionButtonShopOwner);

            firebaseAuth = FirebaseAuth.getInstance();

            mstorageReference = FirebaseStorage.getInstance().getReference("shop_owner/shop_image");

            progressDialog.show();
            databaseReference = FirebaseDatabase.getInstance().getReference("shop_owner");
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
                    Toast.makeText(ViewShopOwners_Activity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            mUserModel = new ArrayList<>();
            userDataAdapter = new UserDataAdapter(getApplicationContext(), mUserModel);
            viewshopownersListView.setAdapter(userDataAdapter);
            viewshopownersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    String[] items = {" Delete"};
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ViewShopOwners_Activity.this);
                    dialog.setTitle("Select Options");
                    dialog.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                Toast.makeText(ViewShopOwners_Activity.this, "Unable to Delete " + mUserModel.get(position).getUserEmail() + " Use Firebase Console to Delete Account", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    dialog.create().show();
                }
            });
            floatingActionButtonShopOwner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addShop();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addShop() {
        try {
            LayoutInflater li = LayoutInflater.from(ViewShopOwners_Activity.this);
            View addUser = li.inflate(R.layout.shop_prompt, null);
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ViewShopOwners_Activity.this);
            alertBuilder.setView(addUser);
            alertBuilder.setCancelable(false);

            final EditText username, useremail, userpassword, userpassword1, shopname, shoppalce, shopphone;
            final Spinner shoptype;
            Button savebtn, cancelbtn;

            String[] catogries = {"Bakery", "Vegetableshop", "Restaurant", "Fishmarket", "Supermarket", "Medicalshop", "Electronicshop", "Fashion", "Butchershop" };
            ArrayAdapter<String> catog = new ArrayAdapter<String>(ViewShopOwners_Activity.this, android.R.layout.simple_spinner_dropdown_item, catogries);

            username = addUser.findViewById(R.id.newshopname);
            useremail = addUser.findViewById(R.id.newshopemail);
            userpassword = addUser.findViewById(R.id.newshoppassword);
            userpassword1 = addUser.findViewById(R.id.newshoppassword1);
            savebtn = addUser.findViewById(R.id.newshopsavebtn);
            cancelbtn = addUser.findViewById(R.id.newshopcancelbtn);

            shopname = addUser.findViewById(R.id.newusershopname);
            shoppalce = addUser.findViewById(R.id.newusershopplace);
            shopphone = addUser.findViewById(R.id.newuserphonenumber);
            shopimage = addUser.findViewById(R.id.newusershopimage);
            shoptype = addUser.findViewById(R.id.newusershoptype);

            shopimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openFileChooser();
                }
            });

            shoptype.setAdapter(catog);

            final AlertDialog alertDialog = alertBuilder.create();

            savebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String s1, s2, s3, s4, s5, s6, s7, s8;
                    s1 = username.getText().toString();
                    s2 = useremail.getText().toString();
                    s3 = userpassword.getText().toString();
                    s4 = userpassword1.getText().toString();
                    s5 = shopname.getText().toString();
                    s6 = shoppalce.getText().toString();
                    s7 = shopphone.getText().toString();
                    s8 = shoptype.getSelectedItem().toString();

                    if (s1.isEmpty() || s2.isEmpty() || s3.isEmpty() || s4.isEmpty() || s5.isEmpty() || s6.isEmpty() || s7.isEmpty() || s8.isEmpty() || mImageUri == null) {
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
                        if (s5.isEmpty()) {
                            shopname.setError("Shop Name Required");
                        }
                        if (s6.isEmpty()) {
                            shoppalce.setError("Shop Place Required");
                        }
                        if (s7.isEmpty()) {
                            shopphone.setError("Shop Phone Number Required");
                        }
                        if (s8.isEmpty()) {
                            Toast.makeText(ViewShopOwners_Activity.this, "Select Shop Type", Toast.LENGTH_SHORT).show();
                        }
                        if (mImageUri == null) {
                            Toast.makeText(ViewShopOwners_Activity.this, "No Files Selected", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (s3.equals(s4)) {
                            progressDialog.show();
                            firebaseAuth.createUserWithEmailAndPassword(s2, s3).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        final String key = task.getResult().getUser().getUid();
                                        databaseReference = FirebaseDatabase.getInstance().getReference("shop_owner").child(key);
                                        HashMap<String, String> hashMap = new HashMap<>();
                                        hashMap.put("userId", key);
                                        hashMap.put("userName", s1);
                                        hashMap.put("userEmail", s2);
                                        databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task)
                                            {
                                                progressDialog.show();
                                                if (task.isSuccessful())
                                                {
                                                    progressDialog.show();
                                                    if (mUploadTask != null && mUploadTask.isInProgress())
                                                    {
                                                        progressDialog.show();
                                                        Toast.makeText(ViewShopOwners_Activity.this, "Uploading in Progress", Toast.LENGTH_SHORT).show();
                                                    } else
                                                        {
                                                        if (mImageUri != null)
                                                        {
                                                            StorageReference fileRef = mstorageReference.child(System.currentTimeMillis() + "." + getFileExtention(mImageUri));
                                                            mUploadTask = fileRef.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                                @Override
                                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                                                    while (!uriTask.isSuccessful())
                                                                        ;
                                                                    Uri downloadUrl = uriTask.getResult();

                                                                    ShopModel shopModel = new ShopModel(s5, downloadUrl.toString(), s6, s7, s8, key);
                                                                    Toast.makeText(ViewShopOwners_Activity.this, "Success", Toast.LENGTH_SHORT).show();

                                                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("shop_owner").child(key).child("shop_details");
                                                                    String uploadId = reference.push().getKey();
                                                                    reference.child(uploadId).setValue(shopModel);
                                                                    alertDialog.dismiss();
                                                                    progressDialog.dismiss();
                                                                    mImageUri = null;

                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    progressDialog.dismiss();
                                                                    mImageUri = null;
                                                                    Toast.makeText(ViewShopOwners_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            });

                                                        } else {
                                                            progressDialog.dismiss();
                                                            Toast.makeText(ViewShopOwners_Activity.this, "No file Selected", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                } else {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(ViewShopOwners_Activity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(ViewShopOwners_Activity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(ViewShopOwners_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
                    mImageUri = null;
                }
            });
            alertDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, image_pic_request);
    }

    private String getFileExtention(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == image_pic_request && resultCode == RESULT_OK && data != null && data.getData() != null) {
                mImageUri = data.getData();
                Picasso.get().load(mImageUri).into(shopimage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
