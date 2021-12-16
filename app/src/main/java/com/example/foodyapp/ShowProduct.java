package com.example.foodyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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
    //        db.QueryData("INSERT INTO tblMilkTea VALUES (null, 'Trà sữa dâu', 34000, '15 Huỳnh Thúc Kháng, Đà Nẵng')");
    //        db.QueryData("INSERT INTO tblMilkTea VALUES (null, 'Trà sữa chocolate', 34000, '15 Huỳnh Thúc Kháng, Đà Nẵng')");

        SelectData();
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

    //Show data to listview
    private void SelectData(){
        Cursor DataMilkTea = db.GetData("SELECT * FROM tblMilkTea");
        arrayMT.clear();
        while (DataMilkTea.moveToNext()){
            int id = DataMilkTea.getInt(0);
            String name = DataMilkTea.getString(1);
            int price = DataMilkTea.getInt(2);
            String location = DataMilkTea.getString(3);
            arrayMT.add(new MilkTea(id, name, price, location));

        }
        adapter.notifyDataSetChanged();
    }

    //Show the add product dialog
    private void DialogAdd(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_product);

        //Init elements
        EditText etName = (EditText) dialog.findViewById(R.id.etname);
        EditText etPrice = (EditText) dialog.findViewById(R.id.etprice);
        EditText etLocation = (EditText) dialog.findViewById(R.id.etlocation);

        //Event: Insert datas by pressing add button
        Button btnadd = (Button) dialog.findViewById(R.id.btnAdd);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString().trim();
                int price = Integer.parseInt(etPrice.getText().toString().trim());
                String location = etLocation.getText().toString().trim();

                if (name.equals("") || location.equals("")){
                    Toast.makeText(ShowProduct.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else{
                    db.QueryData("INSERT INTO tblMilktea VALUES (null, '"+ name +"', '"+ price +"', '"+ location +"')");
                    Toast.makeText(ShowProduct.this, "Đã thêm thành công!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    SelectData();
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

    //Show the update product dialog
    public void DialogUpdate(String name, int price, String location, final int id){

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_update_product);

        //Init elements
        EditText etName = (EditText) dialog.findViewById(R.id.etname);
        EditText etPrice = (EditText) dialog.findViewById(R.id.etprice);
        EditText etLocation = (EditText) dialog.findViewById(R.id.etlocation);

        //Show datas before update
        etName.setText(name);
        etPrice.setText("" + price);
        etLocation.setText(location);

        //Event: Update datas by pressing update button
        Button btnupdate = (Button) dialog.findViewById(R.id.btnUpdate);
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameC = etName.getText().toString().trim();
                int priceC = Integer.parseInt(etPrice.getText().toString().trim());
                String locationC = etLocation.getText().toString().trim();
                if (name.equals("") || location.equals("")){
                    Toast.makeText(ShowProduct.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    db.QueryData("UPDATE tblMilktea SET nameMT = '" + nameC + "', priceMT = '"+ priceC +"', locationMT = '" + locationC + "' WHERE idMT ='" + id + "'");
                    Toast.makeText(ShowProduct.this, "Đã cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    SelectData();
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

    //Show the delete product dialog
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
                db.QueryData("DELETE FROM tblMilktea WHERE idMT ='" + id + "'");
                Toast.makeText(ShowProduct.this, "Đã xóa thành công!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                SelectData();
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