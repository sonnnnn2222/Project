package com.viethcn.shopbanhang.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.viethcn.shopbanhang.database.DbHelper;
import com.viethcn.shopbanhang.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    DbHelper dbHelper;
    public SachDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    // lấy toàn bộ đầu sách có ở trong thư viện
    public ArrayList<Sach> getDSDauSach() {
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        // SACH => masach int, tensach text, giathue int, maloai int references LOAISACH(maloai)
        // LOAISACH => maloai int, tenloai text
        Cursor cursor = sqLiteDatabase.rawQuery("select sc.masach, sc.tensach, sc.giathue, sc.maloai, lo.tenloai from SACH sc, LOAISACH lo where sc.maloai = lo.maloai", null);
        if (cursor.getCount() != 0 ) {
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4) ));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean addSach(String ten, int tien, int maloai){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tensach", ten);
        values.put("giathue", tien);
        values.put("maloai", maloai);
        long check = db.insert("SACH", null, values);
        return check != -1;
    }

    public boolean updateSach(int masach, String tensach, int giathue, int maloai){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tensach", tensach);
        values.put("giathue", giathue);
        values.put("maloai", maloai);
        long check = db.update("SACH", values, "masach = ?", new String[]{String.valueOf(masach)});
        return check != -1;
    }

    // -1: ko được xoá vì sách có trong phiếu mượn
    // 0: xoá thất bại
    // 1: xoá thành công
    public int deleteSach(int masach){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT masach FROM PHIEUMUON WHERE masach =?", new String[]{String.valueOf(masach)});
        // nếu cursor trả về giá trị khác 0 thì trả về -1
        if (cursor.getCount() != 0) return -1;
        long check = db.delete("SACH", "masach = ?", new String[]{String.valueOf(masach)});
        return check != -1? 1:0;
    }


    public List<String> getBookName() {
        List<String> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        // SACH => masach int, tensach text, giathue int, maloai int references LOAISACH(maloai)
        // LOAISACH => maloai int, tenloai text
        Cursor cursor = sqLiteDatabase.rawQuery("select tensach from SACH", null);
        if (cursor.getCount() != 0 ) {
            cursor.moveToFirst();
            do {
                String tenSach = cursor.getString(0);
                list.add(tenSach);
            }while (cursor.moveToNext());
        }
        return list;
    }
}
