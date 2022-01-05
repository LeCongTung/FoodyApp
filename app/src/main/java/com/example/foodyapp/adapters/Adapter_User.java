package com.example.foodyapp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.foodyapp.R;
import com.example.foodyapp.show.Show_UserDetail;
import com.example.foodyapp.units.cart;
import com.example.foodyapp.units.user;

import java.util.List;

public class Adapter_User extends BaseAdapter {
    private Show_UserDetail context;
    private int layout;
    private List<user> milkteaList;

    public Adapter_User(Context context, int layout, List<user> milkteaList) {
        this.context = (Show_UserDetail) context;
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
        TextView tvname, tvphonenumber, tvuser, tvlocation;
        Button btnedit;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null){
            vh = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            vh.tvname = (TextView) view.findViewById(R.id.name);
            vh.tvphonenumber = (TextView) view.findViewById(R.id.phonenumber);
            vh.tvuser = (TextView) view.findViewById(R.id.user);
            vh.tvlocation = (TextView) view.findViewById(R.id.location);

            vh.btnedit = (Button) view.findViewById(R.id.btnedit);

            view.setTag(vh);
        }else {
            vh = (ViewHolder) view.getTag();
        }

        //Show datas
        user mt = milkteaList.get(i);
        vh.tvname.setText(mt.getName());
        vh.tvphonenumber.setText(mt.getPhonenumber());
        vh.tvuser.setText(mt.getUser());
        vh.tvlocation.setText(mt.getLocation());

        //Event: Add to cart with item selected
        vh.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                context.DialogUpdate(mt.getName(), mt.getPrice(), mt.getQuantity(), mt.getImage(), mt.getLocation(), mt.getId());
            }
        });
        return view;
    }
}
