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
    private EditText inputName, inputCost;
    private Button btnaddImage, btnaddProduct;
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

    //Another
    private Uri imageUri;
    private String name, cost;
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
        showImg = (ImageView) findViewById(R.id.imgProduct);

        sqlite = new SQLiteHelperMT(this);

        //Event: Click btnaddImage to adds an image
        btnaddImage = (Button) findViewById(R.id.btnaddImage);
        btnaddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePickDialog();
            }
        });

        //Event: Click btnaddProduct to add an item
        btnaddProduct = (Button) findViewById(R.id.btnaddProduct);
        btnaddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputData();
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
    private void imagePickDialog() {
        String[] options = {"Camera", "Gallery"};
        //Set AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Image From");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0){
                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    }else{
                        pickFromCamera();
                    }
                }else if (i==1){
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }else{
                        pickFromGallery();
                    }
                }
            }
        });
        builder.create().show();
    }

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

        String timestamp = "" + System.currentTimeMillis();
        long id = sqlite.addProductMT(
                "" +name, ""+cost, ""+imageUri);
        Toast.makeText(this, "Record Added with " + id, Toast.LENGTH_SHORT).show();
    }

    //Set permission
    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){
        boolean resultC = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean resultW = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return resultC && resultW;
    }

    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if (grantResults.length>0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && storageAccepted){
                        pickFromCamera();
                    }else{
                        Toast.makeText(this, "Camera & Storage permissions are required!", Toast.LENGTH_SHORT).show();
                    }
                }

            }break;

            case STORAGE_REQUEST_CODE:{
                if (grantResults.length>0){
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted){
                        pickFromCamera();
                    }else{
                        Toast.makeText(this, "Storage permissions are required!", Toast.LENGTH_SHORT).show();
                    }
                }
            }break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK){
            if (resultCode == IMAGE_PICK_GALLERY_CODE){
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);
            }else if (resultCode == IMAGE_PICK_GALLERY_CODE){
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);
            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK){
                    Uri resultUri = result.getUri();
                    imageUri = resultUri;
                    showImg.setImageURI(resultUri);
                }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    Exception error = result.getError();
                    Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}