package com.example.appbanxe.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.appbanxe.R;
import com.example.appbanxe.adpter.LoaiSphamAdapter;
import com.example.appbanxe.adpter.SanPhamMoiAdapter;
import com.example.appbanxe.model.LoaiSp;
import com.example.appbanxe.model.SanPhamMoi;
import com.example.appbanxe.model.SanPhamMoiModel;
import com.example.appbanxe.retroflit.Apibanhang;
import com.example.appbanxe.retroflit.RetroflitClent;
import com.example.appbanxe.utils.Utils;
import com.google.android.material.navigation.NavigationView;
import com.nex3z.notificationbadge.NotificationBadge;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewManHinhChinh;
    NavigationView navigationView;
    ListView listViewManHinhChinh;
    DrawerLayout drawerLayout;
    LoaiSphamAdapter loaiSphamAdapter;
    List<LoaiSp> mangloaisp;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Apibanhang apibanhang;
    List<SanPhamMoi> mangSpMoi;
    SanPhamMoiAdapter spAdapter;
    NotificationBadge badge;
    FrameLayout frameLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            apibanhang = RetroflitClent.getInstance(Utils.BASE_URL).create(Apibanhang.class);
            Anhxa();
            ActionBar();
            ActionViewFlipper();


            if (isConnected(this)){

                ActionViewFlipper();
                getLoaiSanpham();
                getSpMoi();
                getEventClick();
            }else {
                Toast.makeText(getApplicationContext(), "Khong có ket noi", Toast.LENGTH_LONG).show();
            }
        }

    private void getEventClick() {
        listViewManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Intent trangchu = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(trangchu);
                        break;
                    case 1:
                        Intent xemoto = new Intent(getApplicationContext(),XemotoActivity.class);
                        xemoto.putExtra("loai", 1);
                        startActivity(xemoto);
                        break;
                    case 2:
                        Intent xeoto = new Intent(getApplicationContext(),XemotoActivity.class);
                        xeoto.putExtra("loai",2);
                        startActivity(xeoto);
                        break;
                    case 5:
                        Intent donhang = new Intent(getApplicationContext(),XemDonActivity.class);
                        startActivity(donhang);
                        break;

                }
            }
        });
    }

    private void getSpMoi() {
        compositeDisposable.add(apibanhang.getSpMoi()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                sanPhamMoiModel -> {
                    if (sanPhamMoiModel.isSuccess()){
                        mangSpMoi = sanPhamMoiModel.getResult();
                        spAdapter = new SanPhamMoiAdapter(getApplicationContext(), mangSpMoi);
                        recyclerViewManHinhChinh.setAdapter(spAdapter);

                    }
                },
                throwable -> {
                    Toast.makeText(getApplicationContext(),"KO ket noi dc" + throwable.getMessage(), Toast.LENGTH_LONG). show();
                }
        ));
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }


    private void getLoaiSanpham() {
            compositeDisposable.add(apibanhang.getLoaiSp()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            loaiSpModel -> {
                                Log.d("te", "getLoaiSanpham: "+loaiSpModel.getResult().get(0).getTensanpham());
                                if (loaiSpModel.isSuccess()){
                                    mangloaisp = loaiSpModel.getResult();
                                    loaiSphamAdapter = new LoaiSphamAdapter(getApplicationContext(),mangloaisp);
                                    listViewManHinhChinh.setAdapter(loaiSphamAdapter);
                                    loaiSphamAdapter.notifyDataSetChanged();


                                }
                            }
                    ));
        }
// chuyen anh
        private void ActionViewFlipper() {
            List<String> mangquangcao = new ArrayList<>();
            mangquangcao.add("https://giaxe.2banh.vn/cache/dataupload/products/slides/520_368_324c79e471da256df7aed999fc0bd52a.jpg");
            mangquangcao.add("https://muaxegiatot.vn/wp-content/uploads/2018/09/gia-xe-lamborghini-aventador-lp700-4-muaxegiatot-vn.jpg");
            mangquangcao.add("https://giaxe.2banh.vn/dataupload/products/images/1529485418-2ef57d3f56cfc40f757040cfb4c05cdf.jpg");

            for (int i = 0; i<mangquangcao.size(); i++){
                ImageView imageView = new ImageView(getApplicationContext());
                Glide.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                viewFlipper.addView(imageView);
            }
            viewFlipper.setFlipInterval(3000);
            viewFlipper.setAutoStart(true);
            Animation slide_in_right = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.silede_in_fight);
            Animation slide_out_right = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sliede_out_right);
            viewFlipper.setInAnimation(slide_in_right);
            viewFlipper.setOutAnimation(slide_out_right);
        }



    // Thanh toolbar trong trang chủ chưa khỏi tạo
  

    private void Anhxa(){
        toolbar = findViewById(R.id.toobarmanhinhchinh);
        viewFlipper = findViewById(R.id.viewlipper);
        recyclerViewManHinhChinh = findViewById(R.id.recycleview);
        listViewManHinhChinh = findViewById(R.id.listviewmanhinhchinh);
        navigationView = findViewById(R.id.navigationview);
        drawerLayout = findViewById(R.id.drawerlayout);
        badge = findViewById(R.id.menuu_sl);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerViewManHinhChinh.setLayoutManager(layoutManager);
        recyclerViewManHinhChinh.setHasFixedSize(true);
        listViewManHinhChinh = findViewById(R.id.listviewmanhinhchinh);
        frameLayout = findViewById(R.id.framegiohang);
        //khoi tạo Lít
        mangloaisp  = new ArrayList<>();
        // khởi tạo adpter
        mangloaisp =  new ArrayList<>();
        mangSpMoi = new ArrayList<>();
        if (Utils.manggiohang == null){
            Utils.manggiohang = new ArrayList<>();
        }else {
            int totItem = 0;
            for (int i=0; i<Utils.manggiohang.size(); i++){
                totItem = totItem+Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totItem));
        }
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent giohang = new Intent(getApplicationContext(), GiohangActivity.class);
                startActivity(giohang);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        int totItem = 0;
        for (int i=0; i<Utils.manggiohang.size(); i++){
            totItem = totItem+Utils.manggiohang.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totItem));
    }

    private boolean isConnected(@NonNull Context context){
        ConnectivityManager connectivityManager =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (wifi != null && wifi.isConnected() || (mobile != null && mobile.isConnected()) ){
            return true;
        }else {
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}