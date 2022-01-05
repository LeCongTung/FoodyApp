package com.example.foodyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.foodyapp.adapters.Adaptersliderview;
import com.example.foodyapp.show.Show_ListType;
import com.example.foodyapp.show.Show_Search;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class Layout_Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_home);

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
                        startActivity(new Intent(getApplicationContext(), Layout_History.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.cart:
                        startActivity(new Intent(getApplicationContext(), Layout_Cart.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(), Layout_Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        //Excution
        Button btntorice = (Button) findViewById(R.id.selectrice);
        btntorice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Layout_Home.this, Show_ListType.class);
                intent.putExtra("info", "Cơm");
                startActivity(intent);
            }
        });

        TextView tvsearch = (TextView) findViewById(R.id.search);
        tvsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Layout_Home.this, Show_Search.class);
                startActivity(intent);
            }
        });

        Button btntodrink = (Button) findViewById(R.id.selectdrink);
        btntodrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Layout_Home.this, Show_ListType.class);
                intent.putExtra("info", "Đồ uống");
                startActivity(intent);
            }
        });

        Button btntofastfood = (Button) findViewById(R.id.selectfastfood);
        btntofastfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Layout_Home.this, Show_ListType.class);
                intent.putExtra("info", "Đồ ăn nhanh");
                startActivity(intent);
            }
        });

        Button btntocake = (Button) findViewById(R.id.selectcake);
        btntocake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Layout_Home.this, Show_ListType.class);
                intent.putExtra("info", "Bánh");
                startActivity(intent);
            }
        });

        Button btntofruits = (Button) findViewById(R.id.selectfruits);
        btntofruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Layout_Home.this, Show_ListType.class);
                intent.putExtra("info", "Trái cây");
                startActivity(intent);
            }
        });

        Button btntospan = (Button) findViewById(R.id.selectspan);
        btntospan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Layout_Home.this, Show_ListType.class);
                intent.putExtra("info", "Đồ hộp");
                startActivity(intent);
            }
        });

    }


}