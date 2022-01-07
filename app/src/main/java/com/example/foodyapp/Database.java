package com.example.foodyapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void QueryData(String sql){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    public Cursor GetData(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql, null);
    }

    //Excute: Add a product into database
    public void addP (String name, int price, String type, String location, String description, byte[] image){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO product VALUES (null, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, String.valueOf(price));
        statement.bindString(3, type);
        statement.bindString(4, location);
        statement.bindString(5, description);
        statement.bindBlob(6, image);

        statement.executeInsert();
    }

    public void addC (String name, int price, String location, int quantity, byte[] image){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO cart VALUES (null, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, String.valueOf(price));
        statement.bindString(3, location);
        statement.bindString(4, String.valueOf(quantity));
        statement.bindBlob(5, image);

        statement.executeInsert();
    }

    public void addU (String name, String numberphone, String user, String pass, String location){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO user VALUES (null, ?, ?, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, name);
        statement.bindString(2, numberphone);
        statement.bindString(3, user);
        statement.bindString(4, pass);
        statement.bindString(5, location);

        statement.executeInsert();
    }

    public void addH (String user, int total, String time){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO history VALUES (null, ?, ?, ?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, user);
        statement.bindString(2, String.valueOf(total));
        statement.bindString(3, time);

        statement.executeInsert();
    }


    public Boolean checkuser(String user){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT user FROM user WHERE user = ?", new String[] {user});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkphonenumber(String phonenumber){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT numberphoneU FROM user WHERE numberphoneU = ?", new String[] {phonenumber});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkuserpass(String user, String pass){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT user, pass FROM user WHERE user = ? AND pass = ?", new String[] {user, pass});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkpass(String user, String pass){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE user = ? AND pass = ?", new String[] {user, pass});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
