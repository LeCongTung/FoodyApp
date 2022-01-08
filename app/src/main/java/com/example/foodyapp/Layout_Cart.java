package com.example.foodyapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodyapp.adapters.Adapter_Cart;
import com.example.foodyapp.loading.Loading_toHistory;
import com.example.foodyapp.units.cart;
import com.example.foodyapp.units.product;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Layout_Cart extends AppCompatActivity {

    ListView lv;
    ArrayList<cart> milkteaArray;
    Adapter_Cart milkteaAdapter;
    public static Database db;
    int quantityOrder = 0;

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
        db.QueryData("CREATE TABLE IF NOT EXISTS cart(idC INTEGER PRIMARY KEY AUTOINCREMENT, nameC VARCHAR(255), priceC INTEGER, locationC VARCHAR(255), quantityC INTEGER, imageC BLOB)");
        db.QueryData("CREATE TABLE IF NOT EXISTS history(idH INTEGER PRIMARY KEY AUTOINCREMENT, user VARCHAR(255), totalH INTEGER, timeH VARCHAR(255))");

        Intent intent = getIntent();
        String infoname = intent.getExtras().getString("info");
        int total = intent.getIntExtra("total", 0);

        //Show total
        TextView tvtotal = (TextView) findViewById(R.id.total);
        tvtotal.setText("" + total);

        int getTotal = Integer.parseInt(tvtotal.getText().toString());


//        Intent intent = get Intent();
//        int info = intent.getExtras().getInt("info");
//        int takevalue = info;
//        int finalcost = takevalue;
//        TextView tvtotal = (TextView) findViewById(R.id.total);
//        tvtotal.setText("Tổng tiền: " + finalcost);

        showData();

        Button btnpay = (Button) findViewById(R.id.btnpay);
        btnpay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                Intent getvalue = getIntent();
                String infoname = getvalue.getExtras().getString("info");
                int price = Integer.parseInt(tvtotal.getText().toString());
                if (milkteaArray.size() != 0){
                    LocalDateTime current = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
                    String formatted = current.format(formatter);

                    Layout_Cart.db.addH(infoname, price, formatted);

                    Intent intent = new Intent(Layout_Cart.this, Loading_toHistory.class);
                    intent.putExtra("info", infoname);
                    intent.putExtra("total", 0);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_enter, R.anim.anim_enter_2);

                    tvtotal.setText("0");
                    db.QueryData("DELETE FROM cart");
                }else
                    Toast.makeText(Layout_Cart.this, "Giỏ hàng rỗng", Toast.LENGTH_SHORT).show();

            }
        });

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
                        Intent tohistory = new Intent(getApplicationContext(), Layout_History.class);
                        tohistory.putExtra("info", infoname);
                        tohistory.putExtra("total", getTotal);
                        startActivity(tohistory);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        Intent tohome = new Intent(getApplicationContext(), Layout_Home.class);
                        tohome.putExtra("info", infoname);
                        tohome.putExtra("total", getTotal);
                        startActivity(tohome);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.account:
                        Intent toaccount = new Intent(getApplicationContext(), Layout_Profile.class);
                        toaccount.putExtra("info", infoname);
                        toaccount.putExtra("total", getTotal);
                        startActivity(toaccount);
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

    //Update product's quantity in cart
    public void DialogUpdate(String name, int price, int quantity , byte[] image, String location, final int id){

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_cart_detail);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);

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
        etQuantity.setText("" + quantity);
        etTotal.setText("" + price);
        int perProduct = price/quantity;

        imageView.setImageBitmap(bm);
        etLocation.setText(location);

        quantityOrder = Integer.parseInt(etQuantity.getText().toString().trim());

        //Event: Plus one more product
        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantityOrder++;
                etQuantity.setText(String.valueOf(quantityOrder));
                int total = Integer.parseInt(etTotal.getText().toString().trim());
                total += perProduct;
                etTotal.setText(""+ total);
                db.QueryData("UPDATE cart SET quantityC = quantityC + 1 WHERE idC ='" + id + "'");
                db.QueryData("UPDATE cart SET priceC = '"+ total +"' WHERE idC ='" + id + "'");

            }
        });

        //Event: Minus one less product
        btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantityOrder == 1){
                    etTotal.setText(String.valueOf(perProduct));
                    Toast.makeText(Layout_Cart.this, "Đã đạt mức tối thiểu", Toast.LENGTH_SHORT).show();
                }else{
                    quantityOrder--;
                    etQuantity.setText(String.valueOf(quantityOrder));
                    int total = Integer.parseInt(etTotal.getText().toString().trim());
                    total-= perProduct;
                    etTotal.setText(""+ total);
                    db.QueryData("UPDATE cart SET quantityC = quantityC - 1 WHERE idC ='" + id + "'");
                    db.QueryData("UPDATE cart SET priceC = '"+ total +"' WHERE idC ='" + id + "'");

                }

            }
        });

        Button btnaddcart = (Button) dialog.findViewById(R.id.btnaddcart);
        btnaddcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cost = Integer.parseInt(etTotal.getText().toString().trim());

                //Get total price before change quantity
                Intent intent = getIntent();
                String infoname = intent.getExtras().getString("info");
                int total = intent.getIntExtra("total", 0);
                int totalbefore = total - price;
                int getValue = totalbefore + cost;

                dialog.dismiss();
                showData();
                Toast.makeText(Layout_Cart.this, "Đã thay đổi!", Toast.LENGTH_SHORT).show();

                Intent tovalue = new Intent(Layout_Cart.this, Layout_Cart.class);
                tovalue.putExtra("info", infoname);
                tovalue.putExtra("total", getValue);
                startActivity(tovalue);
                overridePendingTransition(R.anim.anim_blur, R.anim.anim_blur);
            }
        });
        dialog.show();
    }

    //Event: Delete a data
    public void DialogDelete(String name, int price, int id) {

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
                //Get data from intent
                Intent intent = getIntent();
                String infoname = intent.getExtras().getString("info");
                int total = intent.getIntExtra("total", 0);
                total -= price;
                TextView tvtotal = (TextView) findViewById(R.id.total);

                db.QueryData("DELETE FROM cart WHERE idC ='" + id + "'");
                Toast.makeText(Layout_Cart.this, "Đã xóa thành công!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                showData();

                Intent tovalue = new Intent(Layout_Cart.this, Layout_Cart.class);
                tovalue.putExtra("info", infoname);
                tovalue.putExtra("total", total);
                startActivity(tovalue);
                overridePendingTransition(R.anim.anim_blur, R.anim.anim_blur);

                tvtotal.setText("" + total);
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