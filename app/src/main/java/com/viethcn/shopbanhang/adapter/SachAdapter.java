package com.viethcn.shopbanhang.adapter;

import static androidx.core.content.ContextCompat.getString;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.viethcn.shopbanhang.MainActivity;
import com.viethcn.shopbanhang.R;
import com.viethcn.shopbanhang.dao.SachDAO;
import com.viethcn.shopbanhang.model.Sach;
import com.viethcn.shopbanhang.model.ThanhVien;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder>{
    private final Context c;
    private final ArrayList<Sach> list;
    private final ArrayList<HashMap<String, Object>> map;
    private final SachDAO dao;

    public SachAdapter(Context c, ArrayList<Sach> list, ArrayList<HashMap<String, Object>> map, SachDAO dao) {
        this.c = c;
        this.list = list;
        this.map = map;
        this.dao = dao;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMa, txtTen, txtGia, txtMaloai, txtTenloai;
        ImageView ivEdit, ivDel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            String check = ((MainActivity)c).getIntent().getStringExtra("check");

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


        holder.ivEdit.setOnClickListener(v -> showDialogUpDate(list.get(holder.getAdapterPosition())));
        holder.ivDel.setOnClickListener(v -> {
            Sach s1 = list.get(holder.getAdapterPosition());
            int id = s1.getMasach();
            int check = dao.deleteSach(id);
            switch (check){
                case 1:
                    Toast.makeText(c, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    reloadDatalist();
                    break;
                case 0:
                    Toast.makeText(c, "Xoá thất bại sách", Toast.LENGTH_SHORT).show();
                    break;
                case -1:
                    Toast.makeText(c, "Sách đang có phiếu mượn, không thể xoá", Toast.LENGTH_SHORT).show();
                    break;
            }
        });

    }


    private void showDialogUpDate(Sach s){
        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        LayoutInflater inf = ((Activity)c).getLayoutInflater();
        View view = inf.inflate(R.layout.dialog_updatebook, null);
        builder.setView(view);

        EditText edtTen = view.findViewById(R.id.edtTen);
        EditText edtTien = view.findViewById(R.id.edtTien);
        Spinner spnLoaiSach = view.findViewById(R.id.spnLoaiSach);

        SimpleAdapter adapter = new SimpleAdapter(
                c, map, android.R.layout.simple_list_item_1,
                new String[]{"tenloai"}, new int[]{android.R.id.text1}
        );

        edtTen.setText(s.getTenSach());
        edtTien.setText(String.valueOf(s.getGiathue()));
        spnLoaiSach.setAdapter(adapter);

        // Sach int masach, String tenSach, int giathue, int maloai, String tenloai) {
        builder.setNegativeButton("Sửa", (dialog, which) -> {
            String ten = edtTen.getText().toString();
            int tien = Integer.parseInt(edtTien.getText().toString());
            int maloai = Integer.parseInt(map.get(spnLoaiSach.getSelectedItemPosition()).get("maloai").toString());
            int id = s.getMasach();

            // int masach, String tensach, int giathue, int maloai
            boolean check = dao.updateSach(id, ten, tien, Integer.parseInt(String.valueOf(maloai)));

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
        list.addAll(dao.getDSDauSach());
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
