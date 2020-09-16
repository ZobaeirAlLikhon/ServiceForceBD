package com.sfbd.serviceforcebd.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.activity.AddressActivity;
import com.sfbd.serviceforcebd.activity.Silk;
import com.sfbd.serviceforcebd.model.CartModel;
import com.sfbd.serviceforcebd.model.Sd;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Scanner;

public class
SubCatagoryAdapter extends RecyclerView.Adapter<SubCatagoryAdapter.MyViewHolder> {
    Context context;
    ArrayList<Sd> sd;
    String catagory;
    String num,price,name,s,su,des;
    private FirebaseUser firebaseUser;
    DatabaseReference dbRef;
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

        if(catagory.equals("Get a Tutor"))
        {
            holder.addCart.setVisibility(View.INVISIBLE);
            holder.eli_button.setVisibility(View.INVISIBLE);
            holder.iviprice.setVisibility(View.VISIBLE);
            holder.product_price.setVisibility(View.INVISIBLE);
            holder.button.setVisibility(View.INVISIBLE);
            holder.button1.setVisibility(View.VISIBLE);
        }




            name= sd.get(position).getName().toString().trim();
            des= sd.get(position).getDes().toString().trim();


            holder.product_price.setText("Start from:"+sd.get(position).getPrice()+"Tk");
            holder.iviprice.setText("Start from:"+sd.get(position).getPrice()+"Tk");
            holder.pro_des.setText(sd.get(position).getDes());
            holder.proNam.setText(sd.get(position).getName());
//            Picasso.get().load(sd.get(position).getImage()).into(holder.pro_image);
        Glide.with(context).load(sd.get(position).getImage()).into(holder.pro_image);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                price= sd.get(position).getPrice().toString().trim();
                num = holder.eli_button.getNumber();
                String name1= sd.get(position).getName().toString().trim();
                if (num==null)
                {
                    num="1";
                }
                if(s==null)
                {
                    s=price;
                }
                int n=Integer.parseInt(num);
                int p=Integer.parseInt(price);
                int sum=n*p;
                su=String.valueOf(sum);

                Intent intent=new Intent(context, AddressActivity.class);
                intent.putExtra("category",catagory);
                intent.putExtra("proName",name1);
                intent.putExtra("price",su);
                intent.putExtra("noOfItem",num);
                context.startActivity(intent);
                s=null;
                num=null;
            }
        });
        holder.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                price= sd.get(position).getPrice().toString().trim();
                num = holder.eli_button.getNumber();
                String name1= sd.get(position).getName().toString().trim();
                if (num==null)
                {
                    num="1";
                }
                if(s==null)
                {
                    s=price;
                }
                int n=Integer.parseInt(num);
                int p=Integer.parseInt(price);
                int sum=n*p;
                su=String.valueOf(sum);

                Intent intent=new Intent(context, AddressActivity.class);
                intent.putExtra("category",catagory);
                intent.putExtra("proName",name1);
                intent.putExtra("price",su);
                intent.putExtra("noOfItem",num);
                context.startActivity(intent);
                s=null;
                num=null;
            }
        });
        holder.addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                String currentUserId = firebaseUser.getUid();
                String name2= sd.get(position).getName().toString().trim();
                Toast.makeText(context,"Add To Cart",Toast.LENGTH_LONG).show();
                    if (num==null)
                    {
                        num="1";
                    }
                price= sd.get(position).getPrice().toString().trim();
                num = holder.eli_button.getNumber();
                if (num==null)
                {
                    num="1";
                }
                if(s==null)
                {
                    s=price;
                }
                int n=Integer.parseInt(num);
                int p=Integer.parseInt(price);
                int sum=n*p;
                su=String.valueOf(sum);
                dbRef= FirebaseDatabase.getInstance().getReference().child("Cart").child("UserCart").child(currentUserId);
                String pushId = dbRef.push().getKey();
                CartModel cartModel=new CartModel(catagory,su,name2,num,pushId);


                dbRef.child(pushId).setValue(cartModel);



            }
        });

        holder.eli_button.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                price= sd.get(position).getPrice().toString().trim();
                num = holder.eli_button.getNumber();
                if(num=="0")
                {
                    num="1";
                }

            }
        });
        holder.eli_button.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                price= sd.get(position).getPrice().toString().trim();
                int p=Integer.parseInt(price);
                int sum=0;
                if(oldValue==0)
                {
                    sum=1*p;
                    s=String.valueOf(sum);
                    holder.product_price.setText("Price :"+s+"Tk");
                }
                else {
                    sum=newValue*p;
                    s=String.valueOf(sum);
                    holder.product_price.setText("Price :"+s+"Tk");
                }
                newValue=0;
                oldValue=0;
            }
        });

    }

    @Override
    public int getItemCount() {
        return sd.size();
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView product_price,pro_des,proNam,iviprice;
        ImageView pro_image;
        Button button,addCart,button1;
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
            iviprice=itemView.findViewById(R.id.price21);
            button1=itemView.findViewById(R.id.booking1);

        }
    }
}
