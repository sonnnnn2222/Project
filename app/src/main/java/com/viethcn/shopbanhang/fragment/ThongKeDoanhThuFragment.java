package com.viethcn.shopbanhang.fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.viethcn.shopbanhang.R;
import com.viethcn.shopbanhang.dao.ThongKeDAO;


import java.util.Calendar;

public class ThongKeDoanhThuFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thongkedoanhthu, container, false);

        EditText edtStart = view.findViewById(R.id.edtStart);
        EditText edtEnd = view.findViewById(R.id.edtEnd);
        Button btnThongKe = view.findViewById(R.id.btnThongKe);
        TextView txtDoanhThu = view.findViewById(R.id.txtDoanhThu);

        Calendar calendar = Calendar.getInstance();

        edtStart.setOnClickListener( v ->{
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
                    (view1, year, month, dayOfMonth) -> {
                        String ngay = dayOfMonth < 10? ("0" + dayOfMonth) : String.valueOf(dayOfMonth);
                        String thang = month < 10? ("0" + month) : String.valueOf(month);

                        edtStart.setText(ngay + "/" + thang + "/" + year);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });

        edtEnd.setOnClickListener( v ->{
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(), (view1, year, month, dayOfMonth) -> {
                        String ngay = dayOfMonth < 10? ("0" + dayOfMonth) : String.valueOf(dayOfMonth);
                        String thang = month < 10? ("0" + month) : String.valueOf(month);

                        edtEnd.setText(ngay + "/" + thang + "/" + year);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });

        btnThongKe.setOnClickListener(v -> {
            ThongKeDAO thongKeDAO = new ThongKeDAO(getContext());
            // lấy ngày bắt đầu và ngày kết thúc
            String start = edtStart.getText().toString();
            String end = edtEnd.getText().toString();

            // biến cost là tổng doanh thu
            // được gọi từ hàm ThongKeDAO, truyền vào ngày bắt đầu và ngày kết thúc
            int cost = thongKeDAO.getDoanhThu(start, end);

            txtDoanhThu.setText(cost + " VND");
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
