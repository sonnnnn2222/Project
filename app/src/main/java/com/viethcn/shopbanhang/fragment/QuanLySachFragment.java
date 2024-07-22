package com.viethcn.shopbanhang.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.viethcn.shopbanhang.R;
import com.viethcn.shopbanhang.adapter.SachAdapter;
import com.viethcn.shopbanhang.dao.SachDAO;
import com.viethcn.shopbanhang.model.Sach;


import java.util.ArrayList;

public class QuanLySachFragment extends Fragment {
    SachDAO dao;
    RecyclerView rcSach;
    FloatingActionButton fabADD;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quanlysach, container, false);

        dao = new SachDAO(getContext());

        rcSach = view.findViewById(R.id.rcvSach);
        fabADD = view.findViewById(R.id.fabAdd);

        reloadDatalist();

        fabADD.setOnClickListener(v -> {

        });

        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void reloadDatalist() {
        ArrayList<Sach> list = dao.getDSDauSach();

        // set layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rcSach.setLayoutManager(layoutManager);

        // SachAdapter cần context và list
        SachAdapter adapter = new SachAdapter(getContext(), list, dao);
        rcSach.setAdapter(adapter);
    }
}
