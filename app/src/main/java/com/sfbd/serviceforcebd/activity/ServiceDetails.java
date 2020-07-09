package com.sfbd.serviceforcebd.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.TypedArray;
import android.os.Bundle;

import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.databinding.ActivityServiceDetailsBinding;
import com.sfbd.serviceforcebd.databinding.ActivityServicesBinding;

public class ServiceDetails extends AppCompatActivity {
    private ActivityServiceDetailsBinding binding;
    private String category;
    private int imag;
    private int[] imgs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityServiceDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        TypedArray imgs = getResources().obtainTypedArray(R.array.random_imgs);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            category = bundle.getString("category");
            imag=bundle.getInt("img_p");
            imgs.getResourceId(imag, -1);
            binding.textView5.setText(category);
            binding.imageView3.setImageResource(imgs.getResourceId(imag, -1));
        }
    }
}