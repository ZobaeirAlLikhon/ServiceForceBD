package com.sfbd.serviceforcebd.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.model.CartModel;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolders> {
    Context context;
    ArrayList<CartModel> list;
    DatabaseReference dref;
   String TAG="Cart";

    public CartAdapter(Context context, ArrayList<CartModel> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolders(LayoutInflater.from(context).inflate(R.layout.cart_list,parent,false));
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolders holder, int position) {

        holder.cartProName.setText("Name: "+list.get(position).getProductName());
        holder.cartPrice1.setText("Price: "+list.get(position).getProductPrice());
        holder.cartQuantity.setText("Quantity: "+list.get(position).getNoOfProduct());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolders extends RecyclerView.ViewHolder {
        Context context;

        TextView cartProName,cartPrice1,cartQuantity,cartId;
        public MyViewHolders(@NonNull View itemView) {
            super(itemView);
            cartProName=itemView.findViewById(R.id.cartProName);
            cartPrice1=itemView.findViewById(R.id.cartPrice);
            cartQuantity=itemView.findViewById(R.id.cartQuanity);
            cartId=itemView.findViewById(R.id.cartId);
        }

    }
    public void deleteItem(int position)
    {
        list.remove(position);
        notifyItemRemoved(position);

    }
}
