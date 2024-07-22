package com.viethcn.shopbanhang.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.viethcn.shopbanhang.database.DbHelper;
import com.viethcn.shopbanhang.model.LoaiSach;

import java.util.ArrayList;

public class LoaiSachDAO {
    private DbHelper dbHelper;

    public LoaiSachDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    // Lấy danh sách loại sách
    public ArrayList<LoaiSach> getDSLoaiSach() {
        ArrayList<LoaiSach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM LOAISACH", null);

        if (cursor.moveToFirst()) {
            do {
                list.add(new LoaiSach(cursor.getInt(0), cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // Thêm loại sách
    public boolean themLoaiSach(String tenloai) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", tenloai);
        long check = sqLiteDatabase.insert("LOAISACH", null, contentValues);
        if (check == -1)
            return false;
        return true;
    }


    // Xóa loại sách
    // 1 : xóa thành công, 0 : thất bại, -1 : sách tồn tại trong thể loại đó
    public int xoaLoaiSach(int id) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SACH WHERE maloai = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() != 0) {
            cursor.close();
            return -1;
        }
        cursor.close();

        long result = sqLiteDatabase.delete("LOAISACH", "maloai = ?", new String[]{String.valueOf(id)});
        if (result == -1) {
            return 0;
        }
        return 1;
    }

    // Thay đổi loại sách
    public boolean thayDoiLoaiSach(LoaiSach loaiSach) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenloai", loaiSach.getTenloai());
        long check = sqLiteDatabase.update("LOAISACH", contentValues, "maloai =?", new String[]{String.valueOf(loaiSach.getId())});
        return check != -1;
    }
}
