package com.viethcn.shopbanhang.fragment;

import android.app.Activity;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.viethcn.shopbanhang.R;
import com.viethcn.shopbanhang.adapter.SachAdapter;
import com.viethcn.shopbanhang.dao.LoaiSachDAO;
import com.viethcn.shopbanhang.dao.SachDAO;
import com.viethcn.shopbanhang.model.LoaiSach;
import com.viethcn.shopbanhang.model.Sach;


import java.util.ArrayList;
import java.util.HashMap;

public class QuanLySachFragment extends Fragment {
    RecyclerView rcSach;
    FloatingActionButton fabADD;
    SachDAO dao;
    ArrayList<HashMap<String, Object>> map = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quanlysach, container, false);
        
        rcSach = view.findViewById(R.id.rcvSach);
        fabADD = view.findViewById(R.id.fabAdd);
        dao = new SachDAO(getContext());

        reloadDatalist();

        fabADD.setOnClickListener(v -> {
            showDialogAddSach();
        });

        return view;
    }

    private void showDialogAddSach() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inf = ((Activity)getContext()).getLayoutInflater();
        View view = inf.inflate(R.layout.dialog_themsach, null);
        builder.setView(view);

        EditText edtTen, edtTien;
        Spinner spnLoaiSach;

        edtTen = view.findViewById(R.id.edtTen);
        edtTien = view.findViewById(R.id.edtTien);
        spnLoaiSach = view.findViewById(R.id.spnLoaiSach);

        SimpleAdapter adapter = new SimpleAdapter(
                getContext(),
                getLoaiSach(),
                android.R.layout.simple_list_item_1,
                new String[]{"tenloai"}, new int[]{android.R.id.text1});
        spnLoaiSach.setAdapter(adapter);
        
        builder.setNegativeButton("Thêm", (dialog, which) -> {
            String ten = edtTen.getText().toString();
            int tien = Integer.parseInt(edtTien.getText().toString());
            
            HashMap<String, Object> map = (HashMap<String, Object>) spnLoaiSach.getSelectedItem();
            int loai = (int) map.get("maloai");
            
            boolean c = dao.addSach(ten, tien, loai);
            if (c){
                Toast.makeText(getContext(), "Thêm sách thành công", Toast.LENGTH_SHORT).show();
                reloadDatalist();
            }else {
                Toast.makeText(getContext(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
            }
            
        });
        builder.setPositiveButton("Hủy", (dialog, which) -> {});

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private ArrayList<HashMap<String, Object>> getLoaiSach(){
        LoaiSachDAO lsDAO = new LoaiSachDAO(getContext());
        ArrayList<LoaiSach> list = lsDAO.getDSLoaiSach();
        ArrayList<HashMap<String, Object>> listMap = new ArrayList<>();

        for (LoaiSach ls : list){
            HashMap<String, Object> map = new HashMap<>();
            map.put("maloai", ls.getId());
            map.put("tenloai", ls.getTenloai());
            listMap.add(map);
        }

        return listMap;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void reloadDatalist() {
        ArrayList<Sach> list = new SachDAO(getContext()).getDSDauSach();

        // set layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcSach.setLayoutManager(layoutManager);

        // SachAdapter cần context và list
        SachAdapter adapter = new SachAdapter(getContext(), list, getLoaiSach());
        rcSach.setAdapter(adapter);
    }
}
