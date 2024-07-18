package com.viethcn.shopbanhang.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context){
        super(context, "ASM2", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String qNguoiDung = "CREATE TABLE NGUOIDUNG (tendangnhap TEXT PRIMARY KEY, matkhau TEXT, hoten TEXT)";
        db.execSQL(qNguoiDung);
        String qSanPham = "CREATE TABLE SANPHAM (masp INTEGER PRIMARY KEY AUTOINCREMENT, tensp TEXT, giaban INTEGER, soluong INTEGER)";
        db.execSQL(qSanPham);

        String dNguoiDung = "INSERT INTO NGUOIDUNG VALUES('nhutviet', '12345', 'Hoang Cong Nhut Viet'), ('nhutvuong', '123456789', 'Hoang Cong Nhut Vuong')";
        db.execSQL(dNguoiDung);
        String dSanPham = "INSERT INTO SANPHAM VALUES(1, 'Bánh', 5000, 2), (2, 'kẹo', 2000, 5), (3, 'Mì tôm', 8000, 20), (4, 'Dầu ăn', 78000, 1)";
        db.execSQL(dSanPham);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP table if exists NGUOIDUNG");
            db.execSQL("DROP table if exists SANPHAM");
            onCreate(db);
        }
    }
}
