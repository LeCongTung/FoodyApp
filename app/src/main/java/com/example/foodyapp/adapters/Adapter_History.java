package com.example.foodyapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodyapp.Layout_Cart;
import com.example.foodyapp.Layout_History;
import com.example.foodyapp.R;
import com.example.foodyapp.units.cart;
import com.example.foodyapp.units.history;

import java.util.List;

public class Adapter_History extends BaseAdapter {
    private Layout_History context;
    private int layout;
    private List<history> milkteaList;

    public Adapter_History(Context context, int layout, List<history> milkteaList) {
        this.context = (Layout_History) context;
        this.layout = layout;
        this.milkteaList = milkteaList;
    }

    @Override
    public int getCount() {
        return milkteaList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView tvid, tvprice, tvtime;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null){
            vh = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            vh.tvid = (TextView) view.findViewById(R.id.idDH);
            vh.tvprice = (TextView) view.findViewById(R.id.total);
            vh.tvtime = (TextView) view.findViewById(R.id.time);

            view.setTag(vh);
        }else {
            vh = (ViewHolder) view.getTag();
        }

        //Show datas
        history mt = milkteaList.get(i);
        vh.tvid.setText("Mã hóa đơn: HD" + String.valueOf(mt.getId()));
        vh.tvprice.setText(String.valueOf(mt.getTotal()) + " VNĐ");
        vh.tvtime.setText(mt.getDate());

        //Event: Add to cart with item selected
        return view;
    }
}
