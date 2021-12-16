package com.example.foodyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class MilkTeaAdapter extends BaseAdapter {

    private ShowProduct context;
    private int layout;
    private List<MilkTea> mtlist;

    public MilkTeaAdapter(ShowProduct context, int layout, List<MilkTea> mtlist) {
        this.context = context;
        this.layout = layout;
        this.mtlist = mtlist;
    }

    @Override
    public int getCount() {
        return mtlist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class viewHolder{
        TextView tvid, tvname, tvprice, tvlocation;
        ImageView btnedit, btndelete;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        viewHolder vh;
        if (view == null){
            vh = new viewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            vh.tvid = (TextView) view.findViewById(R.id.tvid);
            vh.tvname = (TextView) view.findViewById(R.id.tvname);
            vh.tvprice = (TextView) view.findViewById(R.id.tvprice);
            vh.tvprice = (TextView) view.findViewById(R.id.tvprice);
            vh.tvlocation = (TextView) view.findViewById(R.id.tvlocation);

            vh.btnedit = (ImageView) view.findViewById(R.id.btnedit);
            vh.btndelete = (ImageView) view.findViewById(R.id.btndelete);
            view.setTag(vh);
        }else {
            vh = (viewHolder) view.getTag();
        }
        final MilkTea dt = mtlist.get(i);
        vh.tvid.setText(String.valueOf(dt.getIdMT()) + ".");
        vh.tvname.setText(dt.getNameMT());
        vh.tvprice.setText("Giá tiền: " + String.valueOf(dt.getPriceMT()) + " VNĐ");
        vh.tvlocation.setText("Địa điểm: " + dt.getLocationMT());

        //Event: Edit elements
        vh.btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogUpdate(dt.getNameMT(), dt.getPriceMT(), dt.getLocationMT(), dt.getIdMT());

            }
        });

        //Event: Delete elements
        vh.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }
}
