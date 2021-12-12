package com.example.foodyapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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


    //Permission
    final int REQUEST_CODE_GALLERY = 999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        //Init Permission


        //Init View
        inputName = (EditText) findViewById(R.id.nameP);
        inputPrice = (EditText) findViewById(R.id.costP);
        inputLocation = (EditText) findViewById(R.id.locationP);

        showImg = (ImageView) findViewById(R.id.imgProduct);

        //Event: Click btnaddImage to adds an image
        btnaddImage = (Button) findViewById(R.id.btnaddImage);
//        btnaddImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType("image/*");
//                galleryPermission.launch(intent);
//            }
//        });

        //Event: Click btnaddProduct to add an item
        btnaddProduct = (Button) findViewById(R.id.btnaddProduct);
        btnaddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbMT = new SQLiteHelperMT(AddProduct.this);
                dbMT.InsertMT(
                        inputName.getText().toString().trim(),
                        Integer.valueOf(inputPrice.getText().toString().trim()),
                        inputLocation.getText().toString().trim()
                );
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
//    private byte[] imageViewToByte(ImageView image) {
//        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        byte[] byteArray = stream.toByteArray();
//        return byteArray;
//    }

//    private ActivityResultLauncher<Intent> galleryPermission = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == RESULT_OK && result.getData() != null){
//                        Intent intent = result.getData();
//                        Uri imageUri = intent.getData();
//                        showImg.setImageURI(imageUri);
//                    }else{
//                        Toast.makeText(AddProduct.this, "Cancelled!", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//    );


    //Declaration Permissions

}