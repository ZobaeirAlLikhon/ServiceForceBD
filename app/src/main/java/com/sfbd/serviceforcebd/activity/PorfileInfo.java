package com.sfbd.serviceforcebd.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.databinding.ActivityPorfileInfoBinding;
import com.squareup.picasso.Picasso;

public class PorfileInfo extends AppCompatActivity {
    private ActivityPorfileInfoBinding binding;
    private DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPorfileInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid());
        storageReference= FirebaseStorage.getInstance().getReference().child("Pro");
        initInfo();
        editPro();
    }

    private void editPro() {
        binding.editBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PorfileInfo.this,ProUpdateInfo.class);
                startActivity(intent);
            }
        });
    }

    private void initInfo() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("name").exists())
                {
                    binding.proTV1.setText(snapshot.child("name").getValue().toString());
                }
                if(snapshot.child("image").exists())
                {
                    Glide.with(getApplicationContext()).load(snapshot.child("image").getValue().toString()).into(binding.proIV1);
                }
                if(snapshot.child("email").exists())
                {
                    binding.proEmailTV.setText("Email: "+snapshot.child("email").getValue().toString());
                }
                if(snapshot.child("address").exists())
                {
                    binding.proAddressTV.setText("Address: "+snapshot.child("address").getValue().toString());
                }
                if(snapshot.child("date_Of_birth").exists())
                {
                    binding.proDateOfBirthTV.setText("Date Of Birth: "+snapshot.child("date_Of_birth").getValue().toString());
                }
                if(snapshot.child("phone").exists())
                {
                    binding.proPhnTV.setText("Phone: "+snapshot.child("phone").getValue().toString());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}