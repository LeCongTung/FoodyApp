package com.example.foodyapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Blob;

public class SQLiteHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Product.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "ProductDetail";
    private static final String COLUMN_ID = "idProduct";
    private static final String COLUMN_NAME = "nameProduct";
    private static final String COLUMN_COST = "costProduct";
    private static final String COLUMN_RATE = "rateProduct";
    private static final String COLUMN_IMG = "imgProduct";


    public SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
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

//    Event For Product
//    public Boolean insertData (Integer idProduct, String nameProduct, Integer cost, Integer rate, Blob imgProduct){
//        SQLiteDatabase DB = this.getWritableDatabase()
//    }
}
