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

    public static final String TABLE_NAME = "tblMILKTEA";
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
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
    }
}
