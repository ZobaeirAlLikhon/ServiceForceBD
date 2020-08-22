package com.sfbd.serviceforcebd.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.activity.Goru;
import com.sfbd.serviceforcebd.activity.ServicesActivity;
import com.sfbd.serviceforcebd.activity.Silk;
import com.sfbd.serviceforcebd.databinding.FragmentHomeBinding;
import com.synnapps.carouselview.ImageListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private int[] sampleImages = {R.drawable.imagefive,
            R.drawable.a1,R.drawable.a22,R.drawable.a23,R.drawable.a24, R.drawable.a21,};
    private Context context;
    private Button Call_btn;
    private static final int REQUEST_CALL=1;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        initView();
        init();
        findViewById();
        return binding.getRoot();
    }

    private void findViewById() {
        binding.callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:01707071417"));
                context.startActivity(intent);
            }
        });
    }


    private void initView() {

        binding.cleaningCV.setOnClickListener(view -> {
            String service = "Cleaning";
            initService(service);
        });
        binding.applianceCV.setOnClickListener(v -> {
            String service = "Appliance";
            initService(service);
        });

        binding.beautyServiceCV.setOnClickListener(view -> {
            String service = "Beauty Services";
            initService(service);
        });
        binding.laundryCV.setOnClickListener(view-> {
            String service = "Laundry";
            initService(service);
        });

        binding.shiftingCV.setOnClickListener(view -> {
            String service = "Shifting";
            initService(service);
        });
        binding.plumbingServiceCV.setOnClickListener(v -> {
            String service = "Electric & Plumbing Serviecs";
            initService(service);
        });
        binding.paintingRenovationServiceCV.setOnClickListener(v -> {
            String service = "Painting & Renovation Serviecs";
            initService(service);
        });

        binding.carcareCV.setOnClickListener(v -> {
            String service = "Care & Bike Care";
            initService(service);
        });

        binding.electronicsgadgetsCV.setOnClickListener(v -> {
            String service = "Electronics & Gadgets";
            initService(service);
        });

        binding.medicalServiceCV.setOnClickListener(view -> {
            String service = "Medical";
            initService(service);
        });

        binding.eventManagementCV.setOnClickListener(view -> {
            String service = "Event Management";
            initService(service);
        });

        binding.itSupportCV.setOnClickListener(view -> {
            String service = "IT Support";
            initService(service);
        });

        binding.marketCV.setOnClickListener(view -> {
            String service = "Market Service";
            initService(service);
        });

        binding.tuitionManagementCV.setOnClickListener(view -> {
            String service = "Tuition Service";
            initService(service);
        });
        binding.goruProjectCV.setOnClickListener(view ->{
//            String service ="Goru";
//            initService(service);
//            Intent intent = new Intent();
//            intent.setAction(Intent.ACTION_VIEW);
//            intent.addCategory(Intent.CATEGORY_BROWSABLE);
//            intent.setData(Uri.parse("http://www.kurbani.serviceforcebd.com/product-category/cattle-service/"));
//            startActivity(intent);
            Intent intent = new Intent(getActivity(), Goru.class);
            context.startActivity(intent);
        });
       // binding.silkProjectCV.setOnClickListener(view ->{

//            Intent intent = new Intent();
//            intent.setAction(Intent.ACTION_VIEW);
//           intent.addCategory(Intent.CATEGORY_BROWSABLE);
//           intent.setData(Uri.parse("http://serviceforcebd.com/product-category/clothing/silk-sharee/"));
//           startActivity(intent);
//            Intent intent = new Intent(getActivity(), Silk.class);
//            context.startActivity(intent);
//        });

    }

    private void init() {
        binding.carouselView.setPageCount(sampleImages.length);
        binding.carouselView.setImageListener(imageListener);


    }

    private void initService(String service) {
        Intent intent = new Intent(context, ServicesActivity.class);
        intent.putExtra("service",service);
        startActivity(intent);

    }

    private ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setCropToPadding(true);
            imageView.setImageResource(sampleImages[position]);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
