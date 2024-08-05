package com.viethcn.shopbanhang.adapter;

import static androidx.core.content.ContextCompat.getString;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
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

import java.util.ArrayList;
import java.util.HashMap;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder> implements Filterable {
    private final Context context;
    private final ArrayList<Sach> sachList;
    private final ArrayList<Sach> sachListFull;
    private final ArrayList<HashMap<String, Object>> map;
    private final SachDAO dao;

    public SachAdapter(Context context, ArrayList<Sach> sachList, ArrayList<HashMap<String, Object>> map, SachDAO dao) {
        this.context = context;
        this.sachList = sachList;
        this.sachListFull = new ArrayList<>(sachList); // Full copy of the list for filtering
        this.map = map;
        this.dao = dao;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMa, txtTen, txtGia, txtMaloai, txtTenloai;
        ImageView ivEdit, ivDel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            String check = ((MainActivity)context).getIntent().getStringExtra("check");

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
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recycler_sach, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Sach sach = sachList.get(position);
        holder.txtMa.setText("Mã sách: " + sach.getMasach());
        holder.txtTen.setText("Tên sách: " + sach.getTenSach());
        holder.txtGia.setText("Giá sách: " + sach.getGiathue());
        holder.txtMaloai.setText("Mã loại sách: " + sach.getMaloai());


        if (sach.getTenloai() == null) {
            holder.txtTenloai.setText("Tên loại sách: ");
        } else {
            holder.txtTenloai.setText("Tên loại sách: " + sach.getTenloai());
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences("thongtin", Context.MODE_PRIVATE);
        String loai = sharedPreferences.getString("loai", "");

        // Example conditions to control visibility
        if (loai.equals("admin")) { // Example condition to show/hide edit and delete icons
            holder.ivEdit.setVisibility(View.VISIBLE);
            holder.ivDel.setVisibility(View.VISIBLE);
        } else {
            holder.ivEdit.setVisibility(View.GONE);
            holder.ivDel.setVisibility(View.GONE);
        }

        holder.ivEdit.setOnClickListener(v -> showDialogUpdate(sach));
        holder.ivDel.setOnClickListener(v -> {
            int id = sach.getMasach();
            int check = dao.deleteSach(id);
            switch (check) {
                case 1:
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    reloadDatalist();
                    break;
                case 0:
                    Toast.makeText(context, "Xoá thất bại sách", Toast.LENGTH_SHORT).show();
                    break;
                case -1:
                    Toast.makeText(context, "Sách đang có phiếu mượn, không thể xoá", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    @Override
    public int getItemCount() {
        return sachList.size();
    }

    @Override
    public Filter getFilter() {
        return sachFilter;
    }

    private final Filter sachFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Sach> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(sachListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Sach item : sachListFull) {
                    if (item.getTenSach().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            sachList.clear();
            if (results.values != null) {
                sachList.addAll((ArrayList<Sach>) results.values);
            }
            notifyDataSetChanged();
        }
    };

    private void showDialogUpdate(Sach sach) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_updatebook, null);
        builder.setView(view);

        EditText edtTen = view.findViewById(R.id.edtTen);
        EditText edtTien = view.findViewById(R.id.edtTien);
        Spinner spnLoaiSach = view.findViewById(R.id.spnLoaiSach);

        SimpleAdapter adapter = new SimpleAdapter(
                context, map, android.R.layout.simple_list_item_1,
                new String[]{"tenloai"}, new int[]{android.R.id.text1}
        );

        edtTen.setText(sach.getTenSach());
        edtTien.setText(String.valueOf(sach.getGiathue()));
        spnLoaiSach.setAdapter(adapter);

        // Set spinner to the correct item
        for (int i = 0; i < map.size(); i++) {
            if (map.get(i).get("maloai").equals(sach.getMaloai())) {
                spnLoaiSach.setSelection(i);
                break;
            }
        }

        builder.setNegativeButton("Sửa", (dialog, which) -> {
            String ten = edtTen.getText().toString();
            int tien;
            try {
                tien = Integer.parseInt(edtTien.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(context, "Giá sách không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }
            int maloai = Integer.parseInt(map.get(spnLoaiSach.getSelectedItemPosition()).get("maloai").toString());
            int id = sach.getMasach();

            boolean check = dao.updateSach(id, ten, tien, maloai);

            if (check) {
                Toast.makeText(context, "Cập nhập thành công", Toast.LENGTH_SHORT).show();
                reloadDatalist();
            } else {
                Toast.makeText(context, "Cập nhập thất bại", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setPositiveButton("Hủy", (dialog, which) -> {});

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void reloadDatalist() {
        sachList.clear();
        sachList.addAll(dao.getDSDauSach());
        sachListFull.clear();
        sachListFull.addAll(sachList);
        notifyDataSetChanged();
    }
}
