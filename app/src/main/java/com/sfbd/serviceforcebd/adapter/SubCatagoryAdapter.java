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

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.activity.AddressActivity;
import com.sfbd.serviceforcebd.model.Sd;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SubCatagoryAdapter extends RecyclerView.Adapter<SubCatagoryAdapter.MyViewHolder> {
    Context context;
    ArrayList<Sd> sd;
    String catagory;
    String num;
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
        String price= sd.get(position).getPrice().toString().trim();
        String name= sd.get(position).getName().toString().trim();


            holder.product_price.setText("Price"+sd.get(position).getPrice()+"Tk");
            holder.pro_des.setText(sd.get(position).getDes());
            holder.proNam.setText(sd.get(position).getName());
        Picasso.get().load(sd.get(position).getImage()).into(holder.pro_image);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, AddressActivity.class);
                intent.putExtra("category",catagory);
                intent.putExtra("proName",name);
                intent.putExtra("price",price);
                intent.putExtra("noOfItem",num);
                context.startActivity(intent);
            }
        });
        holder.addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.eli_button.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                num = holder.eli_button.getNumber();


            }
        });
        holder.eli_button.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                int p=Integer.parseInt(price);
                int sum=0;
                if(newValue==0)
                {
                    sum=1*p;
                    String s=String.valueOf(sum);
                    holder.product_price.setText("Price:"+s+"Tk");
                }
                else {
                    sum=newValue*p;
                    String s=String.valueOf(sum);
                    holder.product_price.setText("Price:"+s+"Tk");
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return sd.size();
    }

    class  MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView product_price,pro_des,proNam;
        ImageView pro_image;
        Button button,addCart;
        ElegantNumberButton eli_button;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            eli_button=itemView.findViewById(R.id.eligantbutton);
            product_price=itemView.findViewById(R.id.price11);
            pro_des=itemView.findViewById(R.id.productDec);
            pro_image=itemView.findViewById(R.id.proImg);
            button=itemView.findViewById(R.id.booking);
            pro_des.setMovementMethod(new ScrollingMovementMethod());
            proNam=itemView.findViewById(R.id.proName);
            addCart=itemView.findViewById(R.id.addToCart);
        }
    }
}
