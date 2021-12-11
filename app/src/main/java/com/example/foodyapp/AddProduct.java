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

   //view
    private ImageView btnback, showImg;
    private EditText inputName, inputCost, inputLocation;
    private Button btnaddImg, btnaddProduct;
    private SQLiteHelperMT dbMT;

    //permission arrays
    private String[] cameraPermission;
    private String[] storagePermission;

    //Another
    private Uri imageUri;
    private String name, cost, location;
    private SQLiteHelperMT sqlite;

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
        inputLocation = (EditText) findViewById(R.id.locationP);
        showImg = (ImageView) findViewById(R.id.imgProduct);


        //Event: Click btnaddImage to adds an image
        btnaddImg = (Button) findViewById(R.id.btnaddImage);
        btnaddImg.setOnClickListener(new View.OnClickListener() {
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
    private void pickFromGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {
        ContentValues CV = new ContentValues();
        CV.put(MediaStore.Images.Media.TITLE, "Image Title");
        CV.put(MediaStore.Images.Media.DESCRIPTION, "Image Description");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, CV);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private void inputData() {
        name = "" + inputName.getText().toString().trim();
        cost = "" + inputCost.getText().toString().trim();
        location = "" + inputLocation.getText().toString().trim();

        String timestamp = "" + System.currentTimeMillis();
        long id = sqlite.addProductMT(
                "" +name, ""+cost,""+location, ""+imageUri);
        Toast.makeText(this, "Record Added with " + id, Toast.LENGTH_SHORT).show();
    }


}