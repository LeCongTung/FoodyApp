package com.example.foodyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Blob;

public class SQLiteHelperMT extends SQLiteOpenHelper {
    //Init SQLite
    private SQLiteDatabase db;
    private SQLiteStatement st;

    public SQLiteHelperMT(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void queryData(String sql){
        db = getWritableDatabase();
        db.execSQL(sql);
    }

    public void addProductMT(String name, String price, String location, byte[] image){
        db = getWritableDatabase();
        String sql = "INSERT INTO tblMILKTEA VALUES (NULL, ?, ?, ?)";

        st = db.compileStatement(sql);
        st.clearBindings();

        st.bindString(1, name);
        st.bindString(2, price);
        st.bindString(3, location);
        st.bindBlob(4, image);
    }

    public Cursor getData(String sql){
        db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
