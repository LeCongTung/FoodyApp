package com.example.foodyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Blob;

public class SQLiteHelperMT extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Product.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "ProductDetailMT";
    private static final String COLUMN_ID = "idProductMT";
    private static final String COLUMN_NAME = "nameProductMT";
    private static final String COLUMN_COST = "costProductMT";
    private static final String COLUMN_RATE = "rateProductMT";
    private static final String COLUMN_IMG = "imgProductMT";


    public SQLiteHelperMT(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        String query = "CREATE TABLE " + TABLE_NAME +
                "(" + COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + "TEXT" + COLUMN_COST + "INTEGER" + COLUMN_RATE + "INTEGER" + COLUMN_IMG +"BLOB);";
        DB.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME);
        onCreate(DB);
    }

    public long addProductMT(String name, String cost, String image)  {
        SQLiteDatabase DB = this.getWritableDatabase();

        ContentValues CV = new ContentValues();;

        CV.put(COLUMN_NAME, name);
        CV.put(COLUMN_COST, cost);
        CV.put(COLUMN_IMG, image);

        long result = DB.insert(TABLE_NAME, null, CV);
        DB.close();

        return result;
    }
}
