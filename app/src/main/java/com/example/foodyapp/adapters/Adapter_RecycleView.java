package com.example.foodyapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodyapp.R;
import com.example.foodyapp.units.voucher;

import java.util.ArrayList;
import java.util.List;

public class Adapter_RecycleView extends RecyclerView.Adapter<Adapter_RecycleView.ViewHolder>{

    private List<voucher> arrayImage = new ArrayList<>();
    private Context context;

    public Adapter_RecycleView(Context context, List<voucher> arrayImage) {
        this.arrayImage = arrayImage;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.interface_item_inhome, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        voucher v = arrayImage.get(position);

        holder.image.setImageResource(v.getImage());

    }

    @Override
    public int getItemCount() {
        return arrayImage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }
}
