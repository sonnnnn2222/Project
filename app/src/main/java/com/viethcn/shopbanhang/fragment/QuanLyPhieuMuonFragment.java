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
import com.viethcn.shopbanhang.adapter.PhieuMuonAdapter;
import com.viethcn.shopbanhang.dao.PhieuMuonDAO;
import com.viethcn.shopbanhang.model.PhieuMuon;

import java.util.ArrayList;

public class QuanLyPhieuMuonFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quanlyphieumuon, container, false);
        RecyclerView recyclerQLPhieuMuon = view.findViewById(R.id.recyclerQLPhieuMuon);
        FloatingActionButton floatAdd = view.findViewById(R.id.floatAdd);

        // layout


        // data

        PhieuMuonDAO phieuMuonDAO =  new PhieuMuonDAO(getContext());
        ArrayList<PhieuMuon> list = phieuMuonDAO.getDSPhieuMuon();


        // adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerQLPhieuMuon.setLayoutManager(linearLayoutManager);
        PhieuMuonAdapter adapter =  new PhieuMuonAdapter(list, getContext());
        recyclerQLPhieuMuon.setAdapter(adapter);
        return view;
    }
}
