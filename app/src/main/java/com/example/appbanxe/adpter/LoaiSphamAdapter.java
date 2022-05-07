package com.example.appbanxe.adpter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.appbanxe.R;
import com.example.appbanxe.model.LoaiSp;

import java.util.List;

public class LoaiSphamAdapter extends BaseAdapter {
    List<LoaiSp> array;
    Context context;
    public LoaiSphamAdapter(Context context,List<LoaiSp> array) {
        this.array = array;
        this.context = context;
    }
    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int position) {
        return array.get(position);
    }
    public class ViewHolder{
        TextView textensp;
        ImageView imageHinhanh;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_sanpham, null);
            viewHolder.textensp = view.findViewById(R.id.item_tensp);
            viewHolder.imageHinhanh = view.findViewById(R.id.item_image);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textensp.setText(array.get(i).getTensanpham());
        Log.d("te","getView: "+array.get(i).getTensanpham());
        Glide.with(context).load(array.get(i).getHinhanh()).into(viewHolder.imageHinhanh);
        return view;
    }
}
