package com.example.foodyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Carousel;

import android.os.Bundle;
import android.view.View;

public class Layout_Home extends AppCompatActivity {

    private int[] slideAdver = new int[]{
            R.drawable.panel1, R.drawable.panel2, R.drawable.panel3, R.drawable.panel4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_home);

    }


}