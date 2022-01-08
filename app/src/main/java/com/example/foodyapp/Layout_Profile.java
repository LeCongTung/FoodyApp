package com.example.foodyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodyapp.activities.Activity_ChangePass;
import com.example.foodyapp.activities.Activity_Login;
import com.example.foodyapp.loading.Loading_afterLogout;
import com.example.foodyapp.show.Show_AddProduct;
import com.example.foodyapp.show.Show_UserDetail;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Layout_Profile extends AppCompatActivity {

    public static Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_profile);

        //Create a database and a table with values
        db = new Database(this, "Product.sqlite", null, 1);

        Intent intent = getIntent();
        String info = intent.getExtras().getString("info");
        int total = intent.getIntExtra("total", 0);

        //Show user
        TextView tvname = (TextView) findViewById(R.id.nameuser);
        tvname.setText("Xin chào, " + info);

        Button information = (Button) findViewById(R.id.information);
        information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Layout_Profile.this, Show_UserDetail.class);
                intent.putExtra("info", info);
                intent.putExtra("total", total);
                startActivity(intent);
            }
        });

        Button changepass = (Button) findViewById(R.id.changepass);
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Layout_Profile.this, Activity_ChangePass.class);
                intent.putExtra("info", info);
                intent.putExtra("total", total);
                startActivity(intent);
            }
        });

        Button btntoshop = (Button) findViewById(R.id.shop);
        btntoshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (info.equals("tung")){
                    Intent intent = new Intent(Layout_Profile.this, Show_AddProduct.class);
                    intent.putExtra("info", info);
                    intent.putExtra("total", total);
                    startActivity(intent);
                }else
                    Toast.makeText(Layout_Profile.this, "Yêu cầu đăng ký bán hàng", Toast.LENGTH_SHORT).show();
            }
        });

        Button btnlogout = (Button) findViewById(R.id.logout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Layout_Profile.this, Loading_afterLogout.class));

                db.QueryData("DELETE FROM cart");
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
                        Intent tohistory = new Intent(getApplicationContext(), Layout_History.class);
                        tohistory.putExtra("info", info);
                        tohistory.putExtra("total", total);
                        startActivity(tohistory);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.cart:
                        Intent tocart = new Intent(getApplicationContext(), Layout_Cart.class);
                        tocart.putExtra("info", info);
                        tocart.putExtra("total", total);
                        startActivity(tocart);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        Intent tohome = new Intent(getApplicationContext(), Layout_Home.class);
                        tohome.putExtra("info", info);
                        tohome.putExtra("total", total);
                        startActivity(tohome);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}