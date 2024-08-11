
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
        // NGUOIDUNG => tendangnhap text PRIMARY KEY, matkhau text, hoten text, loai text
        String qNguoiDung = "CREATE TABLE NGUOIDUNG (tendangnhap TEXT PRIMARY KEY, matkhau TEXT, hoten TEXT, loai text)";
        db.execSQL(qNguoiDung);

        // LOAISACH => maloai integer PRIMARY KEY autoincrement, tenloai text
        String dbLoaiSach = "CREATE TABLE LOAISACH(maloai integer PRIMARY KEY autoincrement, tenloai text)";
        db.execSQL(dbLoaiSach);

        // SACH => masach integer PRIMARY KEY autoincrement, tensach text, giathue integer,
        //         maloai integer references LOAISACH(maloai)
        String dbSach = "CREATE TABLE SACH (masach integer PRIMARY KEY autoincrement, tensach text, giathue integer, maloai integer references LOAISACH(maloai))";
        db.execSQL(dbSach);

        // PHIEUMUON => mapm integer PRIMARY KEY autoincrement, matv integer references THANHVIEN(matv),
        //              tendangnhap TEXT references NGUOIDUNG(tendangnhap),
        //              masach integer references SACH(masach),
        //              ngay text, trasach integer, tienthue integer
        String dbPhieuMuon = "CREATE TABLE PHIEUMUON(mapm integer PRIMARY KEY autoincrement, tendangnhap TEXT references NGUOIDUNG(tendangnhap), masach integer references SACH(masach), ngay text, trasach integer, tienthue integer)";
        db.execSQL(dbPhieuMuon);

        String dNguoiDung = "INSERT INTO NGUOIDUNG VALUES('admin', '12345', 'admin', 'admin'), ('nhutviet', '12345', 'Hoàng Công Nhựt Việt', ' '), ('giabao', '12345', 'Trần Gia Bảo', ' '), ('xuanson', '12345', 'Bùi Xuân Sơn', ' '), ('ductin', '12345', 'Lại Đức Tín',' '), ('phuquy', '12345', 'Bùi Phú Quý', ' ')";
        db.execSQL(dNguoiDung);

        db.execSQL("INSERT INTO LOAISACH VALUES (1, 'Thiếu nhi'),(2,'Tình cảm'),(3, 'Giáo khoa'),(4, 'Tiểu Thuyết'),(5, ' Truyện Tranh'),(6, 'Trinh Thám'),(7, 'Tình Cảm'),(8, 'Khoa Học Viễn Tưởng'),(9, 'Đời Sống'),(10, 'Triết Học')");
        db.execSQL("INSERT INTO SACH VALUES (1, 'Muốn an được an', 2500, 1), (2, 'Từng bước nở hoa sen', 1000, 1), (3, 'Đạo phật của tuổi trẻ', 2000, 3), (4, 'Giận', 2000, 3), (5, 'Phép lạ của sự tỉnh thức', 2000, 3), (6, 'Tĩnh lặng', 2000, 3), (7, 'An lạc từng bước chân', 2000, 3), (8, 'Hạnh phúc cầm tay', 2000, 3), (9, 'Tìm bình yên trong gia đình', 2000, 3), (10, 'Phải Lòng Với Cô Đơn', 2000, 3), (11, 'Gái Phải Mạnh Mẽ', 2000, 3), (12, 'Tôi Tài Giỏi, Bạn Cũng Thế!', 2000, 3), (13, 'Bí Quyết Tay Trắng Thành Triệu Phú', 2000, 3), (14, ' Bí Quyết Gây Dựng Cơ Nghiệp Bạc Tỷ', 2000, 3), (15, 'Làm Chủ Tư Duy, Thay Đổi Vận Mệnh', 2000, 3)");

        //trả sách: 1: đã trả - 0: chưa trả
        db.execSQL("INSERT INTO PHIEUMUON VALUES (1,'giabao', 1, '19/03/2022', 1, 2500),(2,'xuanson', 3, '19/03/2022', 0, 2000),(3,'giabao', 1, '19/03/2022', 1, 2000)");
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


