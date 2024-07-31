package com.viethcn.shopbanhang.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.viethcn.shopbanhang.R;
import com.viethcn.shopbanhang.dao.ThanhVienDao;
import com.viethcn.shopbanhang.model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHolder>{
    private final Context c;
    private final ArrayList<ThanhVien> list;
    private final ThanhVienDao dao;

    public ThanhVienAdapter(Context c, ArrayList<ThanhVien> list, ThanhVienDao tvDAO) {
        this.c = c;
        this.list = list;
        this.dao = tvDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inf = ((Activity)c).getLayoutInflater();
        View view = inf.inflate(R.layout.item_recycle_thanhvien, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMa.setText("Mã TV: " + list.get(position).getMatv());
        holder.txtHoten.setText("Họ tên: " + list.get(position).getHoten());
        holder.txtNamsinh.setText("Năm sinh: " + list.get(position).getNamsinh());

        holder.ivEditt.setOnClickListener(v -> {
            ThanhVien tv = list.get(holder.getAdapterPosition());
            dialogUpdateThanhVien(tv);
        });

        holder.ivDelete.setOnClickListener(v -> {
            ThanhVien tv = list.get(holder.getAdapterPosition());
            int id = tv.getMatv();
            int check = dao.deleteThanhVien(id);
            switch (check){
                case -1:
                    Toast.makeText(c, "Thành viên đang có phiếu mượn, không thể xoá", Toast.LENGTH_SHORT).show();
                    break;
                case 0:
                    Toast.makeText(c, "Không tìm thấy thành viên", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(c, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    reloadDatalist();
                    break;
            }
        });
    }

    private void dialogUpdateThanhVien(ThanhVien tv) {
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        LayoutInflater inf = ((Activity)c).getLayoutInflater();
        View view = inf.inflate(R.layout.dialog_updatethanhvien, null);

        builder.setView(view);

        TextView txtMa = view.findViewById(R.id.txtMaTV);
        EditText edtHoten = view.findViewById(R.id.edtHoten);
        EditText edtNamSinh = view.findViewById(R.id.edtNamsinh);

        txtMa.setText("Mã TV: " + tv.getMatv());
        edtHoten.setText(tv.getHoten());
        edtNamSinh.setText(tv.getNamsinh());

        builder.setNegativeButton("Cập nhập", (dialog, which) -> {
            String newName = edtHoten.getText().toString();
            String newYear = edtNamSinh.getText().toString();
            int id = tv.getMatv();

            boolean check = dao.updateThanhVien(id, newName, newYear);

            if (check){
                Toast.makeText(c, "Cập nhập thành công", Toast.LENGTH_SHORT).show();
                reloadDatalist();
            }

        });

        builder.setPositiveButton("Hủy", (dialog, which) -> {});
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void reloadDatalist(){
        list.clear();
        list.addAll(dao.getDSThanhVien());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMa, txtHoten, txtNamsinh, ivEditt, ivDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            txtMa = itemView.findViewById(R.id.txtMaTV);
            txtHoten = itemView.findViewById(R.id.txtHotenTV);
            txtNamsinh = itemView.findViewById(R.id.txtNamSinhTV);

            ivEditt = itemView.findViewById(R.id.ivEdit);
            ivDelete = itemView.findViewById(R.id.ivDel);
        }
    }
}
