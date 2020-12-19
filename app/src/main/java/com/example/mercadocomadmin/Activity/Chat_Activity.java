package com.example.mercadocomadmin.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.mercadocomadmin.Adapter.ChatAdapter;
import com.example.mercadocomadmin.Model.ChatModel;
import com.example.mercadocomadmin.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Chat_Activity extends AppCompatActivity {

    RecyclerView chatRecyclerView;
    DatabaseReference databaseReference;
    SharedPreferences sharedPreferences;
    public ProgressDialog progressDialog;
    List<ChatModel> mChatModel;
    ChatAdapter chatAdapter;
    EditText chatMsgEditText;
    FloatingActionButton chatSentBtn;
    String orderId, userId;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_);

        try{

            Toolbar toolbar = findViewById(R.id.mycustomtoolbar);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle("HelpLine");


            chatRecyclerView = findViewById(R.id.chatRecyclerView);
        chatMsgEditText = findViewById(R.id.chatMsgEditText);
        chatSentBtn = findViewById(R.id.chatSentBtn);

        orderId = getIntent().getExtras().getString("orderid");
        userId = getIntent().getExtras().getString("userId");

        chatRecyclerView.setLayoutManager(new LinearLayoutManager(Chat_Activity.this));
        chatRecyclerView.setHasFixedSize(true);

        progressDialog = new ProgressDialog(Chat_Activity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Your Messages...");

        progressDialog.show();
        databaseReference = FirebaseDatabase.getInstance().getReference("Chat");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();
                mChatModel.clear();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    if(orderId.equals(dataSnapshot1.child("temp2").getValue().toString()))
                    {
                        ChatModel model = dataSnapshot1.getValue(ChatModel.class);
                        mChatModel.add(model);
                    }

                }

                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(Chat_Activity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mChatModel = new ArrayList<>();
        chatAdapter = new ChatAdapter(Chat_Activity.this, mChatModel);
        chatRecyclerView.setAdapter(chatAdapter);

        chatSentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sentMsg();
            }
        });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void sentMsg()
    {
        try{


        String message = chatMsgEditText.getText().toString();
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String pushKey;

        pushKey = databaseReference.push().getKey();

        if(message.isEmpty())
        {
            Toast.makeText(Chat_Activity.this, "Please Type Some Message", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ChatModel model = new ChatModel();
            model.setMsg(message);
            model.setDate(date);
            model.setTime(time);
            model.setUserId(userId);
            model.setPushKey(pushKey);
            model.setTemp1("admin");
            model.setTemp2(orderId);
            model.setTemp3("");

            databaseReference.child(pushKey).setValue(model);

            chatMsgEditText.setText("");

        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}