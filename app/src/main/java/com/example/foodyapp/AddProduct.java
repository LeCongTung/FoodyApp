package com.example.foodyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class AddProduct extends AppCompatActivity {

   //View
    private ImageView btnback, showImg;
    private EditText inputName, inputCost, inputLocation;
    private Button btnaddProduct, btnaddImage;
    public static SQLiteHelperMT dbMT;

    //Another
    private Uri imageUri;
    private String name, cost, location;

    //Permission



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        //Init Permission

        //Create database 'tblMilktea
        dbMT = new SQLiteHelperMT(this, "MILKTEA.sqlite", null, 1);
        dbMT.queryData("CREATE TABLE IF NOT EXISTS MILKTEA (idMT INTEGER PRIMARY KEY AUTOINCREMENT, nameMT VARCHAR, priceMT VARCHAR, imageMT BLOB)");

        //Init View
        inputName = (EditText) findViewById(R.id.nameP);
        inputCost = (EditText) findViewById(R.id.costP);
        inputLocation = (EditText) findViewById(R.id.locationP);
        showImg = (ImageView) findViewById(R.id.imgProduct);

        //Event: Click btnaddImage to adds an image
        btnaddImage = (Button) findViewById(R.id.btnaddImage);
        btnaddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }

        });

        //Event: Click btnaddProduct to add an item
        btnaddProduct = (Button) findViewById(R.id.btnaddProduct);
        btnaddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        //Event: Click btnback to go back a previous layout
        btnback = (ImageView) findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddProduct.this, ShowProduct.class);
                startActivity(intent);
            }
        });


    }


    //Event class


}