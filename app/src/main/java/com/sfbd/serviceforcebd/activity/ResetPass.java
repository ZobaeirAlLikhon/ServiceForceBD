package com.sfbd.serviceforcebd.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.databinding.ActivityResetPassBinding;
import com.sfbd.serviceforcebd.databinding.ActivitySignInBinding;

public class ResetPass extends AppCompatActivity {
    private ActivityResetPassBinding binding;
    private FirebaseAuth mAuth;
    private LottieAnimationView lottieAnimationView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth=FirebaseAuth.getInstance();
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=binding.editEmail.getText().toString();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(ResetPass.this,"Enter your Email Id",Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(ResetPass.this,"Please Check your Email",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ResetPass.this,SignInActivity.class));
                            }

                        }
                    });
                }
            }
        });
    }


}