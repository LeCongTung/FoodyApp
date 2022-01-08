package com.example.foodyapp.show;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.foodyapp.Database;
import com.example.foodyapp.Layout_Home;
import com.example.foodyapp.Layout_Profile;
import com.example.foodyapp.R;
import com.example.foodyapp.adapters.Adapter_ListType;
import com.example.foodyapp.adapters.Adapter_Search;
import com.example.foodyapp.adapters.Adapter_User;
import com.example.foodyapp.units.product;
import com.example.foodyapp.units.user;

import java.util.ArrayList;

public class Show_UserDetail extends AppCompatActivity {

    ListView lv;
    ArrayList<user> milkteaArray;
    Adapter_User milkteaAdapter;
    public static Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_detail);

        lv = (ListView) findViewById(R.id.list_item);
        milkteaArray = new ArrayList<user>();
        milkteaAdapter = new Adapter_User(this, R.layout.interface_user, milkteaArray);
        lv.setAdapter(milkteaAdapter);

        //Create a database and a table with values
        db = new Database(this, "Product.sqlite", null, 1);
        Intent intent = getIntent();
        String info = intent.getExtras().getString("info");
        int total = intent.getIntExtra("total", 0);

        //Event: Get information
        showData(info);

        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Show_UserDetail.this, Layout_Profile.class);
                intent.putExtra("info", info);
                intent.putExtra("total", total);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_back, R.anim.anim_back_2);
            }
        });

    }

    //Event: Show all datas
    private void showData(String info) {
        Cursor cursor = db.GetData("SELECT * FROM user WHERE user LIKE '" + info + "'");
        milkteaArray.clear();
        while (cursor.moveToNext()) {
            milkteaArray.add(new
                    user(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)));
        }
        milkteaAdapter.notifyDataSetChanged();
    }

    //Event: update a data
    public void DialogUpdate(String name, String phonenumber, String user, String location, final int id) {

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit_user);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);

        //Init elements
        EditText tvname = (EditText) dialog.findViewById(R.id.name);
        EditText tvphonenumber = (EditText) dialog.findViewById(R.id.phonenumber);
        EditText tvlocation = (EditText) dialog.findViewById(R.id.location);

        //Show datas before update
        tvname.setText(name);
        tvphonenumber.setText(phonenumber);
        tvlocation.setText(location);

        //Event: Update datas by pressing update button
        Button btnupdate = (Button) dialog.findViewById(R.id.btnUpdate);
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameP = tvname.getText().toString().trim();
                String phonenumberU = tvphonenumber.getText().toString().trim();
                String locationP = tvlocation.getText().toString().trim();

                if (nameP.equals("") || phonenumberU.equals("") || user.equals("") || locationP.equals("")){
                    Toast.makeText(Show_UserDetail.this, "Yêu cầu điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    db.QueryData("UPDATE user SET nameU = '" + nameP + "', numberphoneU = '" + phonenumberU + "', locationU = '" + locationP + "' WHERE idU ='" + id + "'");
                    Toast.makeText(Show_UserDetail.this, "Đã cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    Intent intent = getIntent();

                    String info = intent.getExtras().getString("info");
                    int total = intent.getIntExtra("total", 0);
                    showData(info);
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
}