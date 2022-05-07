package com.example.appbanxe.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appbanxe.R;
import com.example.appbanxe.retroflit.Apibanhang;
import com.example.appbanxe.retroflit.RetroflitClent;
import com.example.appbanxe.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DangKyActivity extends AppCompatActivity {
    EditText email, pass, repass, mobile, username;
    AppCompatButton button;
    Apibanhang apibanhang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);
        initView();
        initControll();
    }

    private void initControll() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangKi();
            }
        });
    }

    private void dangKi() {
        String str_email = email.getText().toString().trim();
        String str_pass = pass.getText().toString().trim();
        String str_repass = repass.getText().toString().trim();
        String str_mobile =mobile.getText().toString().trim();
        String str_user =username.getText().toString().trim();
        if (TextUtils.isEmpty(str_email)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập ", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(str_pass)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập Pass", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(str_repass)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập Repass", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(str_mobile)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập Số",Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(str_user)){
            Toast.makeText(getApplicationContext(),"Bạn chưa nhập Tên",Toast.LENGTH_SHORT).show();
        }
        else {
           if (str_pass.equals(str_repass)){
               // past data
               compositeDisposable.add(apibanhang.dangKi(str_email, str_pass, str_user, str_mobile)
                       .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(
                       userModel -> {
                           if (userModel.isSuccess()){
                               Utils.user_current.setEmail(str_email);
                               Utils.user_current.setPass(str_pass);
                               Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                               startActivity(intent);

                           }else {
                               Toast.makeText(getApplicationContext(), userModel.getMessage(), Toast.LENGTH_SHORT).show();
                           }
                       },
                       throwable -> {
                           Toast.makeText(getApplicationContext(),throwable.getMessage(), Toast.LENGTH_SHORT).show();
                       }
               ));


           }else {
               Toast.makeText(getApplicationContext(),"Pass chưa khớp ", Toast.LENGTH_SHORT).show();
           }
        }
    }

    private void initView() {
       apibanhang = RetroflitClent.getInstance(Utils.BASE_URL).create(Apibanhang.class);

        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        repass = findViewById(R.id.repass);
        mobile = findViewById(R.id.mobile);
        username = findViewById(R.id.username);
        button = findViewById(R.id.btndangki);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}