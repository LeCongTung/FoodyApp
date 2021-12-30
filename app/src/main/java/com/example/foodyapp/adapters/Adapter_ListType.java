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

import com.example.foodyapp.R;
import com.example.foodyapp.show.Show_AddProduct;
import com.example.foodyapp.show.Show_ListType;
import com.example.foodyapp.units.cart;
import com.example.foodyapp.units.product;

import java.util.List;
import java.util.Random;

public class Adapter_ListType extends BaseAdapter {
    private Show_ListType context;
    private int layout;
    private List<product> milkteaList;

    public Adapter_ListType(Context context, int layout, List<product> milkteaList) {
        this.context = (Show_ListType) context;
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
        TextView tvname, tvprice, tvtype, tvlocation, tvrate;
        ImageView imageitem;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null){
            vh = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            vh.tvname = (TextView) view.findViewById(R.id.name);
            vh.tvprice = (TextView) view.findViewById(R.id.price);
            vh.tvtype = (TextView) view.findViewById(R.id.type);
            vh.tvlocation = (TextView) view.findViewById(R.id.description);
            vh.tvrate = (TextView) view.findViewById(R.id.rate);

            vh.imageitem = (ImageView) view.findViewById(R.id.image);

            view.setTag(vh);
        }else {
            vh = (ViewHolder) view.getTag();
        }

        //Set random number for rate
        double rand = 7 + (Math.random() * 3);
        double finalnumberrate = Math.round(rand * 10.0)/10.0;

        //Show datas
        product mt = milkteaList.get(i);
        vh.tvname.setText(mt.getName());
        vh.tvprice.setText("Giá: " + String.valueOf(mt.getPrice()) + " VNĐ");
        vh.tvtype.setText(mt.getType());
        vh.tvlocation.setText(mt.getLocation());
        vh.tvrate.setText(""+ finalnumberrate);

        byte[] imageM = mt.getImage();
        Bitmap bm = BitmapFactory.decodeByteArray(imageM, 0, imageM.length);
        vh.imageitem.setImageBitmap(bm);

        //Event: Add to cart with item selected
        vh.imageitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                context.DialogUpdate(mt.getName(), mt.getPrice(), mt.getType(), mt.getLocation(), mt.getDescription(),mt.getImage(), mt.getId());
            }
        });
        return view;
   }
}
