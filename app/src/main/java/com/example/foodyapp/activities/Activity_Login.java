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
import com.example.foodyapp.Layout_Home;
import com.example.foodyapp.Layout_Profile;
import com.example.foodyapp.R;

public class Activity_Login extends AppCompatActivity {


    EditText etuser, etpass;
    Button btnlogin;
    public static Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Init elements
        etuser = (EditText) findViewById(R.id.user);
        etpass = (EditText) findViewById(R.id.pass);

        //Connect to database and create new account
        db = new Database(this, "Product.sqlite", null, 1);
        db.QueryData("CREATE TABLE IF NOT EXISTS user(idU INTEGER PRIMARY KEY AUTOINCREMENT, nameU VARCHAR(255), numberphoneU VARCHAR(10), user VARCHAR(255), pass VARCHAR(255), locationU ARCHAR(255))");

        //Event: Press to go to sign up
        TextView tvregister = (TextView) findViewById(R.id.tvregister);
        tvregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_Login.this, Activity_Register.class);
                startActivity(intent);
            }
        });

        //Event: Press to log in
        btnlogin = (Button) findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = etuser.getText().toString().trim();
                String pass = etpass.getText().toString().trim();
                if (user.equals("") || pass.equals(""))
                    Toast.makeText(Activity_Login.this, "Yêu cầu nhập đủ thông tin!", Toast.LENGTH_SHORT).show();
                else {
                    boolean i = db.checkuserpass(user, pass);
                    if (i == true) {
                        Toast.makeText(Activity_Login.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Activity_Login.this, Layout_Home.class);
                        startActivity(intent);
                    } else {
                        etpass.setText("");
                        Toast.makeText(Activity_Login.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}