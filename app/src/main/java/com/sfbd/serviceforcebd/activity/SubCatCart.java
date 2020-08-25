package com.sfbd.serviceforcebd.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.fragment.CartFragment;

public class SubCatCart extends AppCompatActivity {
    Fragment fragment;
    ImageView imBk;
    TextView toolTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_cat_cart);
        imBk=findViewById(R.id.backBtncart);
        toolTitle=findViewById(R.id.toolbarTVCart);
        toolBarInit();
        fragment = new CartFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.cartFrameLayout, fragment);
        ft.attach(fragment);
        ft.commit();
    }

    private void toolBarInit() {
        imBk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolTitle.setText("Your Cart");
    }
}