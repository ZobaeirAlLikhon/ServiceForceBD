package com.sfbd.serviceforcebd.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;

import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.adapter.SerialD;
import com.sfbd.serviceforcebd.adapter.ServiceAdapter;
import com.sfbd.serviceforcebd.databinding.ActivityServicesBinding;

public class ServicesActivity extends AppCompatActivity {


    private ActivityServicesBinding binding;
    private String[] serviceList;
    private TypedArray img;
    private ServiceAdapter adapter;
    private String service;
    private int[] imgs;
    private static final String TAG = "ServicesActivity";

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
            if (service.equals("Cleaning")) {
                serviceList = getResources().getStringArray(R.array.cleaning_services);
                img = getResources().obtainTypedArray(R.array.cleaning_servicesIMG);

//                Log.d(TAG, "onCreate: serviceList "+serviceList.length);
                initRecyclerViewImg();

            }else if(service.equals("Appliance")){
                serviceList = getResources().getStringArray(R.array.appliance_repair_services);
                img = getResources().obtainTypedArray(R.array.appliance_repair_servicesIMG);

                initRecyclerViewImg();

            }else if(service.equals("Laundry")){
                serviceList = getResources().getStringArray(R.array.laundry_services);
                img = getResources().obtainTypedArray(R.array.laundry_servicesImg);

                initRecyclerViewImg();

            }else if(service.equals("Electric & Plumbing Serviecs")){
                serviceList = getResources().getStringArray(R.array.plumbing_services);
                initRecyclerView();

            }else if(service.equals("Painting & Renovation Serviecs")){
                serviceList = getResources().getStringArray(R.array.painting_renovation_services);
                initRecyclerView();

            }else if(service.equals("Car & Bike Care")){
                serviceList = getResources().getStringArray(R.array.car_care_services);
                img = getResources().obtainTypedArray(R.array.car_care_servicesImg);

                initRecyclerViewImg();

            }else if(service.equals("Electronics & Gadgets")){
                serviceList = getResources().getStringArray(R.array.electronic_gadgets);
                initRecyclerView();

            }else if(service.equals("Beauty Services")){
                serviceList = getResources().getStringArray(R.array.beauty_services);
                initRecyclerView();

            }else if(service.equals("Shifting")){
                serviceList = getResources().getStringArray(R.array.shifting_services);
                initRecyclerView();

            }else if(service.equals("Medical")){
                serviceList = getResources().getStringArray(R.array.medical_services);
                initRecyclerView();

            }else if(service.equals("Event Management")){
                serviceList = getResources().getStringArray(R.array.event_management);
                initRecyclerView();

            }else if(service.equals("IT Support")){
                serviceList = getResources().getStringArray(R.array.it_support);
                initRecyclerView();

            }else if(service.equals("Market Service")){
                serviceList = getResources().getStringArray(R.array.market_service);
                initRecyclerView();

            }else if(service.equals("Tuition Service")){
                serviceList = getResources().getStringArray(R.array.tuition_service);
                initRecyclerView();

            }
            //todo modified code to generate subcatagory...
            else if (service.equals("Birthday")){
                serviceList = getResources().getStringArray(R.array.Birthday);
                initRecyclerView();
            }
            else if (service.equals("Marriage Day")){
                serviceList = getResources().getStringArray(R.array.Marriage_Day);
                initRecyclerView();
            }
            else if (service.equals("Wedding Management")){
                serviceList = getResources().getStringArray(R.array.Wedding_Management);
                initRecyclerView();
            }
            else if (service.equals("Social Function")){
                serviceList = getResources().getStringArray(R.array.Social_Function);
                initRecyclerView();
            }
            else if ((service.equals("Get Together"))){
                serviceList = getResources().getStringArray(R.array.Get_together);
                initRecyclerView();
            }
            else if (service.equals("Photography")){
                serviceList = getResources().getStringArray(R.array.Photography);
                initRecyclerView();
            }
            else if (service.equals("Interior Decoration")){
                serviceList = getResources().getStringArray(R.array.Interior_Decoration);
                initRecyclerView();
            }
            else if (service.equals("Painting")){
                serviceList = getResources().getStringArray(R.array.Painting);
                initRecyclerView();
            }
            //todo modified code to generate subcatagory.to MediCAL
//            else if (service.equals("Serial for a Doctor")){
//                serviceList=getResources().getStringArray(R.array.serial_for_a_Doctor);
//                initRecyclerView();
//            }



        }


    }

    private void initRecyclerView() {
            adapter = new ServiceAdapter(this, serviceList,service);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
            binding.recyclerView.setAdapter(adapter);
    }
    private void initRecyclerViewImg() {
        adapter = new ServiceAdapter(this, serviceList,service,img);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        }

    //method to populate event managment sub catagory
}
