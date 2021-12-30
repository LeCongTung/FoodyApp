package com.example.foodyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import com.example.foodyapp.adapters.Adapter_Cart;
import com.example.foodyapp.adapters.Adapter_ListType;
import com.example.foodyapp.units.cart;
import com.example.foodyapp.units.product;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Layout_Cart extends AppCompatActivity {

    Button btnadd;
    ListView lv;
    ArrayList<cart> milkteaArray;
    Adapter_Cart milkteaAdapter;
    public static Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_cart);

        lv = (ListView) findViewById(R.id.list_item);
        milkteaArray = new ArrayList<cart>();
        milkteaAdapter = new Adapter_Cart(this, R.layout.interface_item_incart, milkteaArray);
        lv.setAdapter(milkteaAdapter);

        //Create a database and a table with values
        db = new Database(this, "Product.sqlite", null, 1);

        showData();

        //Bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.cart);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.cart:
                        return true;

                    case R.id.history:
                        startActivity(new Intent(getApplicationContext(), Layout_History.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Layout_Home.class));
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
    }

    //Event: Show all datas
    private void showData() {
        Cursor cursor = db.GetData("SELECT * FROM cart");
        milkteaArray.clear();
        while (cursor.moveToNext()) {
            milkteaArray.add(new
                    cart(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getBlob(5)));
        }
        milkteaAdapter.notifyDataSetChanged();
    }
}