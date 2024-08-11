package com.viethcn.shopbanhang.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.viethcn.shopbanhang.R;

import java.util.List;


public class MainViewCustomerAdapter extends RecyclerView.Adapter<MainViewCustomerAdapter.ViewHolder> {

    private List<String> data;
    private final Context context;

    public MainViewCustomerAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtModelName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtModelName = itemView.findViewById(R.id.txtModelName);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_model, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtModelName.setText(maxLine(data.get(position)));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private String maxLine(String text) {
        if (text.length() > 20) {
            return text.substring(0, 12) + "...";
        } else {
            return text;
        }
    }
}
