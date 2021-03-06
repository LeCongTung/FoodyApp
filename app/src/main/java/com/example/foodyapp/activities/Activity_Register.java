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
import com.example.foodyapp.loading.Loading_afterRegister;

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
                String getlocation = location.getText().toString().trim();
                if (getname.equals("") || getuser.equals("") || getnumberphone.equals("") || getpass.equals("") || getlocation.equals("")){
                    name.setText("");
                    user.setText("");
                    pass.setText("");
                    numberphone.setText("");
                    location.setText("");
                    Toast.makeText(Activity_Register.this, "Y??u c???u ?????y ????? ????ng th??ng tin", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(Activity_Register.this, "????ng k?? th??nh c??ng!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Activity_Register.this, Loading_afterRegister.class);
                                intent.putExtra("info", getuser);
                                startActivity(intent);
                            }else
                                numberphone.setText("");
                                Toast.makeText(Activity_Register.this, "S??? ??i???n tho???i ???? ???????c d??ng!", Toast.LENGTH_SHORT).show();
                        }else{
                            user.setText("");
                            pass.setText("");
                            Toast.makeText(Activity_Register.this, "???? t???n t???i t??i kho???n n??y!", Toast.LENGTH_SHORT).show();
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
                //Toast.makeText(Register_form.this, "????ng k?? th??nh c??ng", Toast.LENGTH_SHORT).show();
            }
        });
    }
}