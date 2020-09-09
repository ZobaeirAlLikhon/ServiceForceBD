package com.sfbd.serviceforcebd.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
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

import java.time.Year;
import java.util.Calendar;
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
    DatePickerDialog.OnDateSetListener setListener;
    private String date;
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
        binding.datePro.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDiologe();
            }
        });
        toolbarInit();
        initProfileUpdate();
        saveChange();

    }

    private void toolbarInit() {
        binding.backBtnPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.toolbarTVPro.setText("Update Profile");
    }

    private void datePickerDiologe() {
        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                date=dayOfMonth+"/"+month+"/"+year;
                binding.datePro.getEditText().setText(date);
            }
        };
        Calendar calendar=Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog=new DatePickerDialog(
                ProUpdateInfo.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,setListener,year,month,day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();

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





                String name=binding.upProName.getEditText().getText().toString();
                String phone=binding.upProPhn.getEditText().getText().toString();
                String address=binding.upAddress.getEditText().getText().toString();
                String datePick=binding.datePro.getEditText().getText().toString();
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
                else if(datePick.isEmpty())
                {
                    binding.datePro.setError("Enter a Date");
                    progressDialog.dismiss();

                }
                else {
                    imageName=storageReference.child(imageUri.getLastPathSegment());
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
                                    result.put("date_Of_birth", datePick);
                                    result.put("image",String.valueOf(imageU));
                                    databaseReference.updateChildren(result);
                                    Toast.makeText(ProUpdateInfo.this,"Save Change",Toast.LENGTH_LONG).show();
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
                if(snapshot.getChildrenCount()==8) {

                    try {
                        binding.upProPhn.getEditText().setText(snapshot.child("phone").getValue().toString());
                        binding.upProName.getEditText().setText(snapshot.child("name").getValue().toString());
                        binding.upAddress.getEditText().setText(snapshot.child("address").getValue().toString());
                        binding.datePro.getEditText().setText(snapshot.child("date_Of_birth").getValue().toString());
                        Picasso.get().load(snapshot.child("image").getValue().toString()).into(binding.imageView4);
                        progressDialog.dismiss();
                    }
                    catch (Exception e)
                    {

                    }
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