package com.viethcn.shopbanhang.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.viethcn.shopbanhang.database.DbHelper;
import com.viethcn.shopbanhang.model.Sach;

import java.util.ArrayList;

public class SachDAO {
    DbHelper dpHelper;
    public SachDAO(Context context) {
        dpHelper = new DbHelper(context);
    }
    // lấy toàn bộ đầu sách có ở trong thư viện
    public ArrayList<Sach> getDSDauSach() {
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dpHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from SACH", null);
        if (cursor.getCount() != 0 ) {
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3) ));

            }while (cursor.moveToNext());
        }


        return list;
    }
}
