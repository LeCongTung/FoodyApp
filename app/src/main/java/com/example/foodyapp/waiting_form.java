package com.example.foodyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.foodyapp.activities.Activity_Login;

public class waiting_form extends AppCompatActivity {

    Handler wtf = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_form);
        wtf.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(waiting_form.this, Activity_Login.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }
}