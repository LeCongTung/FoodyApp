package com.example.foodyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
    private EditText inputName, inputPrice, inputLocation;
    private Button btnaddProduct, btnaddImage;
    public static SQLiteHelperMT dbMT;

    //Another
    private Uri imageUri;
    private String name, cost, location;

    //Permission
    final int REQUEST_CODE_GALLERY = 999;


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
        inputPrice = (EditText) findViewById(R.id.costP);
        inputLocation = (EditText) findViewById(R.id.locationP);
        showImg = (ImageView) findViewById(R.id.imgProduct);

        //Event: Click btnaddImage to adds an image
        btnaddImage = (Button) findViewById(R.id.btnaddImage);
        btnaddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        AddProduct.this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY);
            }

        });

        //Event: Click btnaddProduct to add an item
        btnaddProduct = (Button) findViewById(R.id.btnaddProduct);
        btnaddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    dbMT.addProductMT(
                            inputName.getText().toString().trim(),
                            inputPrice.getText().toString().trim(),
                            inputLocation.getText().toString().trim(),
                            imageViewToByte(showImg)
                    );
                    Toast.makeText(getApplicationContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                    inputName.setText("");
                    inputPrice.setText("");
                    inputLocation.setText("");
                    showImg.setImageResource(R.drawable.addimage);
                }catch (Exception e){
                    e.printStackTrace();
                }

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

    //Get images from Gallery
    private byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


    //Declaration Permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }else {
                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == REQUEST_CODE_GALLERY && requestCode == RESULT_OK && data != null){
            Uri imageURI = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageURI);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                showImg.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}