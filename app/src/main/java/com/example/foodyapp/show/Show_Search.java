package com.example.foodyapp.show;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodyapp.Database;
import com.example.foodyapp.Layout_Cart;
import com.example.foodyapp.R;
import com.example.foodyapp.adapters.Adapter_ListType;
import com.example.foodyapp.adapters.Adapter_Search;
import com.example.foodyapp.units.product;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Show_Search extends AppCompatActivity {

    ListView lv;
    ArrayList<product> milkteaArray;
    Adapter_Search milkteaAdapter;
    public static Database db;
    int numberorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_search);

        lv = (ListView) findViewById(R.id.list_item);
        milkteaArray = new ArrayList<product>();
        milkteaAdapter = new Adapter_Search(this, R.layout.interface_item_inlist, milkteaArray);
        lv.setAdapter(milkteaAdapter);

        //Create a database and a table with values
        db = new Database(this, "Product.sqlite", null, 1);

        //Search with key word
        Button btnsearch = (Button) findViewById(R.id.btnsearch);
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText keyword = (EditText) findViewById(R.id.etsearch);
                String info = keyword.getText().toString().trim();
                showData(info);
                if (info.equals("") || milkteaArray.size() == 0)
                    Toast.makeText(Show_Search.this, "Không có sản phẩm này", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Event: Show all datas
    private void showData(String info) {
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
                    Toast.makeText(Show_Search.this, "Đã đạt mức tối thiểu", Toast.LENGTH_SHORT).show();
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

                Show_Search.db.addC(
                        etName.getText().toString().trim(),
                        cost,
                        etLocation.getText().toString().trim(),
                        Integer.parseInt(etQuantity.getText().toString().trim()),
                        image);

                Intent intent = new Intent(Show_Search.this, Layout_Cart.class);
                intent.putExtra("info", cost);
                startActivity(intent);

                dialog.dismiss();
                Toast.makeText(Show_Search.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
}