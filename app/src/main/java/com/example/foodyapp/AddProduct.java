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

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class AddProduct extends AppCompatActivity {

   //View
    private ImageView btnback, showImg;
    private EditText inputName, inputPrice, inputLocation;
    private Button btnaddProduct, btnaddImage;
    public static SQLiteHelperMT dbMT;
    SQLiteDatabase sqLiteDatabase;

    //Another
    int id = 0;
    String[] cameraPermission;
    String[] storagePermission;

    //Permission
    final int REQUEST_CODE_CAMERA = 100;
    final int REQUEST_CODE_GALLERY = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        //Init Permission
        dbMT = new SQLiteHelperMT(this);

        //Init View
        inputName = (EditText) findViewById(R.id.nameP);
        inputPrice = (EditText) findViewById(R.id.costP);
        inputLocation = (EditText) findViewById(R.id.locationP);

        showImg = (ImageView) findViewById(R.id.imgProduct);
        btnback = (ImageView) findViewById(R.id.btnback);

        btnaddImage = (Button) findViewById(R.id.btnaddImage);
        btnback = (ImageView) findViewById(R.id.btnback);

        //Event: Click btnaddProduct to add an item
        btnaddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv = new ContentValues();
                cv.put("nameMT", inputName.getText().toString());
                cv.put("priceMT", inputName.getText().toString());
                cv.put("locationMT", inputName.getText().toString());

            }
        });

        //Event: Click btnback to go back a previous layout
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddProduct.this, ShowProduct.class);
                startActivity(intent);

            }
        });

    }

    //Event: Insert datas
    void InsertMT(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_PRICE, price);
        cv.put(COLUMN_LOCATION, location);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1){
            Toast.makeText(context, "Lỗi!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
        }
    }

    //Get images from Gallery
    private byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

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