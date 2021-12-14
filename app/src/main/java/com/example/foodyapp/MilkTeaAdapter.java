package com.example.foodyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class MilkTeaAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<MilkTea> MilkTealist;

    public MilkTeaAdapter(Context context, int layout, List<MilkTea> milkTealist) {
        this.context = context;
        this.layout = layout;
        MilkTealist = milkTealist;
    }

    @Override
    public int getCount() {
        return MilkTealist.size();
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
        TextView name, price, location;
        Button btnedit, btndelete;
    }

    @Override
    public View getView(int postion, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null){
            vh = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            vh.name = (TextView) convertView.findViewById(R.id.nameProduct);
//            vh.price = (TextView) convertView.findViewById(R.id.priceProduct);
            vh.location = (TextView) convertView.findViewById(R.id.locationProduct);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }

        MilkTea mt = MilkTealist.get(postion);
        vh.name.setText(mt.getName());
//        vh.price.setText(mt.getPrice());
        vh.location.setText(mt.getLocation());
        return convertView;
    }
}
