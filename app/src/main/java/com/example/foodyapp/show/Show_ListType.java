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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
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
import com.example.foodyapp.Layout_Cart;
import com.example.foodyapp.Layout_Home;
import com.example.foodyapp.Layout_Profile;
import com.example.foodyapp.R;
import com.example.foodyapp.adapters.Adapter_ListType;
import com.example.foodyapp.adapters.Adapter_Product;
import com.example.foodyapp.units.cart;
import com.example.foodyapp.units.product;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Show_ListType extends AppCompatActivity {

    ListView lv;
    ArrayList<product> milkteaArray;
    Adapter_ListType milkteaAdapter;
    public static Database db;
    int numberorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_type);

        lv = (ListView) findViewById(R.id.list_item);
        milkteaArray = new ArrayList<product>();
        milkteaAdapter = new Adapter_ListType(this, R.layout.interface_item_inlist, milkteaArray);
        lv.setAdapter(milkteaAdapter);

        //Create a database and a table with values
        db = new Database(this, "Product.sqlite", null, 1);
        db.QueryData("CREATE TABLE IF NOT EXISTS cart(idC INTEGER PRIMARY KEY AUTOINCREMENT, nameC VARCHAR(255), priceC INTEGER, locationC VARCHAR(255), quantityC INTEGER, imageC BLOB)");

        //Call class show datas to list view
        Intent intent = getIntent();
        String type = intent.getExtras().getString("type");
        String info = intent.getExtras().getString("info");
        int total = intent.getIntExtra("total", 0);
        showData(type);

        TextView tvtypename = (TextView) findViewById(R.id.typename);
        tvtypename.setText(type);

        Button btntocard = (Button) findViewById(R.id.btntocart);
        btntocard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Show_ListType.this, Layout_Cart.class);
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
                Intent intent = new Intent(Show_ListType.this, Layout_Home.class);
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
                showData(type);
            }
        });
    }

    //Event: Show all datas
    private void showData(String type) {
        Cursor cursor = db.GetData("SELECT * FROM product WHERE typeP LIKE '%" + type + "%'");
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


    public void DialogSearch(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_search_product);

        Intent intent = getIntent();
        String more = intent.getExtras().getString("type");

        //Init elements
        EditText etSearch = (EditText) dialog.findViewById(R.id.search);
        Spinner selection = (Spinner) dialog.findViewById(R.id.selection);
        String arr[] = {"Tên sản phẩm","Giá tăng dần", "Giá giảm dần"};

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
                String name = etSearch.getText().toString().trim();
                String arm = selection.getSelectedItem().toString().trim();
                if (arm.equals("Tên sản phẩm")) {
                    Cursor cursor = db.GetData("SELECT * FROM product WHERE nameP LIKE '%" + name + "%' AND typeP LIKE '%" + more + "%'");
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
                    milkteaAdapter = new Adapter_ListType(Show_ListType.this, R.layout.interface_item_inlist, milkteaArray);
                    lv.setAdapter(milkteaAdapter);

                }else if (arm.equals("Giá tăng dần") && name.isEmpty()){
                    Cursor cursor = db.GetData("SELECT * FROM product WHERE typeP LIKE '%" + more + "%' ORDER BY priceP ASC");
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
                    milkteaAdapter = new Adapter_ListType(Show_ListType.this, R.layout.interface_item_inlist, milkteaArray);
                    lv.setAdapter(milkteaAdapter);

                }else if (arm.equals("Giá tăng dần")){
                    Cursor cursor = db.GetData("SELECT * FROM product WHERE priceP >= '"+ name +"' AND typeP LIKE '%" + more + "%' ORDER BY priceP ASC");
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
                    milkteaAdapter = new Adapter_ListType(Show_ListType.this, R.layout.interface_item_inlist, milkteaArray);
                    lv.setAdapter(milkteaAdapter);

                }else if (arm.equals("Giá giảm dần") && name.isEmpty()){
                    Cursor cursor = db.GetData("SELECT * FROM product WHERE typeP LIKE '%" + more + "%' ORDER BY priceP DESC");
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
                    milkteaAdapter = new Adapter_ListType(Show_ListType.this, R.layout.interface_item_inlist, milkteaArray);
                    lv.setAdapter(milkteaAdapter);

                }else if (arm.equals("Giá giảm dần")) {
                    Cursor cursor = db.GetData("SELECT * FROM product WHERE priceP <= '" + name + "' AND typeP LIKE '%" + more + "%' ORDER BY priceP DESC");
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
                    milkteaAdapter = new Adapter_ListType(Show_ListType.this, R.layout.interface_item_inlist, milkteaArray);
                    lv.setAdapter(milkteaAdapter);
                }
                int quantity = milkteaArray.size();
                Toast.makeText(Show_ListType.this, "Đã tìm thấy "+quantity+" sản phẩm", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
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



    public void DialogPay(String name, int price , byte[] image, String location, String description, final int id){

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_cart_detail);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);

        numberorder = 1;
        Intent intent = getIntent();
        String info = intent.getExtras().getString("info");
        int total = intent.getIntExtra("total", 0);

        //Select a position for dialog
        Window window = dialog.getWindow();
        WindowManager.LayoutParams param = window.getAttributes();
        param.width = WindowManager.LayoutParams.MATCH_PARENT;
        param.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.BOTTOM);
        dialog.setContentView(R.layout.dialog_cart_detail);
        dialog.setCancelable(true);


        //Init elements
        TextView etName = (TextView) dialog.findViewById(R.id.name);
        TextView etQuantity = (TextView) dialog.findViewById(R.id.quantity);
        TextView etTotal = (TextView) dialog.findViewById(R.id.total);
        TextView etDescription = (TextView) dialog.findViewById(R.id.detail);
        TextView etLocation = (TextView) dialog.findViewById(R.id.location);

        Button btnplus = (Button) dialog.findViewById(R.id.btnplus);
        Button btnminus = (Button) dialog.findViewById(R.id.btnminus);

        ImageView imageView = (ImageView) dialog.findViewById(R.id.showImage);
        Bitmap bm = BitmapFactory.decodeByteArray(image, 0, image.length);

        //Show datas before add to cart
        etName.setText(name);
        etQuantity.setText("1");
        etTotal.setText("" + price);
        int perProduct = price;

        imageView.setImageBitmap(bm);

        etDescription.setText(description);
        etLocation.setText("Địa chỉ: "  + location);

        //Event: Plus one more product
        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberorder++;
                etQuantity.setText(String.valueOf(numberorder));
                int total = Integer.parseInt(etTotal.getText().toString().trim());
                total += perProduct;
                etTotal.setText(""+ total);
                db.QueryData("UPDATE cart SET quantityC = quantityC + 1 WHERE idC ='" + id + "'");

            }
        });

        //Event: Minus one less product
        btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberorder == 1){
                    etTotal.setText(String.valueOf(perProduct));
                    Toast.makeText(Show_ListType.this, "Đã đạt mức tối thiểu", Toast.LENGTH_SHORT).show();
                }else{
                    numberorder--;
                    etQuantity.setText(String.valueOf(numberorder));
                    int total = Integer.parseInt(etTotal.getText().toString().trim());
                    total-= perProduct;
                    etTotal.setText(""+ total);
                    db.QueryData("UPDATE cart SET quantityC = quantityC - 1 WHERE idC ='" + id + "'");
                }
            }
        });

        Button btnaddcart = (Button) dialog.findViewById(R.id.btnaddcart);
        btnaddcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable bm = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = bm.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] image = byteArrayOutputStream.toByteArray();

                int cost = Integer.parseInt(etTotal.getText().toString().trim());

                Show_ListType.db.addC(
                        etName.getText().toString().trim(),
                        cost,
                        etLocation.getText().toString().trim(),
                        Integer.parseInt(etQuantity.getText().toString().trim()),
                        image);

                Intent intent = new Intent(Show_ListType.this, Layout_Cart.class);
                intent.putExtra("info", info);
                intent.putExtra("total", total + cost);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_enter_2);

                dialog.dismiss();
                Toast.makeText(Show_ListType.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                showData(info);
            }
        });
        dialog.show();
    }
}