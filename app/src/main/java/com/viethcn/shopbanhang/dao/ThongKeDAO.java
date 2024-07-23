package com.viethcn.shopbanhang.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.viethcn.shopbanhang.database.DbHelper;

public class ThongKeDAO {
    DbHelper dbHelper;
    public ThongKeDAO(Context c) {
        dbHelper = new DbHelper(c);
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
