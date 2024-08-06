package com.viethcn.shopbanhang;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.viethcn.shopbanhang.dao.NguoiDungDao;

public class DangKy extends AppCompatActivity {
    private NguoiDungDao nguoiDungDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_ky);
        EditText edtUser = findViewById(R.id.edtUseName);
        EditText edtPass = findViewById(R.id.edtPassWord);
        EditText edtRePass = findViewById(R.id.edtRePass);
        ImageView imageView = findViewById(R.id.imgRegister);
        TextView txtBack = findViewById(R.id.txtBack);
        nguoiDungDao = new NguoiDungDao(this);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUser.getText().toString();
                String pass = edtPass.getText().toString();
                String rePass = edtRePass.getText().toString();


                if (!pass.equals(rePass)) {
                    Toast.makeText(DangKy.this, "Không trùng mật khẩu", Toast.LENGTH_SHORT).show();
                }else {
                    boolean check = nguoiDungDao.Register(user, pass, rePass);
                    if (check) {
                        Toast.makeText(DangKy.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(DangKy.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DangKy.this, Login.class));
            }
        });
    }
}