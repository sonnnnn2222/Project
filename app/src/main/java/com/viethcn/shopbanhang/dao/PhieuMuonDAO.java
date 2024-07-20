package com.viethcn.shopbanhang.dao;

import android.content.Context;

import com.viethcn.shopbanhang.database.DbHepler;

public class PhieuMuonDAO {

    DbHepler dbHelper;

    public PhieuMuonDAO(Context context) {
        dbHelper = new DbHepler(context);
    }



}
