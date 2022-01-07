package com.example.foodyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.foodyapp.adapters.Adapter_Cart;
import com.example.foodyapp.adapters.Adapter_History;
import com.example.foodyapp.units.cart;
import com.example.foodyapp.units.history;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Layout_History extends AppCompatActivity {

    ListView lv;
    ArrayList<history> milkteaArray;
    Adapter_History milkteaAdapter;
    public static Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_history);

        lv = (ListView) findViewById(R.id.list_item);
        milkteaArray = new ArrayList<history>();
        milkteaAdapter = new Adapter_History(this, R.layout.interface_history, milkteaArray);
        lv.setAdapter(milkteaAdapter);

        //Create a database and a table with values
        db = new Database(this, "Product.sqlite", null, 1);

        Intent intent = getIntent();
        String infoname = intent.getExtras().getString("info");
        int total = intent.getIntExtra("total", 0);

        //Bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.history);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.history:
                        return true;

                    case R.id.home:
                        Intent tohome = new Intent(getApplicationContext(), Layout_Home.class);
                        tohome.putExtra("info", infoname);
                        tohome.putExtra("total", total);
                        startActivity(tohome);
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

        showData(infoname);
    }

    //Event: Show all datas
    private void showData(String info) {
        Cursor cursor = db.GetData("SELECT * FROM history WHERE user LIKE '" + info + "'");
        milkteaArray.clear();
        while (cursor.moveToNext()) {
            milkteaArray.add(new
                    history(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3)));
        }
        milkteaAdapter.notifyDataSetChanged();
    }
}