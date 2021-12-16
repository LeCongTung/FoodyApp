package com.example.foodyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowProduct extends AppCompatActivity {

    SQLiteHelperMT db;
    ListView lsv;
    ArrayList<MilkTea> arrayMT;
    MilkTeaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);

        lsv = (ListView) findViewById(R.id.list_item);
        arrayMT = new ArrayList<>();
        adapter = new MilkTeaAdapter(this, R.layout.item_product, arrayMT);
        lsv.setAdapter(adapter);
        ;
        db = new SQLiteHelperMT(this, "Product.sqlite", null, 1);

        db.QueryData("CREATE TABLE IF NOT EXISTS tblMilktea (idMT INTEGER PRIMARY KEY AUTOINCREMENT, nameMT VARCHAR(200), priceMT INTEGER, locationMT VARCHAR(200))");
//        db.QueryData("INSERT INTO tblMilkTea VALUES (null, 'Trà đào', 25000, '113 Lê Lợi, Đà Nẵng')");
//        db.QueryData("INSERT INTO tblMilkTea VALUES (null, 'Trà chanh', 23000, '113 Lê Lợi, Đà Nẵng')");
//        db.QueryData("INSERT INTO tblMilkTea VALUES (null, 'Trà sữa trân châu đường đen', 30000, '113 Lê Lợi, Đà Nẵng')");
//        db.QueryData("INSERT INTO tblMilkTea VALUES (null, 'Trà sữa hỗn hợp', 35000, '20 Nguyễn Văn Linh, Đà Nẵng')");
//        db.QueryData("INSERT INTO tblMilkTea VALUES (null, 'Trà sữa Hokaido', 40000, '20 Nguyễn Văn Linh, Đà Nẵng')");

        Cursor DataMilkTea = db.GetData("SELECT * FROM tblMilkTea");
        while (DataMilkTea.moveToNext()){
            int id = DataMilkTea.getInt(0);
            String name = DataMilkTea.getString(1);
            int price = DataMilkTea.getInt(2);
            String location = DataMilkTea.getString(3);
            arrayMT.add(new MilkTea(id, name, price, location));

        }
        adapter.notifyDataSetChanged();
    }

    //Event: Open Insert datas in the dialog
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_product, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.add_product){
            DialogAdd();
        }
        return super.onOptionsItemSelected(item);
    }

    private void DialogAdd(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_product);
        dialog.show();
    }
}