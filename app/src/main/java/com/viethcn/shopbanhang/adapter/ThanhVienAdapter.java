package com.viethcn.shopbanhang.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.viethcn.shopbanhang.R;
import com.viethcn.shopbanhang.model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHolder>{
    private Context c;
    private ArrayList<ThanhVien> list;

    public ThanhVienAdapter(Context c, ArrayList<ThanhVien> list) {
        this.c = c;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = ((Activity)c).getLayoutInflater();
        View view = inf.inflate(R.layout.item_recycle_thanhvien, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMa.setText("Mã TV: " + list.get(position).getMatv());
        holder.txtHoten.setText("Họ tên: " + list.get(position).getHoten());
        holder.txtNamsinh.setText("Năm sinh: " + list.get(position).getNamsinh());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMa, txtHoten, txtNamsinh;
        public ViewHolder(View itemView) {
            super(itemView);
            txtMa = itemView.findViewById(R.id.txtMaTV);
            txtHoten = itemView.findViewById(R.id.txtHotenTV);
            txtNamsinh = itemView.findViewById(R.id.txtNamSinhTV);

        }
    }
}
