package com.sfbd.serviceforcebd.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.databinding.ActivityAboutSfbdBinding;

public class About_sfbd extends AppCompatActivity {
    ActivityAboutSfbdBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAboutSfbdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}