package com.viethcn.shopbanhang.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.viethcn.shopbanhang.R;
import com.viethcn.shopbanhang.dao.PhieuMuonDAO;
import com.viethcn.shopbanhang.model.PhieuMuon;
import com.viethcn.shopbanhang.model.Sach;

import java.nio.charset.spi.CharsetProvider;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHold>{

    private ArrayList<PhieuMuon> list;
    private final Context context;

    public PhieuMuonAdapter(ArrayList<PhieuMuon> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_phieumuon, parent, false);

        return new ViewHold(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHold holder, int position) {

        NumberFormat numberFormat = new DecimalFormat("#,###");
        double myNumber = list.get(position).getTienthue();
        String formatterNumber = numberFormat.format(myNumber);
        holder.txtMaPhieuMuon.setText("Mã PM: " + list.get(position).getMapm());
        holder.txtMaSach.setText("Mã Sách: " + list.get(position).getMasach());
        holder.txtTenSach.setText("Tên sách: " + list.get(position).getTensach());
        holder.txtNgay.setText("Ngày mươn: " + list.get(position).getNgay());
        String trangThai;

        if (list.get(position).getTrasach() == 1) {
            trangThai = "Đã trả sách";
            holder.btnTraSach.setVisibility(View.GONE);
        }else {
            trangThai = "Chưa trả sách";
            holder.btnTraSach.setVisibility(View.VISIBLE);
        }
        holder.txtTrangThai.setText("Trạng thái: " + trangThai);
        holder.txtTienThue.setText("Tiền thuê: " + formatterNumber + "VNĐ");


        SharedPreferences sharedPreferences = context.getSharedPreferences("thongtin", Context.MODE_PRIVATE);
        String loai = sharedPreferences.getString("loai", "");
        if (loai.equals("admin")) {
            holder.btnTraSach.setVisibility(View.GONE);
        }
        holder.btnTraSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(context);
                boolean kiemtra = phieuMuonDAO.thayDoiTrangThai(list.get(holder.getAdapterPosition()).getMapm());
                if (kiemtra == true) {
                    list.clear();
                    list = phieuMuonDAO.getDSPhieuMuon();
                    notifyDataSetChanged();
                    Toast.makeText(context, "Đã trả sách", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(context, "Thay đổi trạng thái không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHold extends RecyclerView.ViewHolder {
        TextView txtMaPhieuMuon, txtTenDangNhap, txtMaSach, txtTenSach, txtNgay, txtTrangThai, txtTienThue;
        Button btnTraSach;

        public ViewHold(@NonNull View itemView) {
            super(itemView);
            txtMaPhieuMuon = itemView.findViewById(R.id.txtMaphieumuon);
            txtMaSach = itemView.findViewById(R.id.txtMaSach);
            txtTenSach = itemView.findViewById(R.id.txtTenSach);
            txtNgay = itemView.findViewById(R.id.txtNgay);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
            txtTienThue = itemView.findViewById(R.id.txtTienThue);
            btnTraSach = itemView.findViewById(R.id.btnTraSach);
        }
    }
}
