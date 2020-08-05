package com.sfbd.serviceforcebd.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.adapter.SubCatagoryAdapter;
import com.sfbd.serviceforcebd.model.Sd;

import java.util.ArrayList;

public class SubCatagoryDetails extends AppCompatActivity {
    String service_n,catagory;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<Sd> list;
    SubCatagoryAdapter adapter;
    TextView textView;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_catagory_details);


        textView=findViewById(R.id.noProductFound);
        imageView=findViewById(R.id.noImage);
        service_n=getIntent().getStringExtra("service_name");
        catagory=getIntent().getStringExtra("category");
        getSupportActionBar().setTitle(catagory);
        recyclerView=findViewById(R.id.recyclerView1111);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference=FirebaseDatabase.getInstance().getReference().child("product").child(service_n).child(catagory);
        list=new ArrayList<Sd>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    Sd ss=dataSnapshot1.getValue(Sd.class);
                    list.add(ss);
                }
                adapter=new SubCatagoryAdapter(SubCatagoryDetails.this,list,catagory);
                recyclerView.setAdapter(adapter);
                if(!dataSnapshot.exists())
                {
                    imageView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                    Toast.makeText(SubCatagoryDetails.this,"No Product Found",Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SubCatagoryDetails.this,"No Product Found",Toast.LENGTH_LONG).show();

            }
        });

    }


}