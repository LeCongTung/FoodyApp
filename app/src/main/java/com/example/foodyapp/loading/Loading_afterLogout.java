package com.example.foodyapp.loading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.foodyapp.R;
import com.example.foodyapp.activities.Activity_Login;

public class Loading_afterLogout extends AppCompatActivity {

    Handler wtf = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_after_logout);

        //Auto intent to login
        wtf.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Loading_afterLogout.this, Activity_Login.class);
                startActivity(i);
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_enter_2);
                finish();
            }
        }, 3000);
    }
}