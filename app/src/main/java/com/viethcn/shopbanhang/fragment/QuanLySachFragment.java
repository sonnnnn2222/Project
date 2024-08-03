package com.viethcn.shopbanhang.fragment;

import android.content.Context;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
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
    private RecyclerView rcSach;
    private SearchView searchView;
    private ArrayList<Sach> list;
    private FloatingActionButton fabADD;
    private SachDAO dao;
    private ArrayList<HashMap<String, Object>> map;
    private SachAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quanlysach, container, false);

        rcSach = view.findViewById(R.id.rcvSach);
        searchView = view.findViewById(R.id.searchView);
        fabADD = view.findViewById(R.id.fabAdd);

        dao = new SachDAO(getActivity());
        map = getLoaiSach();
        list = dao.getDSDauSach();

        adapter = new SachAdapter(getContext(), list, map, dao);
        rcSach.setAdapter(adapter);
        rcSach.setLayoutManager(new LinearLayoutManager(getActivity()));

        fabADD.setOnClickListener(v -> showDialogAddSach());


        SharedPreferences sharedPreferences = getContext().getSharedPreferences("thongtin", Context.MODE_PRIVATE);
        String loai = sharedPreferences.getString("loai", "");

        if (loai.equals("admin")) { // Example condition to show/hide edit and delete icons
           fabADD.setVisibility(View.VISIBLE);
        } else {
            fabADD.setVisibility(View.GONE);
        }


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return view;
    }

    private void showDialogAddSach() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_themsach, null);
        builder.setView(view);

        EditText edtTen = view.findViewById(R.id.edtTen);
        EditText edtTien = view.findViewById(R.id.edtTien);
        Spinner spnLoaiSach = view.findViewById(R.id.spnLoaiSach);

        SimpleAdapter adapter = new SimpleAdapter(
                getContext(), getLoaiSach(), android.R.layout.simple_list_item_1,
                new String[]{"tenloai"}, new int[]{android.R.id.text1}
        );
        spnLoaiSach.setAdapter(adapter);

        builder.setNegativeButton("Thêm", (dialog, which) -> {
            String ten = edtTen.getText().toString();
            int tien;
            try {
                tien = Integer.parseInt(edtTien.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Giá sách không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            int loai = Integer.parseInt(((HashMap<String, Object>) spnLoaiSach.getSelectedItem()).get("maloai").toString());

            boolean result = dao.addSach(ten, tien, loai);
            if (result) {
                Toast.makeText(getContext(), "Thêm sách thành công", Toast.LENGTH_SHORT).show();
                reloadDatalist();
            } else {
                Toast.makeText(getContext(), "Thêm sách thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("Hủy", (dialog, which) -> {});

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private ArrayList<HashMap<String, Object>> getLoaiSach() {
        LoaiSachDAO lsDAO = new LoaiSachDAO(getContext());
        ArrayList<LoaiSach> list = lsDAO.getDSLoaiSach();
        ArrayList<HashMap<String, Object>> listMap = new ArrayList<>();

        for (LoaiSach ls : list) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("maloai", ls.getId());
            map.put("tenloai", ls.getTenloai());
            listMap.add(map);
        }

        return listMap;
    }

    private void reloadDatalist() {
        list.clear();
        list.addAll(dao.getDSDauSach());
        adapter.notifyDataSetChanged();
    }
}
