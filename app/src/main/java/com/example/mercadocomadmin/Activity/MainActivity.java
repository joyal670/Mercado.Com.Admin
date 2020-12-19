package com.example.mercadocomadmin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mercadocomadmin.Fragments.Allorders_Fragment;
import com.example.mercadocomadmin.Fragments.Chat_Fragment;
import com.example.mercadocomadmin.Fragments.DeliveryFragment;
import com.example.mercadocomadmin.Fragments.Home_Fragment;
import com.example.mercadocomadmin.Fragments.Logout_Fragment;
import com.example.mercadocomadmin.Fragments.Medical_Fragment;
import com.example.mercadocomadmin.Fragments.Ongoing_Fragment;
import com.example.mercadocomadmin.Fragments.Products_Fragment;
import com.example.mercadocomadmin.Fragments.ShopFragment;
import com.example.mercadocomadmin.Fragments.Users_Fragment;
import com.example.mercadocomadmin.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToogle;
    TextView headerEmail;
    String currentFragment = "other";
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {


            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorBlue));
            }

            mDrawerLayout = findViewById(R.id.drawerLayout);
            mDrawerToogle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);
            mDrawerLayout.addDrawerListener(mDrawerToogle);
            mDrawerToogle.syncState();

            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            final Home_Fragment fragment = new Home_Fragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Home");
            fragmentTransaction.commit();

            headerEmail = navigationView.getHeaderView(0).findViewById(R.id.header_email);

            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            headerEmail.setText(firebaseUser.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        int id = menuItem.getItemId();
        if(id == R.id.home)
        {
            currentFragment = "home";
            Home_Fragment fragment = new Home_Fragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout,fragment,"Home");
            fragmentTransaction.commit();
        }
        else if (id == R.id.allorders)
        {
            currentFragment = "other";
            Allorders_Fragment fragment= new Allorders_Fragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout,fragment,"All Orders");
            fragmentTransaction.commit();
        }
        else if (id == R.id.ongoing)
        {
            currentFragment = "other";
            Ongoing_Fragment fragment= new Ongoing_Fragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout,fragment,"On-Going Orders");
            fragmentTransaction.commit();
        }
        else if (id == R.id.medicalorders)
        {
            currentFragment = "other";
            Medical_Fragment fragment= new Medical_Fragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout,fragment,"Medical Orders");
            fragmentTransaction.commit();
        }
        else if (id == R.id.users)
        {
            currentFragment = "other";
            Users_Fragment fragment= new Users_Fragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout,fragment,"All Users");
            fragmentTransaction.commit();
        }
        else if (id == R.id.shopstatistics)
        {
            currentFragment = "other";
            ShopFragment fragment= new ShopFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout,fragment,"Shop Statistics");
            fragmentTransaction.commit();
        }
        else if (id == R.id.deliverystatistics)
        {
            currentFragment = "other";
            DeliveryFragment fragment= new DeliveryFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout,fragment,"Delivery Statistics");
            fragmentTransaction.commit();
        }
        else if (id == R.id.products)
        {
            currentFragment = "other";
            Products_Fragment fragment= new Products_Fragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout,fragment,"Products");
            fragmentTransaction.commit();
        }
        else if (id == R.id.chat) {
            currentFragment = "other";
            Chat_Fragment fragment = new Chat_Fragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout, fragment, "Chat");
            fragmentTransaction.commit();

        }

        else if(id == R.id.logout)
        {
            currentFragment = "other";
            Logout_Fragment fragment= new Logout_Fragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout,fragment,"Logout");
            fragmentTransaction.commit();
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
        {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {

            if(currentFragment != "home")
            {
                Home_Fragment fragment= new Home_Fragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout,fragment,"Home");
                fragmentTransaction.commit();
            }
            else {
                super.onBackPressed();
            }
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.items,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId())
//        {
//            case R.id.item_MyAccount:
//                Intent intent = new Intent(MainActivity.this,MyAccount_Activity.class);
//                startActivity(intent);
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//
//    }

}
