package com.example.foodyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodyapp.adapters.Adaptersliderview;
import com.example.foodyapp.show.Show_ListType;
import com.example.foodyapp.show.Show_Search;
import com.example.foodyapp.units.voucher;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class Layout_Home extends AppCompatActivity {

    public static Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_home);

        db = new Database(this, "Product.sqlite", null, 1);
        db.QueryData("CREATE TABLE IF NOT EXISTS cart(idC INTEGER PRIMARY KEY AUTOINCREMENT, nameC VARCHAR(255), priceC INTEGER, locationC VARCHAR(255), quantityC INTEGER, imageC BLOB)");


        Intent intent = getIntent();
        String infoname = intent.getExtras().getString("info");
        int total = intent.getIntExtra("total", 0);

        //Slide images
        SliderView sliderView = findViewById(R.id.imageSlide);
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.panel1);
        images.add(R.drawable.panel2);
        images.add(R.drawable.panel3);
        images.add(R.drawable.panel4);

        Adaptersliderview adaptersliderview = new Adaptersliderview(images);

        sliderView.setSliderAdapter(adaptersliderview);
        sliderView.setAutoCycle(true);

        //Bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        return true;

                    case R.id.history:
                        Intent tohistory = new Intent(getApplicationContext(), Layout_History.class);
                        tohistory.putExtra("info", infoname);
                        tohistory.putExtra("total", total);
                        startActivity(tohistory);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.cart:
                        Intent tocart = new Intent(getApplicationContext(), Layout_Cart.class);
                        tocart.putExtra("info", infoname);
                        tocart.putExtra("total", total);
                        startActivity(tocart);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.account:
                        Intent toaccount = new Intent(getApplicationContext(), Layout_Profile.class);
                        toaccount.putExtra("info", infoname);
                        toaccount.putExtra("total", total);
                        startActivity(toaccount);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        //Excution
        TextView tvsearch = (TextView) findViewById(R.id.search);
        tvsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Layout_Home.this, Show_Search.class);
                intent.putExtra("info", infoname);
                intent.putExtra("total", total);
                startActivity(intent);
            }
        });

        Button btntorice = (Button) findViewById(R.id.selectrice);
        btntorice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Layout_Home.this, Show_ListType.class);
                intent.putExtra("type", "Cơm");
                intent.putExtra("info", infoname);
                intent.putExtra("total", total);
                startActivity(intent);
            }
        });

        Button btntodrink = (Button) findViewById(R.id.selectdrink);
        btntodrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Layout_Home.this, Show_ListType.class);
                intent.putExtra("type", "Đồ uống");
                intent.putExtra("info", infoname);
                intent.putExtra("total", total);
                startActivity(intent);
            }
        });

        Button btntofastfood = (Button) findViewById(R.id.selectfastfood);
        btntofastfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Layout_Home.this, Show_ListType.class);
                intent.putExtra("type", "Đồ ăn nhanh");
                intent.putExtra("info", infoname);
                intent.putExtra("total", total);
                startActivity(intent);
            }
        });

        Button btntocake = (Button) findViewById(R.id.selectcake);
        btntocake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Layout_Home.this, Show_ListType.class);
                intent.putExtra("type", "Bánh");
                intent.putExtra("info", infoname);
                intent.putExtra("total", total);
                startActivity(intent);
            }
        });

        Button btntofruits = (Button) findViewById(R.id.selectfruits);
        btntofruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Layout_Home.this, Show_ListType.class);
                intent.putExtra("type", "Trái cây");
                intent.putExtra("info", infoname);
                intent.putExtra("total", total);
                startActivity(intent);
            }
        });

        Button btntospan = (Button) findViewById(R.id.selectspan);
        btntospan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Layout_Home.this, Show_ListType.class);
                intent.putExtra("type", "Đồ hộp");
                intent.putExtra("info", infoname);
                intent.putExtra("total", total);
                startActivity(intent);
            }
        });

    }

    //Set voucher
    RecyclerView listvoucher = findViewById(R.id.voucher);
    List<voucher> listv = new ArrayList<>();



}