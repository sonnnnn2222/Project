package com.viethcn.shopbanhang.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.viethcn.shopbanhang.database.DbHelper;
import com.viethcn.shopbanhang.model.Sach;

import java.util.ArrayList;

public class ThongKeDAO {
    DbHelper dbHelper;
    public ThongKeDAO(Context c) {
        dbHelper = new DbHelper(c);
    }

    public ArrayList<Sach> getTop10() {
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT pm.masach, sc.tensach, COUNT(pm.masach) from PHIEUMUON pm, SACH sc WHERE pm.masach = sc.masach GROUP by pm.masach, sc.tensach ORDER by COUNT(pm.masach) DESC limit 10", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public int getDoanhThu(String start, String end){
        // start => ngày bắt đầu
        // end => ngày kết thúc
        start = start.replace("/", "");
        end = end.replace("/", "");
        SQLiteDatabase sql = dbHelper.getReadableDatabase();
        Cursor cursor = sql.rawQuery("select sum(tienthue) from phieumuon where substr(ngay,7) || substr(ngay,4,2) || substr(ngay,1,2) between ? and ?", new String[]{start, end});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return 0;
    }
}