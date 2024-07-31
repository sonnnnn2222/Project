package com.viethcn.shopbanhang.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.viethcn.shopbanhang.R;
import com.viethcn.shopbanhang.adapter.ThanhVienAdapter;
import com.viethcn.shopbanhang.dao.ThanhVienDao;
import com.viethcn.shopbanhang.model.ThanhVien;

import java.util.ArrayList;

public class QuanLyThanhVienFragment extends Fragment {
    ThanhVienDao dao;
    RecyclerView rcvThanhVien;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quanlythanhvien, container, false);
        rcvThanhVien = view.findViewById(R.id.rcvThanhVien);

        dao = new ThanhVienDao(getContext());

        reloadDatalist();

        FloatingActionButton fabAdd = view.findViewById(R.id.floatAdd);
        fabAdd.setOnClickListener(v -> {
            showDialogThemThanhVien();
        });

        return view;
    }
    private void reloadDatalist() {
        ArrayList<ThanhVien> list = dao.getDSThanhVien();

        // set layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcvThanhVien.setLayoutManager(layoutManager);

        // ThanhVienAdapter cần context và list
        ThanhVienAdapter adapter = new ThanhVienAdapter(getContext(), list, dao);
        rcvThanhVien.setAdapter(adapter);
    }

    private void showDialogThemThanhVien() {
        AlertDialog.Builder builder =new AlertDialog.Builder(getContext());

        LayoutInflater inf = getLayoutInflater();
        View view = inf.inflate(R.layout.dialog_themthanhvien, null);
        builder.setView(view);

        EditText edtHoten = view.findViewById(R.id.edtHoten);
        EditText edtNamsinh = view.findViewById(R.id.edtNamsinh);

        builder.setNegativeButton("Thêm", (dialog, i) -> {
            boolean check = dao.addThanhVien(edtHoten.getText().toString(), edtNamsinh.getText().toString());
            if (check){
                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                reloadDatalist();
            }else {
                Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("Huỷ", (dialog, i) -> {});

        AlertDialog dialog = builder.create();
        dialog.show();

    }
        @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
