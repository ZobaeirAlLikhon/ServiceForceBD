package com.sfbd.serviceforcebd.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.databinding.ActivityAboutSfbdBinding;
import com.sfbd.serviceforcebd.fragment.MoreFragment;

public class About_sfbd extends AppCompatActivity {
    ActivityAboutSfbdBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAboutSfbdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backBtnPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressed();
            }
        });
    }
}