package com.example.foodyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodyapp.adapters.Adaptermilktea;
import com.example.foodyapp.units.product;

import java.util.ArrayList;

public class Show_AddProduct extends AppCompatActivity {

    Button btnadd;
    ListView lv;
    ArrayList<product> milkteaArray;
    Adaptermilktea milkteaAdapter;
    public static Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_add_product);

        lv = (ListView) findViewById(R.id.list_item);
        milkteaArray = new ArrayList<>();
        milkteaAdapter = new Adaptermilktea(this, R.layout.interface_item, milkteaArray);
        lv.setAdapter(milkteaAdapter);

        //Create a database and a table with values
        db = new Database(this, "Product.sqlite", null, 1);
        db.QueryData("CREATE TABLE IF NOT EXISTS product(idP INTEGER PRIMARY KEY AUTOINCREMENT, nameP VARCHAR(255), priceP INTEGER, typeP VARCHAR(255), locationP VARCHAR(255), descriptionMT VARCHAR(255), imageMT BLOB)");


        //Call class show datas to list view
        showData();

        //Event: Add a product
        btnadd = (Button) findViewById(R.id.btnadd);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Show_AddProduct.this, Activity_AddProduct.class);
                startActivity(intent);
            }
        });
    }

    //Event: Show all datas
    private void showData(){
        Cursor cursor = db.GetData("SELECT * FROM product");
        milkteaArray.clear();
        while (cursor.moveToNext()){
            milkteaArray.add(new
                    product(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getBlob(6)));
        }
        milkteaAdapter.notifyDataSetChanged();
    }

    //Event: Delete a data
    public void DialogDelete(String name, int id){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_delete_product);

        //Init elements
        TextView etName = (TextView) dialog.findViewById(R.id.title_name);

        etName.setText("Bạn có chắc muốn xóa \""+ name +"\" không?");

        //Event: Delete datas by pressing delete button
        Button btndelete = (Button) dialog.findViewById(R.id.btnDelete);
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.QueryData("DELETE FROM product WHERE idP ='" + id + "'");
                Toast.makeText(Show_AddProduct.this, "Đã xóa thành công!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                showData();
            }
        });

        Button btncancel = (Button) dialog.findViewById(R.id.btnCancel);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}