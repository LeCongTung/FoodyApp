package com.example.foodyapp.loading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodyapp.R;
import com.example.foodyapp.activities.Activity_Login;

public class Loading_toLogin extends AppCompatActivity {

    Handler wtf = new Handler();
    TextView title, appname;
    LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_form);

        //Animation items
        title = (TextView) findViewById(R.id.title);
        appname = (TextView) findViewById(R.id.nameapp);
        lottieAnimationView = (LottieAnimationView) findViewById(R.id.animation);

        title.animate().alpha(1).setDuration(2000).setStartDelay(1000);
        appname.animate().alpha(1).setDuration(2000).setStartDelay(1000);
        lottieAnimationView.animate().alpha(1).setDuration(2000).setStartDelay(1000);

        title.animate().alpha(0).setDuration(1000).setStartDelay(2000);
        appname.animate().alpha(0).setDuration(1000).setStartDelay(2000);
        lottieAnimationView.animate().translationX(1500).setDuration(2000).setStartDelay(2000);


        //Auto intent to login
        wtf.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(Loading_toLogin.this, Activity_Login.class);
                startActivity(i);
                overridePendingTransition(R.anim.anim_back, R.anim.anim_back_2);
                finish();
            }
        }, 3500);
    }
}