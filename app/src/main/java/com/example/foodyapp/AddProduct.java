package com.example.foodyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AddProduct extends AppCompatActivity {

   //view
    private ImageView btnback;
    private EditText inputName, inputCost;
    private Button btnaddImg, btnaddProduct;
    private SQLiteHelperMT dbMT;

    //permission constants
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;

    //pick images from device
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 102;

    //permission arrays
    private String[] cameraPermission;
    private String[] storagePermission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        //Init Permission
        cameraPermission = new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //Init View
        inputName = (EditText) findViewById(R.id.nameP);
        inputCost = (EditText) findViewById(R.id.costP);

        btnaddImg = (Button) findViewById(R.id.btnaddImage);

        btnaddProduct = (Button) findViewById(R.id.btnaddProduct);
        btnaddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbMT = new SQLiteHelperMT(AddProduct.this);

            }
        });


        btnback = (ImageView) findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddProduct.this, ShowProduct.class);
                startActivity(intent);
            }
        });


    }
}