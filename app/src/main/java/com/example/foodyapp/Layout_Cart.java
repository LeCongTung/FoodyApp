package com.example.foodyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import com.example.foodyapp.adapters.Adapter_ListType;
import com.example.foodyapp.show.Show_AddProduct;
import com.example.foodyapp.show.Show_ListType;
import com.example.foodyapp.units.cart;
import com.example.foodyapp.units.product;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Layout_Cart extends AppCompatActivity {

    Button btnadd;
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

//        Intent intent = getIntent();
//        int info = intent.getExtras().getInt("info");
//        int takevalue = info;
//        int finalcost = takevalue;
//        TextView tvtotal = (TextView) findViewById(R.id.total);
//        tvtotal.setText("Tổng tiền: " + finalcost);

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

    //Update product's quantity in cart
    public void DialogUpdate(String name, int price, int quantity , byte[] image, String location, final int id){

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_cart_detail);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setAttributes(lp);

//        Intent intent = getIntent();
//        String info = intent.getExtras().getString("info");

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
                }

            }
        });

        Button btnaddcart = (Button) dialog.findViewById(R.id.btnaddcart);
        btnaddcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int quantityC = Integer.parseInt(etQuantity.getText().toString().trim());
//                db.QueryData("UPDATE cart SET quantityC = quantityC - 1 WHERE idC ='" + id + "'");

                int cost = Integer.parseInt(etTotal.getText().toString().trim());

//                Intent intent = new Intent(Layout_Cart.this, Layout_Cart.class);
//                intent.putExtra("info", cost);
//                startActivity(intent);

                dialog.dismiss();
                showData();
                Toast.makeText(Layout_Cart.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
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