package com.example.foodyapp.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.foodyapp.Layout_Profile;
import com.example.foodyapp.R;
import com.example.foodyapp.show.Show_AddProduct;

import java.io.ByteArrayOutputStream;

public class Activity_AddProduct extends AppCompatActivity {

    EditText etname, etprice, etlocation, etdescription;
    Spinner ettype;
    Button btnadd;
    ImageView image, btnFCamera, btnFGallery;

    ActivityResultLauncher<Intent> activityCameraResultLauncher;
    ActivityResultLauncher<Intent> activityGalleryResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        Intent intent = getIntent();
        String infoname = intent.getExtras().getString("info");
        int total = intent.getIntExtra("total", 0);

        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Activity_AddProduct.this, Show_AddProduct.class);
                intent.putExtra("info", infoname);
                intent.putExtra("total", total);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_back, R.anim.anim_back_2);
            }
        });

        //Init Elements
        etname = (EditText) findViewById(R.id.name);
        etprice = (EditText) findViewById(R.id.price);
        ettype = (Spinner) findViewById(R.id.type);
        etlocation = (EditText) findViewById(R.id.location);
        etdescription = (EditText) findViewById(R.id.description);
        image = (ImageView) findViewById(R.id.image);

        //Permission: Use a camera and choose image from galley
        activityCameraResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Bundle bundle = result.getData().getExtras();
                            Bitmap bitmap = (Bitmap) bundle.get("data");
                            image.setImageBitmap(bitmap);
                        }
                    }
                });

        activityGalleryResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Intent intent = result.getData();
                            Uri uri = intent.getData();
                            image.setImageURI(uri);
                        }
                    }
                });

        //Event: Open a Camera
        btnFCamera = (ImageView) findViewById(R.id.btnfromcamera);
        btnFCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                activityCameraResultLauncher.launch(intent);
            }
        });

        //Event: Open a Gallery
        btnFGallery = (ImageView) findViewById(R.id.btnfromgallery);
        btnFGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityGalleryResultLauncher.launch(intent);
            }
        });

        //Set text in spinner
        String arr[] = {"Đồ uống", "Cơm", "Đồ ăn nhanh", "Bánh", "Trái cây", "Đồ hộp"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(Activity_AddProduct.this, android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        ettype.setAdapter(adapter);
        ettype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) { }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        //Event: Add a product
        btnadd = (Button) findViewById(R.id.btnadd);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable bm = (BitmapDrawable) image.getDrawable();
                Bitmap bitmap = bm.getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] image = byteArrayOutputStream.toByteArray();

                //Call values into elements
                String nameP = etname.getText().toString().trim();
                int priceP = Integer.parseInt(etprice.getText().toString().trim());
                String typeP = ettype.getSelectedItem().toString().trim();
                String locationP = etlocation.getText().toString().trim();
                String descriptionP = etdescription.getText().toString().trim();

                Show_AddProduct.db.addP(
                        nameP, priceP, typeP, locationP, descriptionP, image);

                Toast.makeText(Activity_AddProduct.this, "Đã thêm thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Activity_AddProduct.this, Show_AddProduct.class);
                intent.putExtra("info", infoname);
                intent.putExtra("total", total);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_back, R.anim.anim_back_2);
            }
        });
    }
}