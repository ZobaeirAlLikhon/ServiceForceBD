package com.sfbd.serviceforcebd.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.activity.AddressActivity;
import com.sfbd.serviceforcebd.activity.LocationActivity;
import com.sfbd.serviceforcebd.activity.MedicalHealthDetails;
import com.sfbd.serviceforcebd.activity.ServiceDetails;
import com.sfbd.serviceforcebd.activity.Silk;
import com.sfbd.serviceforcebd.activity.SubCatagoryDetails;
import com.sfbd.serviceforcebd.databinding.RvServiceItemBinding;

import static android.widget.Toast.LENGTH_LONG;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    private Context context;
    private String[] serviceList;
    private String service,count;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    DatabaseReference dbre;



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
        dbre= FirebaseDatabase.getInstance().getReference().child("Likes").child(serviceList[position]);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        String userId = currentUser.getUid();
        holder.binding.likeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p=position;
                try{
                if (holder.binding.likeIV.getTag().equals("Disikes"))
                {
                    Toast.makeText(context,"Like", LENGTH_LONG).show();
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(serviceList[p]).child(userId).setValue(true);

                }
                else {
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(serviceList[p]).child(userId).removeValue();

                }
                }catch (Exception e)
                {

                }

            }
        });
        dbre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count= String.valueOf(snapshot.getChildrenCount());
                holder.binding.countLike.setText(count);
                if(snapshot.child(userId).exists())
                {
                    try{
                        holder.binding.likeIV.setImageResource(R.drawable.like_foreground);
                        holder.binding.likeIV.setTag("Likes");
                    }catch(Exception e){

                        }


                }
                else {
                    try{
                        holder.binding.likeIV.setImageResource(R.drawable.dislike_foreground);
                        holder.binding.likeIV.setTag("Disikes");
                    }
                    catch (Exception e)
                    {

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        holder.itemView.setOnClickListener(view -> {


            if (serviceList[position].equals("Get a Tuition"))
            {

                Intent intent =new Intent(context, Silk.class);
                context.startActivity(intent);

            }
            else if(serviceList[position].equals("Consult with a Doctor")||serviceList[position].equals("Psychiatrist/mental health")
            ||serviceList[position].equals("Pathology test")||serviceList[position].equals("Serial for a Doctor")
            ||serviceList[position].equals("Medicine Home Delivery")||serviceList[position].equals("Emergency Service"))
            {
                Intent intent =new Intent(context, MedicalHealthDetails.class);
                intent.putExtra("service_name",service);
                intent.putExtra("category",serviceList[position]);
                context.startActivity(intent);
            }
            else {
            Intent intent = new Intent(context, SubCatagoryDetails.class);
            intent.putExtra("service_name",service);
            intent.putExtra("category",serviceList[position]);


            context.startActivity(intent);}
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
