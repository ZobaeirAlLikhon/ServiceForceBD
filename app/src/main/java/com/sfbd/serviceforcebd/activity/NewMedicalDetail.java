package com.sfbd.serviceforcebd.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.databinding.ActivityNewMedicalDetailBinding;
import com.sfbd.serviceforcebd.fragment.NewMedicalFragmentOne;
import com.sfbd.serviceforcebd.fragment.NewMedicalFragmentTwo;

public class NewMedicalDetail extends AppCompatActivity {



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_medical_detail);
        String service = getIntent().getStringExtra("service");
        TextView tv = findViewById(R.id.toolmed);
        ImageView imageView = findViewById(R.id.backmed);
        tv.setText(service);
        imageView.setOnClickListener(v -> {
            onBackPressed();
        });

        if (service.equals("Serial for a Doctor"))
        {getSupportFragmentManager().beginTransaction().add(R.id.medical_frag,new NewMedicalFragmentTwo()).commit();}
        else if(service.equals("Medicine Home Delivery")){
            getSupportFragmentManager().beginTransaction().add(R.id.medical_frag,new NewMedicalFragmentOne()).commit();
        }



    }
}