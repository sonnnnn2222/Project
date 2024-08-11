package com.viethcn.shopbanhang;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.viethcn.shopbanhang.dao.NguoiDungDao;

public class Login extends AppCompatActivity {
    private NguoiDungDao nguoiDungDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Ánh xạ các thành phần giao diện
        EditText edtUser = findViewById(R.id.edtUserName);
        EditText edtPassWord = findViewById(R.id.edtPassWord);
        ImageView imageView = findViewById(R.id.imgLogin);
        TextView txtForgot = findViewById(R.id.txtForgot);
        TextView txtSignUp = findViewById(R.id.txtSignUp);
        nguoiDungDao = new NguoiDungDao(this);

        // Xử lý sự kiện khi nhấn vào nút đăng nhập
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUser.getText().toString().trim();
                String pass = edtPassWord.getText().toString().trim();

                if (user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(Login.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra thông tin đăng nhập
                boolean check = nguoiDungDao.CheckLogin(user, pass);

                if (check) {
                    Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, MainActivity.class));
                } else {
                    Toast.makeText(Login.this, "Đăng nhập thất bại. Vui lòng kiểm tra lại tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Xử lý sự kiện khi nhấn vào nút đăng ký
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, DangKy.class));
            }
        });

        // Xử lý sự kiện khi nhấn vào nút quên mật khẩu
        txtForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogForgot();
            }
        });
    }
//
    private void showDialogForgot() {
        // Tạo và hiển thị hộp thoại quên mật khẩu
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_forgot, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // Ánh xạ các thành phần trong hộp thoại
        EditText edtUsername = view.findViewById(R.id.edtUserName);
        Button btnSend = view.findViewById(R.id.btnSend);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        // Xử lý sự kiện khi nhấn vào nút hủy
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        // Xử lý sự kiện khi nhấn vào nút gửi
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                if (username.isEmpty()) {
                    Toast.makeText(Login.this, "Vui lòng nhập tên người dùng hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                String matkhau = nguoiDungDao.ForgotPassWordByUsername(username);
                if (matkhau != null) {
                    // Hiển thị mật khẩu lên màn hình
                    Toast.makeText(Login.this, "Mật khẩu của bạn là: " + matkhau, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Login.this, "Tên người dùng không tồn tại trong hệ thống", Toast.LENGTH_SHORT).show();
                }
                alertDialog.dismiss();
            }
        });
    }

}
