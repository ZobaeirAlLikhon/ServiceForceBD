package com.sfbd.serviceforcebd.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.activity.AddressActivity;
import com.sfbd.serviceforcebd.model.Sd;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SubCatagoryAdapter extends RecyclerView.Adapter<SubCatagoryAdapter.MyViewHolder> {
    Context context;
    ArrayList<Sd> sd;
    String catagory;
    public SubCatagoryAdapter(Context c,ArrayList<Sd> s ,String cat)
    {
        context=c;
        sd=s;
        catagory=cat;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.s_catagory_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.product_price.setText(sd.get(position).getPrice());
            holder.pro_des.setText(sd.get(position).getDes());
        Picasso.get().load(sd.get(position).getImage()).into(holder.pro_image);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, AddressActivity.class);
                intent.putExtra("category",catagory);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sd.size();
    }

    class  MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView product_price,pro_des;
        ImageView pro_image;
        Button button;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            product_price=itemView.findViewById(R.id.price11);
            pro_des=itemView.findViewById(R.id.productDec);
            pro_image=itemView.findViewById(R.id.proImg);
            button=itemView.findViewById(R.id.booking);
            pro_des.setMovementMethod(new ScrollingMovementMethod());

        }
    }
}
