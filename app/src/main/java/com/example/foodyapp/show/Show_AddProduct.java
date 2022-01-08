package com.example.foodyapp.show;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodyapp.Database;
import com.example.foodyapp.Layout_Profile;
import com.example.foodyapp.R;
import com.example.foodyapp.activities.Activity_AddProduct;
import com.example.foodyapp.adapters.Adapter_Product;
import com.example.foodyapp.units.product;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Show_AddProduct extends AppCompatActivity {

    Button btnadd;
    ListView lv;
    ArrayList<product> milkteaArray;
    Adapter_Product milkteaAdapter;
    public static Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_add_product);

        lv = (ListView) findViewById(R.id.list_item);
        milkteaArray = new ArrayList<>();
        milkteaAdapter = new Adapter_Product(this, R.layout.interface_item, milkteaArray);
        lv.setAdapter(milkteaAdapter);

        //Create a database and a table with values
        db = new Database(this, "Product.sqlite", null, 1);
        db.QueryData("CREATE TABLE IF NOT EXISTS product(idP INTEGER PRIMARY KEY AUTOINCREMENT, nameP VARCHAR(255), priceP INTEGER, typeP VARCHAR(255), locationP VARCHAR(255), descriptionP VARCHAR(255), imageP BLOB)");

        Intent intent = getIntent();
        String info = intent.getExtras().getString("info");
        int total = intent.getIntExtra("total", 0);

        //Call class show datas to list view
        showData();

        //Event: Add a product
        btnadd = (Button) findViewById(R.id.btnadd);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Show_AddProduct.this, Activity_AddProduct.class);
                intent.putExtra("info", info);
                intent.putExtra("total", total);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_enter_2);
            }
        });

        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Show_AddProduct.this, Layout_Profile.class);
                intent.putExtra("info", info);
                intent.putExtra("total", total);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_back, R.anim.anim_back_2);
            }
        });

        //Event: Search data and refresh
        ImageView sort = (ImageView) findViewById(R.id.sort);
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogSearch();
            }
        });

        ImageView refresh = (ImageView) findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showData();
            }
        });
    }

    //Event: Show all datas
    private void showData() {
        Cursor cursor = db.GetData("SELECT * FROM product");
        milkteaArray.clear();
        while (cursor.moveToNext()) {
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

    //Event: Search datas
    public void DialogSearch(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_search_product);

        //Init elements
        EditText etSearch = (EditText) dialog.findViewById(R.id.search);
        Spinner selection = (Spinner) dialog.findViewById(R.id.selection);
        String arr[] = {"Tên sản phẩm", "Loại sản phẩm","Giá tăng dần", "Giá giảm dần"};

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        selection.setAdapter(adapter);
        selection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Event: Search datas by pressing search button
        Button btnsearch = (Button) dialog.findViewById(R.id.btnSearch);
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info = etSearch.getText().toString().trim();
                String arm = selection.getSelectedItem().toString().trim();
                if (arm.equals("Tên sản phẩm")) {
                    Cursor cursor = db.GetData("SELECT * FROM product WHERE nameP LIKE '%" + info + "%'");
                    milkteaArray.clear();
                    while (cursor.moveToNext()) {
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
                    milkteaAdapter = new Adapter_Product(Show_AddProduct.this, R.layout.interface_item, milkteaArray);
                    lv.setAdapter(milkteaAdapter);

                }else if (arm.equals("Giá tăng dần") && info.isEmpty()){
                    Cursor cursor = db.GetData("SELECT * FROM product ORDER BY priceP ASC");
                    milkteaArray.clear();
                    milkteaArray.clear();
                    while (cursor.moveToNext()) {
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
                    milkteaAdapter = new Adapter_Product(Show_AddProduct.this, R.layout.interface_item, milkteaArray);
                    lv.setAdapter(milkteaAdapter);

                }else if (arm.equals("Giá tăng dần")){
                    Cursor cursor = db.GetData("SELECT * FROM product WHERE priceP >= '"+ info +"' ORDER BY priceP ASC");
                    milkteaArray.clear();
                    while (cursor.moveToNext()) {
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
                    milkteaAdapter = new Adapter_Product(Show_AddProduct.this, R.layout.interface_item, milkteaArray);
                    lv.setAdapter(milkteaAdapter);

                }else if (arm.equals("Giá giảm dần") && info.isEmpty()){
                    Cursor cursor = db.GetData("SELECT * FROM product ORDER BY priceP DESC");
                    milkteaArray.clear();
                    while (cursor.moveToNext()) {
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
                    milkteaAdapter = new Adapter_Product(Show_AddProduct.this, R.layout.interface_item, milkteaArray);
                    lv.setAdapter(milkteaAdapter);

                }else if (arm.equals("Giá giảm dần")) {
                    Cursor cursor = db.GetData("SELECT * FROM product WHERE priceP <= '" + info + "' ORDER BY priceP DESC");
                    milkteaArray.clear();
                    while (cursor.moveToNext()) {
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
                    milkteaAdapter = new Adapter_Product(Show_AddProduct.this, R.layout.interface_item, milkteaArray);
                    lv.setAdapter(milkteaAdapter);

                }else if (arm.equals("Loại sản phẩm")) {
                    Cursor cursor = db.GetData("SELECT * FROM product WHERE typeP LIKE '%" + info + "%'");
                    milkteaArray.clear();
                    while (cursor.moveToNext()) {
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
                    milkteaAdapter = new Adapter_Product(Show_AddProduct.this, R.layout.interface_item, milkteaArray);
                    lv.setAdapter(milkteaAdapter);

                }
                dialog.dismiss();
                int quantity = milkteaArray.size();
                Toast.makeText(Show_AddProduct.this, "Đã tìm thấy "+quantity+" sản phẩm", Toast.LENGTH_SHORT).show();
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

    //Event: update a data
    public void DialogUpdate(String name, int price, String type, String location, String description, byte[] image, final int id) {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_update_product);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);

        //Init elements
        EditText tvname = (EditText) dialog.findViewById(R.id.name);
        EditText tvprice = (EditText) dialog.findViewById(R.id.price);
        Spinner tvtype = (Spinner) dialog.findViewById(R.id.type);
        EditText tvlocation = (EditText) dialog.findViewById(R.id.location);
        EditText tvdescription = (EditText) dialog.findViewById(R.id.description);

        ImageView imageView = (ImageView) dialog.findViewById(R.id.image);
        Bitmap bm = BitmapFactory.decodeByteArray(image, 0, image.length);

        //Permission: Use a camera and choose image from galley

        //Set text in spinner
        String arr[] = {"Đồ uống", "Cơm", "Đồ ăn nhanh", "Bánh", "Trái cây", "Đồ hộp"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Show_AddProduct.this, android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        tvtype.setAdapter(adapter);
        tvtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //Show datas before update
        tvname.setText(name);
        tvprice.setText("" + price);
        tvlocation.setText(location);
        tvdescription.setText(description);
        imageView.setImageBitmap(bm);

        //Event: Update datas by pressing update button
        Button btnupdate = (Button) dialog.findViewById(R.id.btnadd);
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameP = tvname.getText().toString().trim();
                int priceP = Integer.parseInt(tvprice.getText().toString().trim());
                String typeP = tvtype.getSelectedItem().toString().trim();
                String locationP = tvlocation.getText().toString().trim();
                String descriptionP = tvdescription.getText().toString().trim();

                if (nameP.equals("") || priceP <= 0 || typeP.equals("") || locationP.equals("") || descriptionP.equals("")){
                    Toast.makeText(Show_AddProduct.this, "Yêu cầu điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    db.QueryData("UPDATE product SET nameP = '" + nameP + "', priceP = '" + priceP + "', typeP = '"+ typeP +"', locationP = '" + locationP + "', descriptionP = '" + descriptionP + "' WHERE idP ='" + id + "'");
                    Toast.makeText(Show_AddProduct.this, "Đã cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    showData();
                }
            }
        });

        //Event: Close the dialog
        Button btncancel = (Button) dialog.findViewById(R.id.btnCancel);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //Event: Delete a data
    public void DialogDelete(String name, int id) {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_delete_product);

        //Init elements
        TextView etName = (TextView) dialog.findViewById(R.id.title_name);

        etName.setText("Bạn có chắc muốn xóa \"" + name + "\" không?");

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