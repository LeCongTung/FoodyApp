package com.example.foodyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodyapp.adapters.Adapter_Discount;
import com.example.foodyapp.adapters.Adapter_ListType;
import com.example.foodyapp.adapters.Adapter_RecycleView;
import com.example.foodyapp.adapters.Adaptersliderview;
import com.example.foodyapp.show.Show_ListType;
import com.example.foodyapp.show.Show_Search;
import com.example.foodyapp.units.product;
import com.example.foodyapp.units.voucher;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smarteist.autoimageslider.SliderView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Layout_Home extends AppCompatActivity {

    ListView lv;
    ArrayList<product> milkteaArray;
    Adapter_Discount milkteaAdapter;
    public static Database db;
    int numberorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_home);

        lv = (ListView) findViewById(R.id.list_item);
        milkteaArray = new ArrayList<product>();
        milkteaAdapter = new Adapter_Discount(this, R.layout.interface_item_indiscount, milkteaArray);
        lv.setAdapter(milkteaAdapter);

        db = new Database(this, "Product.sqlite", null, 1);
        db.QueryData("CREATE TABLE IF NOT EXISTS cart(idC INTEGER PRIMARY KEY AUTOINCREMENT, nameC VARCHAR(255), priceC INTEGER, locationC VARCHAR(255), quantityC INTEGER, imageC BLOB)");

        Intent intent = getIntent();
        String infoname = intent.getExtras().getString("info");
        int total = intent.getIntExtra("total", 0);

        //Slide images
        SliderView sliderView = findViewById(R.id.imageSlide);
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.panel1);
        images.add(R.drawable.panel2);
        images.add(R.drawable.panel3);
        images.add(R.drawable.panel4);

        Adaptersliderview adaptersliderview = new Adaptersliderview(images);

        sliderView.setSliderAdapter(adaptersliderview);
        sliderView.setAutoCycle(true);

        //Bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomnav);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        return true;

                    case R.id.history:
                        Intent tohistory = new Intent(getApplicationContext(), Layout_History.class);
                        tohistory.putExtra("info", infoname);
                        tohistory.putExtra("total", total);
                        startActivity(tohistory);
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


        //Excution
        TextView tvsearch = (TextView) findViewById(R.id.search);
        tvsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Layout_Home.this, Show_Search.class);
                intent.putExtra("info", infoname);
                intent.putExtra("total", total);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_enter_2);
            }
        });

        Button btntorice = (Button) findViewById(R.id.selectrice);
        btntorice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Layout_Home.this, Show_ListType.class);
                intent.putExtra("type", "Cơm");
                intent.putExtra("info", infoname);
                intent.putExtra("total", total);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_enter_2);
            }
        });

        Button btntodrink = (Button) findViewById(R.id.selectdrink);
        btntodrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Layout_Home.this, Show_ListType.class);
                intent.putExtra("type", "Đồ uống");
                intent.putExtra("info", infoname);
                intent.putExtra("total", total);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_enter_2);
            }
        });

        Button btntofastfood = (Button) findViewById(R.id.selectfastfood);
        btntofastfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Layout_Home.this, Show_ListType.class);
                intent.putExtra("type", "Đồ ăn nhanh");
                intent.putExtra("info", infoname);
                intent.putExtra("total", total);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_enter_2);
            }
        });

        Button btntocake = (Button) findViewById(R.id.selectcake);
        btntocake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Layout_Home.this, Show_ListType.class);
                intent.putExtra("type", "Bánh");
                intent.putExtra("info", infoname);
                intent.putExtra("total", total);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_enter_2);
            }
        });

        Button btntofruits = (Button) findViewById(R.id.selectfruits);
        btntofruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Layout_Home.this, Show_ListType.class);
                intent.putExtra("type", "Trái cây");
                intent.putExtra("info", infoname);
                intent.putExtra("total", total);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_enter_2);
            }
        });

        Button btntospan = (Button) findViewById(R.id.selectspan);
        btntospan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Layout_Home.this, Show_ListType.class);
                intent.putExtra("type", "Đồ hộp");
                intent.putExtra("info", infoname);
                intent.putExtra("total", total);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_enter_2);
            }
        });

        //Set voucher
        RecyclerView listvoucher = findViewById(R.id.voucher);
        List<voucher> listv = new ArrayList<>();
        listv.add(new voucher(R.drawable.voucher1));
        listv.add(new voucher(R.drawable.voucher2));
        listv.add(new voucher(R.drawable.voucher1));
        listv.add(new voucher(R.drawable.voucher2));
        listv.add(new voucher(R.drawable.voucher1));
        listv.add(new voucher(R.drawable.voucher2));

        LinearLayoutManager hlist = new LinearLayoutManager(this);
        hlist.setOrientation(LinearLayoutManager.HORIZONTAL);
        listvoucher.setLayoutManager(hlist);

        Adapter_RecycleView adaptor = new Adapter_RecycleView(Layout_Home.this, listv);

        listvoucher.setAdapter(adaptor);

        showData();
    }

    private void showData() {
        Cursor cursor = db.GetData("SELECT * FROM product WHERE priceP < 35000");
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
        int perProduct = price-5000;
        etTotal.setText("" + perProduct);


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
                    Toast.makeText(Layout_Home.this, "Đã đạt mức tối thiểu", Toast.LENGTH_SHORT).show();
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

                Intent intent = new Intent(Layout_Home.this, Layout_Cart.class);
                intent.putExtra("info", info);
                intent.putExtra("total", total + cost);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_enter_2);

                dialog.dismiss();
                Toast.makeText(Layout_Home.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                showData();
            }
        });
        dialog.show();
    }

}