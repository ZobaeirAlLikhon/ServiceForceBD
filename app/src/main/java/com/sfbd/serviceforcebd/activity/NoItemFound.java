package com.sfbd.serviceforcebd.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.sfbd.serviceforcebd.R;

public class NoItemFound extends AppCompatActivity {
    private LottieAnimationView watch_animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_item_found);
        watch_animation=findViewById(R.id.watch_animation);
        watch_animation.loop(true);
       watch_animation.setSpeed(1f);
        watch_animation.playAnimation();
    }
}