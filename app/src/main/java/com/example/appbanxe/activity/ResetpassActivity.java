package com.example.appbanxe.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.appbanxe.R;
import com.example.appbanxe.retroflit.Apibanhang;
import com.example.appbanxe.retroflit.RetroflitClent;
import com.example.appbanxe.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ResetpassActivity extends AppCompatActivity {
    EditText email;
    AppCompatButton btnreset;
    Apibanhang apibanhang;
    CompositeDisposable compositeDisposable  = new CompositeDisposable();
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpass);
        initView();
        initControll();
    }

    private void initControll() {
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_email = email.getText().toString().trim();
                if (TextUtils.isEmpty(str_email)){
                    Toast.makeText(getApplicationContext(),"Ban chua dang nhap",Toast.LENGTH_SHORT).show();

                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    compositeDisposable.add(apibanhang.resetPass(str_email)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            userModel -> {
                                if (userModel.isSuccess()){
                                    Toast.makeText(getApplicationContext(),userModel.getMessage(),Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else {

                                    Toast.makeText(getApplicationContext(), userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                progressBar.setVisibility(View.INVISIBLE);
                            },
                            throwable -> {
                                Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                    ));

                }
            }
        });
    }

    private void initView() {
        apibanhang = RetroflitClent.getInstance(Utils.BASE_URL).create(Apibanhang.class);
        email  = findViewById(R.id.edtresetpass);
        btnreset = findViewById(R.id.btnresetpass);
        progressBar = findViewById(R.id.progressbar);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}