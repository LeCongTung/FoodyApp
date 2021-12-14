package com.example.foodyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowProduct extends AppCompatActivity {

    ListView listview;
    Button btnadd;
    ImageView btnback;
    SQLiteHelperMT dbMT;
    SQLiteDatabase sqLiteDatabase;
    ArrayList<MilkTea> arrayListMT;
    MilkTeaAdapter adapterMT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);

        listview = (ListView) findViewById(R.id.listProduct);
        arrayListMT = new ArrayList<>();
        adapterMT = new MilkTeaAdapter(this, R.layout.item_product, arrayListMT);
        listview.setAdapter(adapterMT);

        //Init using data from Database *Product.sqlite
        dbMT = new SQLiteHelperMT(this, "Product.sqlite", null, 1);

        //Create table
        dbMT.QueryData("CREATE TABLE IF NOT EXISTS tblMilkTea(idMT INTEGER PRIMARY KEY AUTOINCREMENT, nameMT VARCHAR(100), price INTEGER, locationMT VARCHAR(255))");
//        dbMT.QueryData("INSERT INTO tblMilkTea VALUES (null, 'Trà sữa thảo mộc', 30000, '113 Lê Lợi, Đà Nẵng')");
//        dbMT.QueryData("INSERT INTO tblMilkTea VALUES (null, 'Trà sũa matcha', 25000, '113 Lê Lợi, Đà Nẵng')");



        //Show data
        Cursor dataMT = dbMT.GetData("SELECT * FROM tblMilkTea");
        while (dataMT.moveToNext()){
            int id = dataMT.getInt(0);
            String name = dataMT.getString(1);
            int price = dataMT.getInt(2);
            String location = dataMT.getString(3);
            arrayListMT.add(new MilkTea(id, name, price, location));
        }
        adapterMT.notifyDataSetChanged();

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