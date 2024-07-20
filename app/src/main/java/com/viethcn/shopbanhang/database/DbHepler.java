package com.viethcn.shopbanhang.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHepler extends SQLiteOpenHelper {

    public DbHepler(Context context){
        super(context, "THUESACH", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qNguoiDung = "CREATE TABLE NGUOIDUNG (tendangnhap TEXT PRIMARY KEY, matkhau TEXT, hoten TEXT)";
        db.execSQL(qNguoiDung);
        String dbThanhVien = "CREATE TABLE THANHVIEN(matv integer PRIMARY KEY autoincrement, hoten text, namsinh text )";
        db.execSQL(dbThanhVien);
        String dbLoaiSach = "CREATE TABLE LOAISACH(maloai integer PRIMARY KEY autoincrement, tenloai text)";
        db.execSQL(dbLoaiSach);
        String dbSach = "CREATE TABLE SACH (masach integer PRIMARY KEY autoincrement, tensach text, giathue integer, maloai integer references LOAISACH(maloai))";
        db.execSQL(dbSach);
        String dbPhieuMuon = "CREATE TABLE PHIEUMUON(mapm integer PRIMARY KEY autoincrement, matv integer references THANHVIEN(matv), tendangnhap TEXT references NGUOIDUNG(tendangnhap), masach integer references SACH(masach), ngay text, trasach integer, tienthue integer)";
        db.execSQL(dbPhieuMuon);


        String dNguoiDung = "INSERT INTO NGUOIDUNG VALUES('nhutviet', '12345', 'Hoang Cong Nhut Viet')";
        db.execSQL(dNguoiDung);
        db.execSQL("insert into LOAISACH values (1, 'Thiếu nhi'), (2, 'Tình cảm'), (3, 'Giáo Khoa')");
        db.execSQL("insert into SACH values (1, 'Hãy đợi đấy', 2500, 1), (2, 'Thằng cụi', 3000, 1), (1, 'Sách giáo Khoa', 3500, 3)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP table if exists NGUOIDUNG");
            db.execSQL("DROP table if exists LOAISACH");
            db.execSQL("DROP table if exists SACH");
            onCreate(db);
        }
    }
    }
