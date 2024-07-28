package com.viethcn.shopbanhang;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.viethcn.shopbanhang.dao.NguoiDungDao;
import com.viethcn.shopbanhang.fragment.QuanLyLoaiSachFragment;
import com.viethcn.shopbanhang.fragment.QuanLyPhieuMuonFragment;
import com.viethcn.shopbanhang.fragment.QuanLySachFragment;
import com.viethcn.shopbanhang.fragment.QuanLyThanhVienFragment;
import com.viethcn.shopbanhang.fragment.ThongKeDoanhThuFragment;
import com.viethcn.shopbanhang.fragment.ThongKeTop10Fragment;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolBar);
        FrameLayout frameLayout = findViewById(R.id.frameLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        View headerLayout = navigationView.getHeaderView(0);
        TextView txtName = headerLayout.findViewById(R.id.txtHeader);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.menu);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                if (menuItem.getItemId() == R.id.mQuanLyPhieuMuon) {
                    fragment = new QuanLyPhieuMuonFragment();
                } else if (menuItem.getItemId() == R.id.mQuanLyLoaiSach) {
                    fragment = new QuanLyLoaiSachFragment();
                } else if (menuItem.getItemId() == R.id.mDoanhThu) {
                    fragment = new ThongKeDoanhThuFragment();
                } else if (menuItem.getItemId() == R.id.mQuanLyThanhVien) {
                    fragment = new QuanLyThanhVienFragment();
                } else if (menuItem.getItemId() == R.id.mQuanLySach) {
                    fragment = new QuanLySachFragment();
                } else if (menuItem.getItemId() == R.id.mDoiMatKhau) {
                    showDialogDoiMatKhau();
                } else if (menuItem.getItemId() == R.id.mDangXuat) {
                    Intent intent = new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
                } else if (menuItem.getItemId() == R.id.mTop10) {
                    fragment = new ThongKeTop10Fragment();
                }

                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, fragment)
                            .commit();

                    toolbar.setTitle(menuItem.getTitle());
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        // Display a default fragment when the app starts
        if (savedInstanceState == null) {
            navigationView.setCheckedItem(R.id.mQuanLyPhieuMuon); // Default selected menu item
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new QuanLyPhieuMuonFragment()).commit();
            toolbar.setTitle("Quản Lý Phiếu Mượn");
        }

        // Display the user name
        SharedPreferences sharedPreferences = getSharedPreferences("thongtin", MODE_PRIVATE);
        String hoten = sharedPreferences.getString("hoten", "");
        txtName.setText("Xin Chào: " + hoten);
    }

    public void showDialogDoiMatKhau() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setNegativeButton("Sửa mật khẩu", null)
                .setPositiveButton("Huỷ", null);

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_doimaukhau, null);
        EditText edtPassCu = view.findViewById(R.id.edtPassCu);
        EditText edtPassMoi = view.findViewById(R.id.edtPassMoi);
        EditText edtNhapLaiPassMoi = view.findViewById(R.id.edtNhapLaiPass);

        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String PassCu = edtPassCu.getText().toString();
                String PassMoi = edtPassMoi.getText().toString();
                String NhapLaiPassMoi = edtNhapLaiPassMoi.getText().toString();

                if (PassCu.equals("") || PassMoi.equals("") || NhapLaiPassMoi.equals("")) {
                    Toast.makeText(MainActivity.this, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    if (PassMoi.equals(NhapLaiPassMoi)) {
                        SharedPreferences sharedPreferences = getSharedPreferences("thongtin", MODE_PRIVATE);
                        String tendangnhap = sharedPreferences.getString("tendangnhap", "");
                        // cap nhat
                        NguoiDungDao nguoiDungDao = new NguoiDungDao(MainActivity.this);
                        int check = nguoiDungDao.capNhatMatKhau(tendangnhap, PassCu, PassMoi);
                        if (check == 1) {
                            Toast.makeText(MainActivity.this, "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, Login.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else if (check == 0) {
                            Toast.makeText(MainActivity.this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Cập nhật mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Nhập mật khẩu không trùng", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
