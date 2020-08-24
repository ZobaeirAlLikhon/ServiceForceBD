package com.sfbd.serviceforcebd.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.databinding.ActivityAddressBinding;
import com.sfbd.serviceforcebd.databinding.ActivityProUpdateInfoBinding;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class ProUpdateInfo extends AppCompatActivity {
    private static final int PIC_IMAGE_REQ=1;
    private ActivityProUpdateInfoBinding binding;
    private Context context;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    StorageReference imageName;
    private FirebaseUser firebaseUser;
    private Fragment fragment;
    private Uri imageUri,imageU;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProUpdateInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        progressDialog=new ProgressDialog(ProUpdateInfo.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid());
        storageReference= FirebaseStorage.getInstance().getReference().child("Pro");
        binding.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,PIC_IMAGE_REQ);
            }
        });

        initProfileUpdate();
        saveChange();

    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PIC_IMAGE_REQ && resultCode==RESULT_OK &&data!=null && data.getData()!=null)
        {
            imageUri=data.getData();

            Picasso.get().load(imageUri).into(binding.imageView4);
        }

    }

    private void saveChange() {
        binding.saveCng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog=new ProgressDialog(ProUpdateInfo.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                imageName=storageReference.child(imageUri.getLastPathSegment());




                String name=binding.upProName.getEditText().getText().toString();
                String phone=binding.upProPhn.getEditText().getText().toString();
                String address=binding.upAddress.getEditText().getText().toString();
                if (imageUri==null)
                {
                    Toast.makeText(ProUpdateInfo.this,"Select Image",Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
                else if(name.isEmpty())
                {
                    binding.upProName.setError("Enter Your Name");
                    progressDialog.dismiss();
                }
                else if(phone.isEmpty())
                {
                    binding.upProPhn.setError("Enter Your Name");
                    progressDialog.dismiss();
                }
                else if(address.isEmpty())
                {
                    binding.upAddress.setError("Enter Your Name");
                    progressDialog.dismiss();
                }
                else {
                    imageName.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(ProUpdateInfo.this,"uploaded",Toast.LENGTH_LONG).show();

                            imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imageU=uri;
                                    HashMap<String, Object> result = new HashMap<>();
                                    result.put("name", name);
                                    result.put("phone", phone);
                                    result.put("address", address);
                                    result.put("image",String.valueOf(imageU));
                                    databaseReference.updateChildren(result);
                                    progressDialog.dismiss();
                                }
                            });

                        }
                    });

                }

            }
        });
    }

    private void initProfileUpdate() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount()==7) {
                    binding.upProPhn.getEditText().setText(snapshot.child("phone").getValue().toString());
                    binding.upProName.getEditText().setText(snapshot.child("name").getValue().toString());
                    binding.upAddress.getEditText().setText(snapshot.child("address").getValue().toString());
                    Picasso.get().load(snapshot.child("image").getValue().toString()).into(binding.imageView4);
                    progressDialog.dismiss();
                }
                else{
                    binding.upProPhn.getEditText().setText(snapshot.child("phone").getValue().toString());
                    binding.upProName.getEditText().setText(snapshot.child("name").getValue().toString());
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
}