package com.sfbd.serviceforcebd.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.sfbd.serviceforcebd.R;

public class NewMedicalFragmentOne extends Fragment {

    private MaterialButton sendbtn,callbtn;
    private TextView camerabtn;
    private TextInputLayout textInputLayout;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    public NewMedicalFragmentOne(){

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_medical_frag_one,container,false);

        //initialize button with id
        sendbtn = view.findViewById(R.id.sendbtnmed);
        callbtn = view.findViewById(R.id.callbtnmed);

        //camera text initialization
        camerabtn = view.findViewById(R.id.camera_btn);

        //Material TextInputlayout initialization
        textInputLayout = view.findViewById(R.id.medbox);

        //initialize image view
        imageView = view.findViewById(R.id.imgpres);

        callbtn.setOnClickListener(v ->{
                    Intent intent =new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:01707071417"));
                    getContext().startActivity(intent);
        });
        sendbtn.setOnClickListener(v ->{
            String medname = textInputLayout.getEditText().getText().toString();
            Toast.makeText(getContext(),medname,Toast.LENGTH_SHORT).show();
            ///here goes firebasedatbase push code
        });

        camerabtn.setOnClickListener(v ->{
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        });
      //database push goes here


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }
}
