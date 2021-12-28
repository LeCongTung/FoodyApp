package com.example.foodyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.foodyapp.adapters.Adapter_Product;
import com.example.foodyapp.adapters.Adaptersliderview;
import com.example.foodyapp.units.product;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class Layout_Home extends AppCompatActivity {

    RecyclerView rcv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_home);

        //Slide images
        SliderView sliderView = findViewById(R.id.imageSlide);
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.panel1);
        images.add(R.drawable.panel2);
        images.add(R.drawable.panel3);
        images.add(R.drawable.panel4);

        Adaptersliderview adaptersliderview = new Adaptersliderview(images);

        sliderView.setSliderAdapter(adaptersliderview);
        sliderView.setAutoCycle(true);

        //RecycleView products
        LinearLayoutManager layoutmanager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcv = (RecyclerView) findViewById(R.id.bestChoice);
        rcv.setLayoutManager(layoutmanager);

    }


}