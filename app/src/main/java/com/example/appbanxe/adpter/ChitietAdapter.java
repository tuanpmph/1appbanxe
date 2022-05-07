package com.example.appbanxe.adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanxe.R;
import com.example.appbanxe.model.Item;

import java.util.List;

public class ChitietAdapter extends RecyclerView.Adapter<ChitietAdapter.MyViewHolder> {
    List<Item> itemList;
    public ChitietAdapter(List<Item> itemList){
        this.itemList = itemList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitiet, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.txtten.setText(item.getTensp() + " ");

    }

    @Override
    public int getItemCount() {

        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imagechitiet;
        TextView txtten,txtsoluong;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imagechitiet = itemView.findViewById(R.id.item_imgchitiet);
            txtten = itemView.findViewById(R.id.item_temspchotiet);
            txtsoluong = itemView.findViewById(R.id.item_soluongchitiet);

        }
    }
}
