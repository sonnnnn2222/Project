package com.viethcn.shopbanhang.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.viethcn.shopbanhang.R;
import com.viethcn.shopbanhang.adapter.CategoryAdapter;
import com.viethcn.shopbanhang.adapter.MainViewCustomerAdapter;
import com.viethcn.shopbanhang.dao.LoaiSachDAO;
import com.viethcn.shopbanhang.dao.SachDAO;

import java.util.ArrayList;
import java.util.List;

public class CustomerFragment extends Fragment {
    private RecyclerView rcvCategory, rcvBook;
    private LoaiSachDAO CategoryDAO;
    private SachDAO bookDAO;
    private List<String> listCategory, listBook;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer, container, false);

        CategoryDAO = new LoaiSachDAO(getContext());
        bookDAO = new SachDAO(getContext());

        rcvCategory = view.findViewById(R.id.rcvCategory);
        rcvBook = view.findViewById(R.id.rcvBook);

        showCategory();
        showBook();

        return view;
    }


    private void showCategory() {
        listCategory = new ArrayList<>();
        listCategory = CategoryDAO.getCategoryName();
        CategoryAdapter adapter = new CategoryAdapter(listCategory);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvCategory.setLayoutManager(layoutManager);
        rcvCategory.setAdapter(adapter);
    }

    private void showBook(){
        listBook = new ArrayList<>();
        listBook = bookDAO.getBookName();
        MainViewCustomerAdapter adapter = new MainViewCustomerAdapter(getContext(), listBook);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvBook.setLayoutManager(layoutManager);
        rcvBook.setAdapter(adapter);
    }
}
