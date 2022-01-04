package com.example.foodyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.foodyapp.activities.Activity_Login;
import com.example.foodyapp.show.Show_AddProduct;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Layout_Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_profile);

        Button information = (Button) findViewById(R.id.information);
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Layout_Profile.this, Private_information.class);
                startActivity(intent);
            }
        });
        Button btntoshop = (Button) findViewById(R.id.shop);
        btntoshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Layout_Profile.this, Show_AddProduct.class));
            }
        });

        Button btnlogout = (Button) findViewById(R.id.logout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Layout_Profile.this, Activity_Login.class));
                Toast.makeText(Layout_Profile.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            }
        });



        //Bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.account);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.account:
                        return true;

                    case R.id.history:
                        startActivity(new Intent(getApplicationContext(), Layout_History.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.cart:
                        startActivity(new Intent(getApplicationContext(), Layout_Cart.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Layout_Home.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}