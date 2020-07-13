package com.sfbd.serviceforcebd.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.airbnb.lottie.LottieAnimationView;
import com.sfbd.serviceforcebd.R;
import com.google.firebase.analytics.FirebaseAnalytics;

public class WelcomeActivity extends AppCompatActivity {

    private int progress;
    private LottieAnimationView lottieAnimationView;
    private FirebaseAnalytics mFirebaseAnalytics;
    private int Splash_Screen=4000;
    Animation button_amin;
    private ImageView imageView;
   // private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//       requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);


        lottieAnimationView = findViewById(R.id.servicesAnimLV);
        imageView=findViewById(R.id.imageView2);
        //textView=findViewById(R.id.textView3);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        button_amin= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        //textView.setAnimation(button_amin);
        imageView.setAnimation(button_amin);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */


                Intent mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);


                startActivity(mainIntent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();

            }
        }, Splash_Screen);
    }



}
