package com.viethcn.shopbanhang.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.viethcn.shopbanhang.R;
import com.viethcn.shopbanhang.adapter.PhieuMuonAdapter;
import com.viethcn.shopbanhang.dao.PhieuMuonDAO;
import com.viethcn.shopbanhang.dao.SachDAO;
import com.viethcn.shopbanhang.dao.ThanhVienDao;
import com.viethcn.shopbanhang.model.PhieuMuon;
import com.viethcn.shopbanhang.model.Sach;
import com.viethcn.shopbanhang.model.ThanhVien;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class QuanLyPhieuMuonFragment extends Fragment {
    PhieuMuonDAO phieuMuonDAO;
    ArrayList<PhieuMuon> list;
    RecyclerView recyclerQLPhieuMuon;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quanlyphieumuon, container, false);
        recyclerQLPhieuMuon = view.findViewById(R.id.recyclerQLPhieuMuon);
        FloatingActionButton floatAdd = view.findViewById(R.id.floatAdd);

        loadData();

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        return view;


    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_themphieumuon, null);

        Spinner spnTV = view.findViewById(R.id.spnTV);
        Spinner spnSach = view.findViewById(R.id.spnSach);
        EditText edtTien = view.findViewById(R.id.edtTien);
        getDataThanhVien(spnTV);
        getDataSach(spnSach);
        builder.setView(view);

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // lấy mã thành viên

                HashMap<String, Object> hsTV = (HashMap<String, Object>) spnTV.getSelectedItem();
                int matv =(int) hsTV.get("matv");

                //Lấy mã sách

                HashMap<String, Object> hsSach = (HashMap<String, Object>) spnSach.getSelectedItem();
                int masach = (int) hsSach.get("masach");

                int tien = Integer.parseInt(edtTien.getText().toString()) ;


                themPhieuMuon(matv, masach, tien);
            }
        });


        builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }

    private void loadData() {
        phieuMuonDAO =  new PhieuMuonDAO(getContext());
        list = phieuMuonDAO.getDSPhieuMuon();


        // adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerQLPhieuMuon.setLayoutManager(linearLayoutManager);
        PhieuMuonAdapter adapter =  new PhieuMuonAdapter(list, getContext());
        recyclerQLPhieuMuon.setAdapter(adapter);
    }


    private void themPhieuMuon(int matv, int masach, int tien) {
        // Lấy tên đăng nhập
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("thongtin", Context.MODE_PRIVATE);
        String tendangnhap = sharedPreferences.getString("tendangnhap", "");
        // lấy ngày hiện tại
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd//MM/yyyy", Locale.getDefault());
        String ngay = simpleDateFormat.format(currentTime);

        PhieuMuon phieuMuon = new PhieuMuon(matv, tendangnhap, masach, ngay, 0, tien);
        boolean check = phieuMuonDAO.ThemPhieuMuon(phieuMuon);
        if (check == true) {

            Toast.makeText(getContext(), "Thêm phiếu mượn thành công", Toast.LENGTH_SHORT).show();
            loadData();
        }else {
            Toast.makeText(getContext(), "Thêm phiếu mượn thất bại", Toast.LENGTH_SHORT).show();

        }





    }


    private void getDataThanhVien(Spinner spnTV) {
        ThanhVienDao thanhVienDao = new ThanhVienDao(getContext());
        ArrayList<ThanhVien> list = thanhVienDao.getDSThanhVien();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (ThanhVien tv : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("matv", tv.getMatv());
            hs.put("hoten", tv.getHoten());
            listHM.add(hs);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"hoten"},
                new int[]{android.R.id.text1});
        spnTV.setAdapter(simpleAdapter);
    }


    private void getDataSach(Spinner spnSach) {
        SachDAO sachDAO = new SachDAO(getContext());
        ArrayList<Sach> list = sachDAO.getDSDauSach();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for (Sach sach : list) {
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("masach", sach.getMasach());
            hs.put("tensach", sach.getTenSach());
            listHM.add(hs);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tensach"},
                new int[]{android.R.id.text1});
        spnSach.setAdapter(simpleAdapter);
    }

}
