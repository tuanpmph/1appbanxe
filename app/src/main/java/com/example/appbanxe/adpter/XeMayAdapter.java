package com.example.appbanxe.adpter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanxe.Interface.ItemClickListener;
import com.example.appbanxe.R;
import com.example.appbanxe.activity.ChitietActivity;
import com.example.appbanxe.model.SanPhamMoi;

import java.text.DecimalFormat;
import java.util.List;

public class XeMayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    List<SanPhamMoi> array;
    private  static final  int VIEW_TYPE_DATA = 0;
    private  static final  int VIEW_TYPE_LOADING = 1;

    public XeMayAdapter(Context context, List<SanPhamMoi> array) {
        this.context = context;
        this.array = array;
    }






    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType ==VIEW_TYPE_DATA){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_xemay, parent, false);
            return  new MyViewHolder(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent,false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
       if (holder instanceof MyViewHolder){
           MyViewHolder myViewHolder = (MyViewHolder) holder;
           SanPhamMoi sanPham = array.get(position);
           myViewHolder.tensp.setText(sanPham.getTensp());
           DecimalFormat decimalFormat = new java.text.DecimalFormat("###,###,###");
           myViewHolder.giasp.setText("Gia: " + decimalFormat.format(Double.parseDouble(sanPham.getGiasp())) +"Đ");
           myViewHolder.mota.setText(sanPham.getMotasp());
           myViewHolder.idsp.setText(sanPham.getId() + "");
           Glide.with(context).load(sanPham.getHinhanh()).into(myViewHolder.hinhanh);
           myViewHolder.setItemClickListener(new ItemClickListener() {
               @Override
               public void onClick(View view, int pos, boolean isLongClick) {
                   if (!isLongClick){
                       Intent intent = new Intent(context, ChitietActivity.class);
                       intent.putExtra("chitiet",sanPham);
                       intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       context.startActivity(intent);
                   }
               }
           });
       }else {
           LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
           loadingViewHolder.progressBar.setIndeterminate(true);
       }
    }

    @Override
    public int getItemViewType(int position) {
        return array.get(position) == null ? VIEW_TYPE_LOADING:VIEW_TYPE_DATA;
    }

    @Override
    public int getItemCount() {
        return array.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tensp, giasp, mota,idsp;
        ImageView hinhanh;
        private ItemClickListener itemClickListener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tensp = itemView.findViewById(R.id.itemxm_ten);
            giasp = itemView.findViewById(R.id.itemxm_gia);
            mota = itemView.findViewById(R.id.itemxm_mota);
            idsp = itemView.findViewById(R.id.itemxm_idsp);
            hinhanh = itemView.findViewById(R.id.itemdt_image);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }
    }


    public class LoadingViewHolder extends  RecyclerView.ViewHolder {
        ProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);
        }
    }
}
