package com.example.appbanxe.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.appbanxe.R;
import com.example.appbanxe.adpter.XeMayAdapter;
import com.example.appbanxe.model.SanPhamMoi;
import com.example.appbanxe.retroflit.Apibanhang;
import com.example.appbanxe.retroflit.RetroflitClent;
import com.example.appbanxe.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class XemotoActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    Apibanhang apibanhang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int page =1;
    int loai ;
    XeMayAdapter adapterxm;
    List<SanPhamMoi> sanPhamMoiList;
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xemoto);
        apibanhang = RetroflitClent.getInstance(Utils.BASE_URL).create(Apibanhang.class);
        loai = getIntent().getIntExtra("loai", 1);
        AnhXa();
        ActionToolbar();
        getData(page);
        addEcentLoad();
    }

    private void addEcentLoad() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isLoading == false ){
                    if (linearLayoutManager.findFirstCompletelyVisibleItemPosition() == sanPhamMoiList.size()-1){
                        isLoading = true;
                        loadMore();
                    }
                }
            }
        });
    }

    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                sanPhamMoiList.add(null);
                adapterxm.notifyItemInserted(sanPhamMoiList.size()-1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sanPhamMoiList.remove(sanPhamMoiList.size()-1);
                adapterxm.notifyItemInserted(sanPhamMoiList.size());
                page = page+1;
                getData(page);
                adapterxm.notifyDataSetChanged();
                isLoading = false;
            }
        },2000);
    }

    private void getData(int page) {
         compositeDisposable.add(apibanhang.getSanPham(page, loai)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                sanPhamMoiModel -> {

                    if (sanPhamMoiModel.isSuccess()){
                        if (adapterxm == null){
                            sanPhamMoiList = sanPhamMoiModel.getResult();
                            adapterxm = new XeMayAdapter(getApplicationContext(), sanPhamMoiList);
                            recyclerView.setAdapter(adapterxm);
                        }else {
                            int vitri = sanPhamMoiList.size()-1;
                            int soluongadd = sanPhamMoiModel.getResult().size();
                            for (int i= 0 ; i<soluongadd; i++){
                                sanPhamMoiList.add(sanPhamMoiModel.getResult().get(i));
                            }
                            adapterxm.notifyItemRangeInserted(vitri,soluongadd);
                        }
                        sanPhamMoiList = sanPhamMoiModel.getResult();
                        adapterxm = new XeMayAdapter(getApplicationContext(), sanPhamMoiList);
                        recyclerView.setAdapter(adapterxm);
                    }
                },
                throwable -> {
                    Toast.makeText(getApplicationContext(),"Ko ket noi saver", Toast.LENGTH_LONG).show();
                }
        ));
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void AnhXa() {
        toolbar = findViewById(R.id.toobar);
        recyclerView = findViewById(R.id.recycleview_xm);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        sanPhamMoiList = new ArrayList<>();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}