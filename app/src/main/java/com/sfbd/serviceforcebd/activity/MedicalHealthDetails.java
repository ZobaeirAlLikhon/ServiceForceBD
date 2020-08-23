package com.sfbd.serviceforcebd.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.adapter.MedicalAdapter;
import com.sfbd.serviceforcebd.connection.ConnectionManager;
import com.sfbd.serviceforcebd.model.MedicalModel;
import com.sfbd.serviceforcebd.model.Sd;

import java.util.ArrayList;

public class MedicalHealthDetails extends AppCompatActivity {
    private TextView toolTitle;
    private ImageView toolBackBT;
    private String service_n,catagory;
    RecyclerView mediRecyclerView;
    ArrayList<MedicalModel> mList;
     MedicalAdapter adapter;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_health_details);
        progressDialog=new ProgressDialog(MedicalHealthDetails.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        checkInternetConnection();

        toolTitle=findViewById(R.id.MtoolbarTV);
        toolBackBT=findViewById(R.id.MbackBtn);
        mediRecyclerView=findViewById(R.id.MrecyclerView1111);
        service_n=getIntent().getStringExtra("service_name");
        catagory=getIntent().getStringExtra("category");
        mediRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference= FirebaseDatabase.getInstance().getReference().child("product").child("Medical").child(catagory);
        mList=new ArrayList<MedicalModel>();

        recyclerViewInit();
        toolBarInit();

    }



    private void recyclerViewInit() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists())
                {
                    Intent intent=new Intent(MedicalHealthDetails.this,NoItemFound.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                        MedicalModel ss = dataSnapshot1.getValue(MedicalModel.class);
                        mList.add(ss);
                    }
                    adapter = new MedicalAdapter(MedicalHealthDetails.this, mList, catagory);
                    mediRecyclerView.setAdapter(adapter);
                    progressDialog.dismiss();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void toolBarInit() {
        toolTitle.setText(catagory);
        toolBackBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void checkInternetConnection() {
        if(!ConnectionManager.connection(MedicalHealthDetails.this))
        {
            progressDialog.dismiss();
            new AlertDialog.Builder(MedicalHealthDetails.this)
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

            Toast.makeText(MedicalHealthDetails.this,"No internet",Toast.LENGTH_LONG).show();
        }
    }
}