
package com.viethcn.shopbanhang.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context){
        super(context, "THUESACH", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String qNguoiDung = "CREATE TABLE NGUOIDUNG (tendangnhap TEXT PRIMARY KEY, matkhau TEXT, hoten TEXT, loai text)";
        db.execSQL(qNguoiDung);
        String dbThanhVien = "CREATE TABLE THANHVIEN(matv integer PRIMARY KEY autoincrement, hoten text, namsinh text )";
        db.execSQL(dbThanhVien);
        String dbLoaiSach = "CREATE TABLE LOAISACH(maloai integer PRIMARY KEY autoincrement, tenloai text)";
        db.execSQL(dbLoaiSach);
        String dbSach = "CREATE TABLE SACH (masach integer PRIMARY KEY autoincrement, tensach text, giathue integer, maloai integer references LOAISACH(maloai))";
        db.execSQL(dbSach);
        String dbPhieuMuon = "CREATE TABLE PHIEUMUON(mapm integer PRIMARY KEY autoincrement, matv integer references THANHVIEN(matv), tendangnhap TEXT references NGUOIDUNG(tendangnhap), masach integer references SACH(masach), ngay text, trasach integer, tienthue integer)";
        db.execSQL(dbPhieuMuon);

        // chỉ có 1 tài khoản có Loại:THUTHU thôi nhe
        String dNguoiDung = "INSERT INTO NGUOIDUNG VALUES('nhutviet', '12345', 'Hoang Cong Nhut Viet', 'thuthu'), ('giabao', '123456', 'Trần Gia Bảo', ''), ('xuanson', '12345', 'Bùi Xuân Sơn', '')";
        db.execSQL(dNguoiDung);
        db.execSQL("INSERT INTO LOAISACH VALUES (1, 'Thiếu nhi'),(2,'Tình cảm'),(3, 'Giáo khoa')");
        db.execSQL("INSERT INTO SACH VALUES (1, 'Hãy đợi đấy', 2500, 1), (2, 'Thằng cuội', 1000, 1), (3, 'Lập trình Android', 2000, 3)");
        db.execSQL("INSERT INTO THANHVIEN VALUES (1,'Hoàng Công Nhựt Việt','2005'),(2,'Trần Gia Bảo','2005'), (3,'Bùi Xuân Sơn','2005'), (4,'Bùi Phú Quý','2002'), (5,'Lại Đức Tín','2005')");
        //trả sách: 1: đã trả - 0: chưa trả
        db.execSQL("INSERT INTO PHIEUMUON VALUES (1,1,'giabao', 1, '19/03/2022', 1, 2500),(2,1,'xuanson', 3, '19/03/2022', 0, 2000),(3,2,'giabao', 1, '19/03/2022', 1, 2000)");
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


