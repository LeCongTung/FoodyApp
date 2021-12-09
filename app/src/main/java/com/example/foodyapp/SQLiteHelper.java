package com.example.foodyapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.Blob;

public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "Product.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create table Productdetail (idProduct NUMBER primary key, nameProduct TEXT, cost INTEGER, rate INTEGER, imgProduct BLOB)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop table if exists Productdetail");
    }

//    Event For Product
//    public Boolean insertData (Integer idProduct, String nameProduct, Integer cost, Integer rate, Blob imgProduct){
//        SQLiteDatabase DB = this.getWritableDatabase()
//    }
}
