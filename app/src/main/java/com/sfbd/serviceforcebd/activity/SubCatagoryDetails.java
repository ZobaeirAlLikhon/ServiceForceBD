package com.sfbd.serviceforcebd.activity;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.adapter.CartAdapter;
import com.sfbd.serviceforcebd.adapter.SubCatagoryAdapter;
import com.sfbd.serviceforcebd.connection.ConnectionManager;
import com.sfbd.serviceforcebd.fragment.CartFragment;
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
    Fragment fragment;
    ProgressDialog progressDialog;
    ImageView toolbarCart;
    ImageView bkBt;
    TextView toolTitle,countText;
    private FirebaseAuth mAuth;
    private String curentUserId;
    DatabaseReference countref;
    private  int count;
    private String countt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_catagory_details);

        //Progress dialoge
        progressDialog=new ProgressDialog(SubCatagoryDetails.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        if(!ConnectionManager.connection(SubCatagoryDetails.this))
        {
            progressDialog.dismiss();
            new AlertDialog.Builder(SubCatagoryDetails.this)
                    .setTitle("No Internet Connection!!")
                    .setMessage("please turn on your data connection")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Whatever...
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        }
                    }).show();

            Toast.makeText(SubCatagoryDetails.this,"No internet",Toast.LENGTH_LONG).show();
        }
        countText=findViewById(R.id.countTV);
        mAuth=FirebaseAuth.getInstance();
        curentUserId=mAuth.getCurrentUser().getUid();
        int c;
        countref= FirebaseDatabase.getInstance().getReference().child("Cart").child("UserCart").child(curentUserId);
        countref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.exists())
            {
                count=(int) snapshot.getChildrenCount();
                countText.setText(Integer.toString(count));
            }
            else
                {
                    countText.setText("0");
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        textView=findViewById(R.id.noProductFound);
        toolbarCart=findViewById(R.id.toolbarIVcart);
        imageView=findViewById(R.id.noImage);
        service_n=getIntent().getStringExtra("service_name");
        catagory=getIntent().getStringExtra("category");
        recyclerView=findViewById(R.id.recyclerView1111);
        toolTitle=findViewById(R.id.toolbarTV);
        bkBt=findViewById(R.id.backBtn);
        bkBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference=FirebaseDatabase.getInstance().getReference().child("product").child(service_n).child(catagory);
        list=new ArrayList<Sd>();
       toolTitle.setText(catagory);
        toolbarCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new CartFragment();

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.add(R.id.ptoductlistV, fragment);
                ft.attach(fragment);

                ft.commit();
            }
        });

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
                progressDialog.dismiss();
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
   /* public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.toolbarIVcart) {
            getSupportActionBar().setTitle("Cart");

            fragment = new CartFragment();

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.ptoductlistV, fragment);
            ft.attach(fragment);

            ft.commit();
        }
        return super.onOptionsItemSelected(item);
    }*/


}