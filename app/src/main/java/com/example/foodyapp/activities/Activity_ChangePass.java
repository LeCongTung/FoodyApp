package com.example.foodyapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodyapp.Database;
import com.example.foodyapp.Layout_Profile;
import com.example.foodyapp.R;
import com.example.foodyapp.show.Show_UserDetail;

public class Activity_ChangePass extends AppCompatActivity {

    public static Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);

        db = new Database(this, "Product.sqlite", null, 1);
        Intent intent = getIntent();
        String info = intent.getExtras().getString("info");
        int total = intent.getIntExtra("total", 0);

        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_ChangePass.this, Layout_Profile.class);
                intent.putExtra("info", info);
                intent.putExtra("total", total);
                startActivity(intent);
            }
        });

        EditText pass = (EditText) findViewById(R.id.pass);
        EditText newpass = (EditText) findViewById(R.id.newpass);
        EditText newpassagain = (EditText) findViewById(R.id.newpassagain);

        //Excute: change new password
        ImageView btnchange = (ImageView) findViewById(R.id.btnchange);
        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getoldpass = pass.getText().toString().trim();
                String getpass = newpass.getText().toString().trim();
                String trygetpass = newpassagain.getText().toString().trim();
                if (getpass.equals("") || trygetpass.equals("") || getoldpass.equals("")){
                    pass.setText("");
                    newpass.setText("");
                    newpassagain.setText("");
                    Toast.makeText(Activity_ChangePass.this, "Yêu cầu đầy đủ đúng thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    boolean i = db.checkpass(info, getoldpass);
                    if (getpass.equals(trygetpass) && !getoldpass.equals(getpass) && i==true) {
                        db.QueryData("UPDATE user SET pass = '" + trygetpass + "' WHERE user ='" + info + "'");
                        Toast.makeText(Activity_ChangePass.this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Activity_ChangePass.this, Layout_Profile.class);
                        intent.putExtra("info", info);
                        intent.putExtra("total", total);
                        startActivity(intent);
                    } else {
                        newpass.setText("");
                        newpassagain.setText("");
                        Toast.makeText(Activity_ChangePass.this, "Không trùng khớp mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}