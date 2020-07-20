package com.sfbd.serviceforcebd.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.activity.AddressActivity;
import com.sfbd.serviceforcebd.activity.LocationActivity;
import com.sfbd.serviceforcebd.activity.ServiceDetails;
import com.sfbd.serviceforcebd.activity.SubCatagoryDetails;
import com.sfbd.serviceforcebd.databinding.RvServiceItemBinding;

import static android.widget.Toast.LENGTH_LONG;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    private Context context;
    private String[] serviceList;
    private String service;
    private int i;


    public ServiceAdapter(Context context, String[] serviceList,String service) {
        this.context = context;
        this.serviceList = serviceList;
        this.service=service;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RvServiceItemBinding binding = RvServiceItemBinding.inflate(LayoutInflater.from(context), parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.itemTV.setText(serviceList[position]);

        holder.itemView.setOnClickListener(view -> {

        i=position;
            Intent intent = new Intent(context, SubCatagoryDetails.class);
            intent.putExtra("service_name",service);
            intent.putExtra("category",serviceList[position]);


            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return serviceList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RvServiceItemBinding binding;
        public ViewHolder(@NonNull RvServiceItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
