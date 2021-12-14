package com.example.foodyapp;

import static com.example.foodyapp.SQLiteHelperMT.TABLE_NAME;

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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class AddProduct extends AppCompatActivity {

    //View
    private ImageView showImg;
    private EditText inputName, inputPrice, inputLocation;
    private Button btnaddProduct, btnaddImage;
    SQLiteHelperMT dbMT;
    SQLiteDatabase sqLiteDatabase;

    //Another
    int id = 0;
    String[] cameraPermission;
    String[] storagePermission;

    //Permission
    final int REQUEST_CODE_CAMERA = 100;
    final int REQUEST_CODE_GALLERY = 101;

//    =new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
//    =new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        //Init View
        inputName = (EditText) findViewById(R.id.nameP);
        inputPrice = (EditText) findViewById(R.id.costP);
        inputLocation = (EditText) findViewById(R.id.locationP);

        showImg = (ImageView) findViewById(R.id.imgProduct);

        btnaddImage = (Button) findViewById(R.id.btnaddImage);

        //Event: Click btnaddProduct to add an item
        btnaddProduct = (Button) findViewById(R.id.btnaddProduct);
        btnaddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

    }

    //Event: Insert datas


    //Get images from Gallery
//    private byte[] imageViewToByte(ImageView image) {
//        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
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