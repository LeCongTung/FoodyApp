package com.example.foodyapp.loading;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.foodyapp.Layout_Home;
import com.example.foodyapp.R;
import com.example.foodyapp.activities.Activity_Login;
import com.example.foodyapp.adapters.Adaptersliderview;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class Loading_afterRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_after_register);

        Intent intent = getIntent();
        String infoname = intent.getExtras().getString("info");

        //Slide images
        SliderView sliderView = findViewById(R.id.imageSlide);
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.welcome1);
        images.add(R.drawable.welcome2);
        images.add(R.drawable.welcome3);
        images.add(R.drawable.welcome4);

        Adaptersliderview adaptersliderview = new Adaptersliderview(images);

        sliderView.setSliderAdapter(adaptersliderview);
        sliderView.setAutoCycle(true);

        TextView btnskip = (TextView) findViewById(R.id.btnskip);
        btnskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Loading_afterRegister.this, Layout_Home.class);
                intent.putExtra("info", infoname);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_enter, R.anim.anim_enter_2);
            }
        });

    }
}