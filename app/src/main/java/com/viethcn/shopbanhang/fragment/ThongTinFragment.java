package com.viethcn.shopbanhang.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class ThongTinFragment extends Fragment {


    private TextView txtDangNhap, txtHoTen, txtMatKhau;;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thongtin_fragment, container, false);
        txtDangNhap  = view.findViewById(R.id.txtDangNhap);
        txtHoTen  = view.findViewById(R.id.txtHoTen);
        txtMatKhau  = view.findViewById(R.id.txtMatKhau);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("thongtin", Context.MODE_PRIVATE);
        String dangNhap = sharedPreferences.getString("tendangnhap", "");
        txtDangNhap.setText("Tài khoản: " + dangNhap);
        String matKhau = sharedPreferences.getString("matkhau", "");
        txtMatKhau.setText("Mật khẩu: " + matKhau);
        String hoTen = sharedPreferences.getString("hoten", "");
        txtHoTen.setText("Họ tên:  " + hoTen);








        return view;
    }


}
