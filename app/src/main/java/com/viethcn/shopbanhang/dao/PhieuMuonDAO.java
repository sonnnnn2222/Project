package com.viethcn.shopbanhang.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.viethcn.shopbanhang.database.DbHelper;
import com.viethcn.shopbanhang.model.PhieuMuon;

import java.util.ArrayList;

public class PhieuMuonDAO {

    DbHelper dbHelper;

    public PhieuMuonDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<PhieuMuon> getDSPhieuMuon() {
        ArrayList<PhieuMuon> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT pm.mapm,  pm.matv, tv.hoten , pm.tendangnhap,  nd.hoten ,  pm.masach,  sc.tensach, pm.ngay,  pm.trasach,  pm.tienthue FROM PHIEUMUON pm JOIN THANHVIEN tv ON pm.matv = tv.matv JOIN NGUOIDUNG nd ON pm.tendangnhap = nd.tendangnhap JOIN  SACH sc ON pm.masach = sc.masach", null);
        if (cursor.getCount() != 0 ) {
            cursor.moveToFirst();
            do {
                list.add(new PhieuMuon(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8), cursor.getInt(9)));

            }while (cursor.moveToNext());
        }

        return list;
    }



}
