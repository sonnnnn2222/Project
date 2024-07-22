package com.viethcn.shopbanhang.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.viethcn.shopbanhang.R;
import com.viethcn.shopbanhang.adapter.LoaiSachAdapter;
import com.viethcn.shopbanhang.dao.LoaiSachDAO;
import com.viethcn.shopbanhang.model.ItemClick;
import com.viethcn.shopbanhang.model.LoaiSach;

import java.util.ArrayList;

public class QuanLyLoaiSachFragment extends Fragment {
    private RecyclerView recyclerLoaiSach;
    private LoaiSachDAO dao;
    private LoaiSachAdapter adapter;
    private ArrayList<LoaiSach> list;
    private EditText edtLoaiSach;
    private int maloai;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quanlyloaisach, container, false);

        recyclerLoaiSach = view.findViewById(R.id.recyclerLoaiSach);
        edtLoaiSach = view.findViewById(R.id.edtLoaiSach);
        Button btnThem = view.findViewById(R.id.btnThem);
        Button btnSua = view.findViewById(R.id.btnSua);

        dao = new LoaiSachDAO(getContext());
        list = new ArrayList<>();
        adapter = new LoaiSachAdapter(getContext(), list, new ItemClick() {
            @Override
            public void onClick(LoaiSach loaiSach) {
                maloai = loaiSach.getId();
                edtLoaiSach.setText(loaiSach.getTenloai());
                btnSua.setVisibility(View.VISIBLE);
                btnThem.setVisibility(View.GONE);
            }
        });

        recyclerLoaiSach.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerLoaiSach.setAdapter(adapter);

        loadData();

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenLoai = edtLoaiSach.getText().toString().trim();

                if (tenLoai.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập tên loại sách", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (dao.themLoaiSach(tenLoai)) {
                    Toast.makeText(getContext(), "Thêm loại sách thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                    edtLoaiSach.setText("");
                } else {
                    Toast.makeText(getContext(), "Thêm loại sách không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenloai = edtLoaiSach.getText().toString().trim();
                LoaiSach loaiSach = new LoaiSach(maloai, tenloai);

                if (dao.thayDoiLoaiSach(loaiSach)) {
                    Toast.makeText(getContext(), "Thay đổi thông tin thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                    edtLoaiSach.setText("");
                    btnSua.setVisibility(View.GONE);
                    btnThem.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getContext(), "Thay đổi thông tin không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void loadData() {
        list.clear();
        list.addAll(dao.getDSLoaiSach());
        adapter.notifyDataSetChanged();
    }
}
