package com.viethcn.shopbanhang;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.viethcn.shopbanhang.dao.NguoiDungDao;
import com.viethcn.shopbanhang.dao.SachDAO;
import com.viethcn.shopbanhang.fragment.QuanLyLoaiSachFragment;
import com.viethcn.shopbanhang.fragment.QuanLyPhieuMuonFragment;
import com.viethcn.shopbanhang.fragment.QuanLySachFragment;
import com.viethcn.shopbanhang.fragment.QuanLyThanhVienFragment;
import com.viethcn.shopbanhang.fragment.ThongKeDoanhThuFragment;

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
                }else if (menuItem.getItemId() == R.id.mQuanLyLoaiSach) {
                    fragment = new QuanLyLoaiSachFragment();
                }else if (menuItem.getItemId() == R.id.mDoanhThu) {
                    fragment = new ThongKeDoanhThuFragment();
                }else if (menuItem.getItemId() == R.id.mQuanLyThanhVien) {
                    fragment = new QuanLyThanhVienFragment();
                }else if (menuItem.getItemId() == R.id.mQuanLySach) {
                    fragment = new QuanLySachFragment();
                }else if (menuItem.getItemId() == R.id.mDoiMatKhau) {
                    showDialogDoiMatKhau();
                }



                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frameLayout, fragment)
                            .commit();

                    toolbar.setTitle(menuItem.getTitle());
                }



                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });

        // hiển thị chức năng cho adim
        SharedPreferences sharedPreferences = getSharedPreferences("thongtin", MODE_PRIVATE);
        String loai = sharedPreferences.getString("loai", "");
        if (loai.equals("thuthu")) {
            Menu menu = navigationView.getMenu();
            menu.findItem(R.id.mDoanhThu).setVisible(false);
            menu.findItem(R.id.mTop10).setVisible(false);
        }


    }

    public void showDialogDoiMatKhau() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_doimaukhau, null);
        EditText edtPassCu = view.findViewById(R.id.edtPassCu);
        EditText edtPassMoi = view.findViewById(R.id.edtPassMoi);
        EditText edtNhapLaiPassMoi = view.findViewById(R.id.edtNhapLaiPass);

        builder.setView(view);

        builder.setPositiveButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("Sửa mật khẩu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String PassCu =edtPassCu.getText().toString();
                String PassMoi = edtPassMoi.getText().toString();
                String NhapLaiPassMoi = edtNhapLaiPassMoi.getText().toString();
                if (PassMoi.equals(NhapLaiPassMoi)) {
                    SharedPreferences sharedPreferences = getSharedPreferences("thongtin", MODE_PRIVATE);
                    String tendangnhap =sharedPreferences.getString("tendangnhap", "");
                    // cap nhat
                    NguoiDungDao nguoiDungDao = new NguoiDungDao(MainActivity.this);
                    boolean check = nguoiDungDao.capNhatMatKhau(tendangnhap, PassCu, PassMoi);
                    if (check) {
                        Toast.makeText(MainActivity.this, "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }else {
                        Toast.makeText(MainActivity.this, "Cập nhật mật khẩu thất bại", Toast.LENGTH_SHORT).show();

                    }

                }else {
                    Toast.makeText(MainActivity.this, "Nhập mật khẩu không trùng", Toast.LENGTH_SHORT).show();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();



    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}