package com.viethcn.shopbanhang.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.viethcn.shopbanhang.database.DbHelper;

public class NguoiDungDao {
    private final DbHelper dbHelper;
    SharedPreferences sharedPreferences;

    public NguoiDungDao(Context context) {
        dbHelper = new DbHelper(context);
        sharedPreferences = context.getSharedPreferences("thongtin", Context.MODE_PRIVATE);
    }


    // login
    public boolean CheckLogin(String userName, String passWord){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM NGUOIDUNG WHERE tendangnhap = ? AND matkhau = ?", new String[]{userName, passWord});
        // Nếu cursor có dữ liệu thì trả về true ngược lại là false
       if (cursor.getCount() !=0) {
           cursor.moveToFirst();
           SharedPreferences.Editor editor = sharedPreferences.edit();
           editor.putString("tendangnhap", cursor.getString(0));
           editor.putString("hoten", cursor.getString(2));
           editor.commit();
           return true;
       }else {
           return false;
       }
    }

    // dang ky
    public boolean Register(String userName, String passWord, String hoTen) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tendangnhap", userName);
        contentValues.put("matkhau", passWord);
        contentValues.put("hoten", hoTen);
        long check = sqLiteDatabase.insert("NGUOIDUNG", null, contentValues);
        return check != -1;
    }

    // forgot

    public String ForgotPassWord(String email) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select matkhau from NGUOIDUNG where tendangnhap = ?", new String[]{email});
        if (cursor.getCount() > 0) {
            // di chuyển con nháy chuột lên trên đầu khi select dữ liệu
            cursor.moveToFirst();
            return cursor.getString(0);
        }else {
            return "";
        }
    }


    public int capNhatMatKhau(String userName, String oldPass, String newPass) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from NGUOIDUNG where tendangnhap = ? and matkhau = ? ", new String[]{userName, oldPass});
        if (cursor.getCount() > 0 ) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("matkhau", newPass);
            long check = sqLiteDatabase.update("NGUOIDUNG", contentValues, "tendangnhap = ?", new String[]{userName});

            if (check == -1) {
                return -1;
            } else {
                return 1;
            }

        }
        return 0;
    }
}
