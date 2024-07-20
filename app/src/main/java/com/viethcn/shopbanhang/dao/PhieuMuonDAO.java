package com.viethcn.shopbanhang.dao;

import android.content.Context;

import com.viethcn.shopbanhang.database.DbHelper;

public class PhieuMuonDAO {

    DbHelper dbHelper;

    public PhieuMuonDAO(Context context) {
        dbHelper = new DbHelper(context);
    }



}
