package com.example.foodyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class ShowProduct extends AppCompatActivity {

    ListView listview;
    Button btnadd;
    ImageView btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);

        listview = (ListView) findViewById(R.id.listProduct);

        btnback = (ImageView) findViewById(R.id.btnback);


        //Event: Click btnadd to go to AddProduct
        btnadd = (Button) findViewById(R.id.btnaaddForm);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowProduct.this, AddProduct.class);
                startActivity(intent);
            }
        });

    }

}