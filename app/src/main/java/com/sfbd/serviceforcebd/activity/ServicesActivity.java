package com.sfbd.serviceforcebd.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.res.TypedArray;
import android.os.Bundle;

import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.adapter.ServiceAdapter;
import com.sfbd.serviceforcebd.databinding.ActivityServicesBinding;

public class ServicesActivity extends AppCompatActivity {


    private ActivityServicesBinding binding;
    private String[] serviceList;
    private ServiceAdapter adapter;
    private String service;
    private int[] imgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityServicesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backBtn.setOnClickListener(view -> {
            onBackPressed();
        });



        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            service = bundle.getString("service");
            binding.toolbarTV.setText(service);

            assert service != null;
            if (service.equals("Cleaning")){
                serviceList = getResources().getStringArray(R.array.cleaning_services);
                initRecyclerView();
            }else if(service.equals("Beauty Services")){
                serviceList = getResources().getStringArray(R.array.beauty_services);
                initRecyclerView();

            }else if(service.equals("Shifting")){
                serviceList = getResources().getStringArray(R.array.shifting_services);
                initRecyclerView();

            }else if(service.equals("Medical")){
                 imgs = getResources().getIntArray(R.array.random_imgs);
                serviceList = getResources().getStringArray(R.array.medical_services);
                initRecyclerView();

            }else if(service.equals("Rent-A-Car")){
                serviceList = getResources().getStringArray(R.array.rent_a_car_services);
                initRecyclerView();

            }else if(service.equals("Event Management")){
                serviceList = getResources().getStringArray(R.array.event_management);
                initRecyclerView();

            }else if(service.equals("IT Support")){
                serviceList = getResources().getStringArray(R.array.it_support);
                initRecyclerView();

            }else if(service.equals("Home Delivery")){
                serviceList = getResources().getStringArray(R.array.home_delivery);
                initRecyclerView();

            }else if(service.equals("Tuition Service")){
                serviceList = getResources().getStringArray(R.array.tuition_service);
                initRecyclerView();

            }
        }


    }

    private void initRecyclerView() {
            adapter = new ServiceAdapter(this, serviceList);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
            binding.recyclerView.setAdapter(adapter);
    }
}
