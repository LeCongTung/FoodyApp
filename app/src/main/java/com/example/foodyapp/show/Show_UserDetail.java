package com.example.foodyapp.show;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.foodyapp.Database;
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

        //Event: Get information
        EditText etuser = (EditText) findViewById(R.id.etsearch);
        Button btnsearch = (Button) findViewById(R.id.btncomfirm);
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String info = etuser.getText().toString().trim().toLowerCase();
                showData(info);
                if (!info.equals("tung") || milkteaArray.size() == 0)
                    Toast.makeText(Show_UserDetail.this, "Sai thông tin yêu cầu", Toast.LENGTH_SHORT).show();
                else
                    etuser.setVisibility(View.INVISIBLE);
                    btnsearch.setVisibility(View.INVISIBLE);
                    showData(info);
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
}