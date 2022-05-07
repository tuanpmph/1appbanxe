package com.example.appbanxe.adpter;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanxe.Interface.IImageClickListenner;
import com.example.appbanxe.R;
import com.example.appbanxe.model.EventBus.TinhtongEvent;
import com.example.appbanxe.model.GioHang;
import com.example.appbanxe.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class GiaHangAdapter extends RecyclerView.Adapter<GiaHangAdapter.MyViewHodel> {
Context context;
List<GioHang> gioHangList;

    public GiaHangAdapter(Context context, List<GioHang> gioHangList) {
        this.context = context;
        this.gioHangList = gioHangList;
    }

    @NonNull
    @Override
    public MyViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang, parent, false);
        return new MyViewHodel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHodel holder, int position) {
         GioHang gioHang = gioHangList.get(position);
         holder.item_giohang_tensp.setText(gioHang.getTensp());

         holder.item_giohang_soluong.setText(gioHang.getSoluong() +" ");
        Glide.with(context).load(gioHang.getHinhsp()).into(holder.item_giohang_image);
        DecimalFormat decimalFormat = new java.text.DecimalFormat("###,###,###");
        holder.item_giohang_gia.setText(decimalFormat.format((gioHang.getGiasp())));
        long gia = gioHang.getSoluong() * gioHang.getGiasp();
        holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
        holder.setListenner(new IImageClickListenner() {
            @Override
            public void onImageClick(View view, int pos, int giatri) {
                Log.d("TAG", "onImageClick: "+ pos +"..."+giatri);
                if (giatri == 1){
                    if (gioHangList.get(pos).getSoluong() > 1){
                        int soluongmoi = gioHangList.get(pos).getSoluong()-1;
                        gioHangList.get(pos).setSoluong(soluongmoi);
                        holder.item_giohang_soluong.setText(gioHangList.get(pos).getSoluong() +" ");
                        long gia = gioHangList.get(pos).getSoluong()* gioHangList.get(pos).getGiasp();
                        holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
                        EventBus.getDefault().postSticky(new TinhtongEvent());
                    }
                    else if (gioHangList.get(pos).getSoluong() == 1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thông Báo");
                        builder.setMessage("Bạn có muốn xóa sản phẩm này khỏi giỏ hàng");
                        builder.setPositiveButton("Đồng ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Utils.manggiohang.remove(pos);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new TinhtongEvent());

                            }
                        });
                        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                     builder.show();
                    }
                }else if (giatri == 2){
                    if (gioHangList.get(pos).getSoluong() < 11){
                        int soluongmoi = gioHangList.get(pos).getSoluong()+1;
                        gioHangList.get(pos).setSoluong(soluongmoi);
                    }
                    holder.item_giohang_soluong.setText(gioHangList.get(pos).getSoluong() +" ");
                    long gia = gioHangList.get(pos).getSoluong()* gioHangList.get(pos).getGiasp();
                    holder.item_giohang_giasp2.setText(decimalFormat.format(gia));
                    EventBus.getDefault().postSticky(new TinhtongEvent());
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return gioHangList.size();
    }


    public class MyViewHodel extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView item_giohang_image,imgtru, imgcong;
        TextView item_giohang_tensp,  item_giohang_gia,item_giohang_soluong,item_giohang_giasp2;
        IImageClickListenner listenner;
        public MyViewHodel(@NonNull View itemView) {
            super(itemView);
            item_giohang_image = itemView.findViewById(R.id.item_giohang_image);
            item_giohang_tensp = itemView.findViewById(R.id.item_giohang_tensp);
            item_giohang_gia = itemView.findViewById(R.id.item_giohang_gia);
            item_giohang_giasp2 = itemView.findViewById(R.id.item_giohang_giasp2);
            item_giohang_soluong = itemView.findViewById(R.id.item_giohang_soluong);
            imgtru = itemView.findViewById(R.id.item_giohang_tru);
            imgcong = itemView.findViewById(R.id.item_giohang_cong);

            //event click

            imgcong.setOnClickListener(this);
            imgtru.setOnClickListener(this);

        }

        public void setListenner(IImageClickListenner listenner) {
            this.listenner = listenner;
        }

        @Override
        public void onClick(View view) {
            if (view == imgtru){
                listenner.onImageClick(view, getAdapterPosition(), 1);
            }else if (view == imgcong){
                listenner.onImageClick(view, getAdapterPosition(),2);

            }
        }
    }
}
