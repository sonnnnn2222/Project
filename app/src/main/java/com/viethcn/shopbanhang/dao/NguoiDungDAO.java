package com.viethcn.shopbanhang.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.viethcn.shopbanhang.database.DpHelper;

public class NguoiDungDAO {
    private DpHelper dbHelper;
    public NguoiDungDAO(Context context) {
        dbHelper = new DpHelper(context);
    }
    // Login
    public boolean CheckLogin(String username, String password){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT*FROM NGUOIDUNG WHERE tendangnhap = ? AND matkhau= ?",new String[]{username,password});
        return cursor.getCount() > 0;
    }
    // Register
    public boolean Register (String email, String password, String repass){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("email",email);
        contentValues.put("matkhau", password);
        contentValues.put("repass",repass);

        long check = sqLiteDatabase.insert("NGUOIDUNG",null,contentValues);
        return check != -1;
    }
}
