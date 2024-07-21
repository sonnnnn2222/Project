package com.viethcn.shopbanhang;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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
import com.viethcn.shopbanhang.dao.SachDAO;
import com.viethcn.shopbanhang.fragment.QuanLyLoaiSachFragment;
import com.viethcn.shopbanhang.fragment.QuanLyPhieuMuonFragment;

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
                }


                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, fragment)
                        .commit();

                getSupportActionBar().setTitle(menuItem.getTitle());

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


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}