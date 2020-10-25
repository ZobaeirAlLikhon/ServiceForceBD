package com.sfbd.serviceforcebd.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.activity.AddressActivity;
import com.sfbd.serviceforcebd.activity.MediAddressActivity;
import com.sfbd.serviceforcebd.model.MedicalModel;
import com.sfbd.serviceforcebd.model.Sd;

import java.util.ArrayList;

public class MedicalAdapter extends RecyclerView.Adapter<MedicalAdapter.MediViewHolder> {
    Context context;
    ArrayList<MedicalModel> list;
    String cata;

    public MedicalAdapter(Context context, ArrayList<MedicalModel> list, String cata) {
        this.context = context;
        this.list = list;
        this.cata = cata;
    }

    @NonNull
    @Override
    public MedicalAdapter.MediViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new MedicalAdapter.MediViewHolder(LayoutInflater.from(context).inflate(R.layout.medical_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MedicalAdapter.MediViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName().toString().trim());
        holder.des.setText(list.get(position).getDes().toString().trim());
        holder.price.setText("Visit: "+list.get(position).getPrice().toString().trim()+" TK");
        holder.bookBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1=list.get(position).getName().toString().trim();
                String priceM=list.get(position).getPrice().toString().trim();
                Intent intent=new Intent(context, MediAddressActivity.class);
                intent.putExtra("category",cata);
                intent.putExtra("proName",name1);
                intent.putExtra("price",priceM);
                intent.putExtra("noOfItem","Doctor");
                context.startActivity(intent);
            }
        });
        holder.callBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:01707071417"));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MediViewHolder extends  RecyclerView.ViewHolder {
        TextView name,des,price;
        Button callBTN,bookBTN;
        public MediViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.medical_Name);
            des=itemView.findViewById(R.id.medical_Dec);
            price=itemView.findViewById(R.id.MediPrice);
            callBTN=itemView.findViewById(R.id.callBTn);
            bookBTN=itemView.findViewById(R.id.medical_V);

        }
    }
}
