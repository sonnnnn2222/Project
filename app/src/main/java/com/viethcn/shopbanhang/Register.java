package com.viethcn.shopbanhang;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.viethcn.shopbanhang.dao.NguoiDungDAO;

public class Register extends AppCompatActivity {
    private NguoiDungDAO nguoiDungDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        EditText edtUser = findViewById(R.id.edtUser);
        EditText edtPass = findViewById(R.id.edtPass);
        EditText edtRePass = findViewById(R.id.edtRePass);
        EditText edtEmail = findViewById(R.id.edtEmail);
        Button btnRegister = findViewById(R.id.btnRegister);
        Button btnGoBack = findViewById(R.id.btnGoBack);

        nguoiDungDAO = new NguoiDungDAO(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUser.getText().toString();
                String pass = edtPass.getText().toString();
                String repass = edtRePass.getText().toString();
                String email = edtEmail.getText().toString();

                if (!pass.equals(repass)){
                    Toast.makeText(Register.this, "Nhap 2 mat khau khong trung nhau", Toast.LENGTH_SHORT).show();
                }else{
                    boolean check = nguoiDungDAO.Register(user ,pass,email);
                    if (check){
                        Toast.makeText(Register.this, "Dang Ki thanh cong", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(Register.this, "Dang ki that bai", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    }
