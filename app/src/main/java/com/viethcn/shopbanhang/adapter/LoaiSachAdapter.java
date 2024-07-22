package com.viethcn.shopbanhang.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.viethcn.shopbanhang.R;
import com.viethcn.shopbanhang.dao.LoaiSachDAO;
import com.viethcn.shopbanhang.model.ItemClick;
import com.viethcn.shopbanhang.model.LoaiSach;

import java.util.ArrayList;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHolder> {
    private Context context;
    private ArrayList<LoaiSach> list;
    private ItemClick itemClick;

    public LoaiSachAdapter(Context context, ArrayList<LoaiSach> list, ItemClick itemClick) {
        this.context = context;
        this.list = list;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_loaisach, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoaiSach loaiSach = list.get(position);
        holder.txtTenLoai.setText("Tên Loại: " + loaiSach.getTenloai());
        holder.txtMaLoai.setText("Mã Loại: " + loaiSach.getId());

        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
                int check = loaiSachDAO.xoaLoaiSach(loaiSach.getId());
                switch (check) {
                    case 1:
                        // Xóa thành công, load lại dữ liệu
                        list.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        Toast.makeText(context, "Xóa loại sách thành công", Toast.LENGTH_SHORT).show();
                        break;
                    case -1:
                        Toast.makeText(context, "Không thể xóa loại sách này vì đã có sách thuộc thể loại này", Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        Toast.makeText(context, "Xóa loại sách không thành công", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaiSach loaiSach = list.get(holder.getAdapterPosition());
                itemClick.onClick(loaiSach);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaLoai, txtTenLoai;
        ImageView ivDel, ivEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaLoai = itemView.findViewById(R.id.txtMaloai);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoai);
            ivDel = itemView.findViewById(R.id.ivDel);
            ivEdit = itemView.findViewById(R.id.ivEdit);
        }
    }
}
