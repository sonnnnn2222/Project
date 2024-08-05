package com.viethcn.shopbanhang.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.viethcn.shopbanhang.R;

public class ThongTinFragment extends Fragment {


    private EditText txtDangNhap, txtHoTen, txtMatKhau;;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongtin, container, false);
        txtDangNhap  = view.findViewById(R.id.txtDangNhap);
        txtHoTen  = view.findViewById(R.id.txtHoTen);
        txtMatKhau  = view.findViewById(R.id.txtMatKhau);
        txtMatKhau.setEnabled(false);
        txtHoTen.setEnabled(false);
        txtDangNhap.setEnabled(false);

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
