package com.viethcn.shopbanhang.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.viethcn.shopbanhang.database.DbHelper;
import com.viethcn.shopbanhang.model.ThanhVien;

import java.security.PublicKey;
import java.util.ArrayList;

public class ThanhVienDao {

    DbHelper dbHelper;
    public ThanhVienDao(Context context) {
        dbHelper =  new DbHelper(context);
    }

    public ArrayList<ThanhVien> getDSThanhVien() {
        ArrayList<ThanhVien> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * from THANHVIEN", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new ThanhVien(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));

            }while (cursor.moveToNext());
        }
        return list;
    }
}
