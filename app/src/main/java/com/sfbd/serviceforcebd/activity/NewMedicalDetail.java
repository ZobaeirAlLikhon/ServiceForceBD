package com.sfbd.serviceforcebd.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.fragment.NewMedicalFragmentOne;
import com.sfbd.serviceforcebd.fragment.NewMedicalFragmentTwo;

public class NewMedicalDetail extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_medical_detail);
        String service = getIntent().getStringExtra("service");
        if (service.equals("Serial for a Doctor"))
        {getSupportFragmentManager().beginTransaction().add(R.id.medical_frag,new NewMedicalFragmentTwo()).commit();}
        else if(service.equals("Medicine Home Delivery")){
            getSupportFragmentManager().beginTransaction().add(R.id.medical_frag,new NewMedicalFragmentOne()).commit();
        }



    }
}