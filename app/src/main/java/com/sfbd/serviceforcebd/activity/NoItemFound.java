package com.sfbd.serviceforcebd.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.sfbd.serviceforcebd.R;

public class NoItemFound extends AppCompatActivity {
    private LottieAnimationView watch_animation;
    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_item_found);
        watch_animation=findViewById(R.id.watch_animation);
        textView=findViewById(R.id.toolbarNoPro);
        imageView=findViewById(R.id.backBtnn);
        watch_animation.loop(true);
       watch_animation.setSpeed(1f);
        watch_animation.playAnimation();
        String catagory;
        catagory=getIntent().getStringExtra("catagory");
        textView.setText(catagory);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}