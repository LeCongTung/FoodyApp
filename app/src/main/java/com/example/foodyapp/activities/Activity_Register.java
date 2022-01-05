package com.example.foodyapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodyapp.Database;
import com.example.foodyapp.R;

public class Activity_Register extends AppCompatActivity {

    EditText name, numberphone, user, pass, location;
    Button btnregister;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);

        //Connect to database
        db = new Database(this, "Product.sqlite", null, 1);

        //Init Elements
        name = (EditText) findViewById(R.id.name);
        user = (EditText) findViewById(R.id.user);
        numberphone = (EditText) findViewById(R.id.phonenumber);
        pass = (EditText) findViewById(R.id.pass);
        location = (EditText) findViewById(R.id.location);

        //Go to layout login
        btnregister = (Button) findViewById(R.id.btnregister);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getname = name.getText().toString().trim();
                String getuser = user.getText().toString().trim().toLowerCase();
                String getnumberphone = numberphone.getText().toString().trim().toLowerCase();
                String getpass = pass.getText().toString().trim().toLowerCase();
                String getlocation = location.getText().toString().trim().toLowerCase();
                if (getname.equals("") || getuser.equals("") || getnumberphone.equals("") || getpass.equals("") || getlocation.equals("")){
                    name.setText("");
                    user.setText("");
                    pass.setText("");
                    numberphone.setText("");
                    location.setText("");
                    Toast.makeText(Activity_Register.this, "Yêu cầu đầy đủ đúng thông tin", Toast.LENGTH_SHORT).show();
                }else{
                        boolean i = db.checkuser(getuser);
                        boolean y = db.checkphonenumber(getnumberphone);
                        if (i == false) {
                            if (y == false){
                                Activity_Login.db.addU(
                                        getname,
                                        getnumberphone,
                                        getuser,
                                        getpass,
                                        getlocation);
                                Toast.makeText(Activity_Register.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Activity_Register.this, Activity_Login.class));
                            }else
                                numberphone.setText("");
                                Toast.makeText(Activity_Register.this, "Số điện thoại đã được dùng!", Toast.LENGTH_SHORT).show();
                        }else{
                            user.setText("");
                            pass.setText("");
                            Toast.makeText(Activity_Register.this, "Đã tồn tại tài khoản này!", Toast.LENGTH_SHORT).show();
                        }

                }
            }
        });

        //Back to layout login
        TextView tvLogin = (TextView) findViewById(R.id.tvLogin);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Register.this, Activity_Login.class);
                startActivity(intent);
                //Toast.makeText(Register_form.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }
}