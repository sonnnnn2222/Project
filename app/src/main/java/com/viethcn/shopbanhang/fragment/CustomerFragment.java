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

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.viethcn.shopbanhang.R;
import com.viethcn.shopbanhang.adapter.CategoryAdapter;
import com.viethcn.shopbanhang.adapter.MainViewCustomerAdapter;
import com.viethcn.shopbanhang.dao.LoaiSachDAO;
import com.viethcn.shopbanhang.dao.SachDAO;

import java.util.ArrayList;
import java.util.List;
//

public class CustomerFragment extends Fragment {
    private RecyclerView rcvCategory, rcvBook;
    private LoaiSachDAO CategoryDAO;
    private SachDAO bookDAO;
    private ImageSlider imgSlider;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer, container, false);

        CategoryDAO = new LoaiSachDAO(getContext());
        bookDAO = new SachDAO(getContext());

        rcvCategory = view.findViewById(R.id.rcvCategory);
        rcvBook = view.findViewById(R.id.rcvBook);
        imgSlider = view.findViewById(R.id.imgSlider);

        showCategory();
        showBook();
        setSlider();


        return view;
    }


    private void showCategory() {
        List<String> listCategory = new ArrayList<>();
        listCategory = CategoryDAO.getCategoryName();
        CategoryAdapter adapter = new CategoryAdapter(listCategory);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvCategory.setLayoutManager(layoutManager);
        rcvCategory.setAdapter(adapter);
    }

    private void showBook(){
        List<String> listBook = bookDAO.getBookName();
        MainViewCustomerAdapter adapter = new MainViewCustomerAdapter(getContext(), listBook);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rcvBook.setLayoutManager(layoutManager);
        rcvBook.setAdapter(adapter);
    }

    private void setSlider() {
        List<SlideModel> listImg = new ArrayList<>();
        listImg.add(new SlideModel(R.drawable.book_rent_banner , ScaleTypes.FIT));
        listImg.add(new SlideModel(R.drawable.book_rent ,ScaleTypes.FIT));

        imgSlider.setImageList(listImg);
    }

}
