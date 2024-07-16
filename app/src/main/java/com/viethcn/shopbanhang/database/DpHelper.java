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

   

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
