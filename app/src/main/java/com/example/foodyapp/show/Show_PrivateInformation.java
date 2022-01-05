package com.example.foodyapp.show;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.foodyapp.Layout_Profile;
import com.example.foodyapp.R;

public class Show_PrivateInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_information);
        TextView tvBack = (TextView) findViewById(R.id.tvBack);
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Show_PrivateInformation.this, Layout_Profile.class);
                startActivity(intent);
            }
        });
    }
}