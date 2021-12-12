package com.example.foodyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import androidx.annotation.Nullable;
import java.sql.Blob;

public class SQLiteHelperMT extends SQLiteOpenHelper {

    //Declare elements
    private Context context;
    private static final String DATABASE_NAME = "Product.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "tblMILKTEA";
    private static final String COLUMN_ID = "idMT";
    private static final String COLUMN_NAME = "nameMT";
    private static final String COLUMN_PRICE = "priceMT";
    private static final String COLUMN_LOCATION = "locationMT";


    public SQLiteHelperMT(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                "(" + COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + "TEXT, " + COLUMN_PRICE + "INTEGER, " + COLUMN_LOCATION + "TEXT );";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void InsertMT(String name, int price, String location){
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
}
