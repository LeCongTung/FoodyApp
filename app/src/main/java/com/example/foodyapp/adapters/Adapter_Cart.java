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
import com.example.foodyapp.R;
import com.example.foodyapp.show.Show_ListType;
import com.example.foodyapp.units.cart;
import com.example.foodyapp.units.product;

import java.util.List;

public class Adapter_Cart extends BaseAdapter {
    private Layout_Cart context;
    private int layout;
    private List<cart> milkteaList;

    public Adapter_Cart(Context context, int layout, List<cart> milkteaList) {
        this.context = (Layout_Cart) context;
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
        TextView tvname, tvprice, tvlocation, tvquantity;
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
            vh.tvlocation = (TextView) view.findViewById(R.id.location);
            vh.tvquantity = (TextView) view.findViewById(R.id.quantity);

            vh.imageitem = (ImageView) view.findViewById(R.id.image);

            view.setTag(vh);
        }else {
            vh = (ViewHolder) view.getTag();
        }


        //Show datas
        cart mt = milkteaList.get(i);
        vh.tvname.setText(mt.getName());
        vh.tvprice.setText("Giá: " + String.valueOf(mt.getPrice()) + " VNĐ");
        vh.tvlocation.setText(mt.getLocation());
        vh.tvquantity.setText(""+ mt.getQuantity());

        byte[] imageM = mt.getImage();
        Bitmap bm = BitmapFactory.decodeByteArray(imageM, 0, imageM.length);
        vh.imageitem.setImageBitmap(bm);

        //Event: Add to cart with item selected
        vh.imageitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogUpdate(mt.getName(), mt.getPrice(), mt.getQuantity(), mt.getImage(), mt.getLocation(), mt.getId());
            }
        });
        return view;
   }
}
