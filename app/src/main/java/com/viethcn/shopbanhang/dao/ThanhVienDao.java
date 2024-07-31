package com.viethcn.shopbanhang.dao;

import android.content.ContentValues;
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

    public boolean addThanhVien(String hoten, String namsinh){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoten", hoten);
        values.put("namsinh", namsinh);
        long result = db.insert("THANHVIEN", null, values);
        return result != -1; // nếu biến result này khác -1 thì trả về true
    }

    public boolean updateThanhVien(int id, String newName, String newBirthYear){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("hoten", newName);
        values.put("namsinh", newBirthYear);
        long check = db.update("THANHVIEN", values, "matv = ?", new String[]{id + ""});
        return check != -1; // nếu biến result này khác -1 thì trả về true
    }
    public int deleteThanhVien(int matv){
        int result = 2;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // THANHVIEN => matv integer PRIMARY KEY autoincrement, hoten text, namsinh text
        Cursor cursor = db.rawQuery("SELECT matv FROM PHIEUMUON WHERE matv = ?", new String[]{matv + ""});

        // nếu cursor trả về giá trị lớn hơn 0 (tức thành viên này đang có phiếu mượn) trả về -1
        if (cursor.getCount() != 0) return -1;

        // check sẽ trả về 1 nếu thành công và 0 nếu thất bại
        int check = db.delete("THANHVIEN", "matv = ?", new String[]{matv + ""});
        return check != -1? 1: 0;
    }
}
