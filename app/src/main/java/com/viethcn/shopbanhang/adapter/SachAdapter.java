package com.viethcn.shopbanhang.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.viethcn.shopbanhang.R;
import com.viethcn.shopbanhang.dao.SachDAO;
import com.viethcn.shopbanhang.model.Sach;

import java.util.ArrayList;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder>{
    private final Context c;
    private final ArrayList<Sach> list;
    private final SachDAO dao;

    public SachAdapter(Context c, ArrayList<Sach> list, SachDAO dao) {
        this.c = c;
        this.list = list;
        this.dao = dao;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMa, txtTen, txtGia, txtMaloai, txtTenloai;
        ImageView ivEdit, ivDel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMa = itemView.findViewById(R.id.tvMa);
            txtTen = itemView.findViewById(R.id.tvTen);
            txtGia = itemView.findViewById(R.id.tvGia);
            txtMaloai = itemView.findViewById(R.id.tvMaloai);
            txtTenloai = itemView.findViewById(R.id.tvTenLoai);

            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDel = itemView.findViewById(R.id.ivDel);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = ((Activity)c).getLayoutInflater();
        View view = inf.inflate(R.layout.item_recycler_sach, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SachAdapter.ViewHolder holder, int position) {
        Sach sach = list.get(holder.getAdapterPosition());

        holder.txtMa.setText("Mã sách: "+sach.getMasach());
        holder.txtTen.setText("Tên sách: "+sach.getTenSach());
        holder.txtGia.setText("Giá sách: "+sach.getGiathue());
        holder.txtMaloai.setText("Mã loại sách: "+sach.getMaloai());

        if ( sach.getTenloai() == null){
            holder.txtTenloai.setText("Tên loại sách: ");
        }else {
            holder.txtTenloai.setText("Tên loại sách: "+sach.getTenloai());
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
