package com.example.mercadocomadmin.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mercadocomadmin.R;

public class SplashScreen extends AppCompatActivity {

    Handler handler;
    ImageView imageView;
    TextView textView1, textView2;
    Animation top, bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        try {
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorBlue));
            }

            imageView = findViewById(R.id.imageView);
            textView1 = findViewById(R.id.textView);
            textView2 = findViewById(R.id.textView2);

            top = AnimationUtils.loadAnimation(this, R.anim.top);
            bottom = AnimationUtils.loadAnimation(this, R.anim.bottom);
            imageView.setAnimation(top);
            textView1.setAnimation(bottom);
            textView2.setAnimation(bottom);

            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashScreen.this, Login_Activity.class);
                    startActivity(intent);
                    finish();
                }
            }, 3000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
