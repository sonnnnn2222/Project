package com.viethcn.shopbanhang.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DpHelper extends SQLiteOpenHelper {
    public DpHelper(Context context) {
        super(context,"DUANMAU",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //THUTHU(matt text, hoten text, matkhau text)
        String dbThuThu = "create table THUTHU(matt text primary key, hoten text, matkhau text)";
        db.execSQL(dbThuThu) ;

        String dbThanhVien = "create table THANHVIEN(matv integer primary key autoincrement, hoten text, namsinh text )";
        db.execSQL(dbThanhVien);

        String dbLoaiSach = "create table LOAISACH(maloai integer primary key autoincrement, tenloai text)";
        db.execSQL(dbLoaiSach);

        String dbSach = "create table SACH(masach integer primary key autoincrement, tensach text, giathue integer, maloai integer references LOAISACH(maloai))";
        db.execSQL(dbSach);

        String dbPhieuMuon = "create table PHIEUMUON(maphieumuon integer primary key autoincrement, matv integer references THANHVIEN(matv), matt text references THUTHU(matt), masach integer references SACH(masach), ngay text, trasach integer, tienthue integer)";
        db.execSQL(dbPhieuMuon);
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
