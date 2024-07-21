package com.viethcn.shopbanhang.dao;

import android.content.Context;

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
        return list;
    }



}
