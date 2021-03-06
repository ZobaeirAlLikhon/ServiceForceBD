package com.sfbd.serviceforcebd.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.sfbd.serviceforcebd.R;
import com.google.firebase.analytics.FirebaseAnalytics;

public class WelcomeActivity extends AppCompatActivity  {

    private int progress;
//    private LottieAnimationView lottieAnimationView;
    private FirebaseAnalytics mFirebaseAnalytics;
    private int Splash_Screen=2000;
    Animation button_amin;
    private ImageView imageView;
    private int RECQUST=11;
   // private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
//       requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



//        lottieAnimationView = findViewById(R.id.servicesAnimLV);
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

                SharedPreferences preferences=getSharedPreferences("ppp",Context.MODE_PRIVATE);
                boolean fristTime=preferences.getBoolean("story",true);
                if(fristTime)
                {
                    Intent mainIntent = new Intent(WelcomeActivity.this, StoryBord_Screen.class);
                    startActivity(mainIntent);

                }else {
                    Intent mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                }

                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();

            }
        }, Splash_Screen);


    }


}
