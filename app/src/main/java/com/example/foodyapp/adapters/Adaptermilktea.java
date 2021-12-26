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
import com.example.foodyapp.Show_AddProduct;
import com.example.foodyapp.units.product;

import java.util.List;

public class Adaptermilktea extends BaseAdapter {
    private Show_AddProduct context;
    private int layout;
    private List<product> milkteaList;

    public Adaptermilktea(Context context, int layout, List<product> milkteaList) {
        this.context = (Show_AddProduct) context;
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
        TextView tvname, tvprice, tvtype, tvlocation;
        ImageView imageitem, btnedit, btndelete;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null){
            vh = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            vh.tvname = (TextView) view.findViewById(R.id.name);
            vh.tvtype = (TextView) view.findViewById(R.id.type);
            vh.tvprice = (TextView) view.findViewById(R.id.price);
            vh.tvlocation = (TextView) view.findViewById(R.id.location);
            vh.imageitem = (ImageView) view.findViewById(R.id.image);

            vh.btnedit = (ImageView) view.findViewById(R.id.btnedit);
            vh.btndelete = (ImageView) view.findViewById(R.id.btndelete);
            view.setTag(vh);
        }else {
            vh = (ViewHolder) view.getTag();
        }

        //Show datas
        product mt = milkteaList.get(i);
        vh.tvname.setText(mt.getName());
        vh.tvtype.setText(String.valueOf(mt.getType()));
        vh.tvprice.setText("Giá: " + String.valueOf(mt.getPrice()) + " VNĐ");
        vh.tvlocation.setText(mt.getLocation());

        byte[] imageM = mt.getImage();
        Bitmap bm = BitmapFactory.decodeByteArray(imageM, 0, imageM.length);
        vh.imageitem.setImageBitmap(bm);

        //Event: Edit a data
        vh.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                context.DialogUpdate(mb.getName(), mb.getQuantity(), mb.getPrice(), mb.getDescription(),mb.getImage(), mb.getId());
            }
        });

        //Event: Delete a data
        vh.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogDelete(mt.getName(), mt.getId());
            }
        });
        return view;
   }
}
