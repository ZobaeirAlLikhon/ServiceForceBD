package com.sfbd.serviceforcebd.fragment;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.sfbd.serviceforcebd.BuildConfig;
import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.activity.Goru;
import com.sfbd.serviceforcebd.activity.ServicesActivity;
import com.sfbd.serviceforcebd.activity.Silk;
import com.sfbd.serviceforcebd.adapter.SearchAdapter;
import com.sfbd.serviceforcebd.databinding.FragmentHomeBinding;
import com.sfbd.serviceforcebd.model.Sd;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.ImageListener;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventListener;
import java.util.List;
import java.util.Queue;


import static com.facebook.FacebookSdk.getApplicationContext;
/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    int[] sampleImages = {R.drawable.new_car,R.drawable.new_it,R.drawable.baner_medi,R.drawable.baner_shifting,
    R.drawable.baner_clean,R.drawable.baner_visiting};
    private Context context;
    private Button Call_btn;
    private static final int REQUEST_CALL=1;
    DatabaseReference dbre,dbre1;
    private List<String> name=new ArrayList<>();
    private String p;

    SearchAdapter adapter;
    private int RECQUST=11;
    String sLetestVersion,sCurrentVersion;

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


        dbre1= FirebaseDatabase.getInstance().getReference().child("product");
        name=Arrays.asList(getResources().getStringArray(R.array.search_service));

        binding.mainRecy.setLayoutManager(new LinearLayoutManager(context));
//        name = getResources().getStringArray(R.array.cleaning_services);
        adapter=new SearchAdapter(context,name);
        binding.mainRecy.setAdapter(adapter);
        new GetLatestVersion().execute();

        imageBanner();
        search();
        initView();
        init();
        findViewById();
        popularItem();
        return binding.getRoot();
    }

    private void popularItem() {
        binding.carBikePV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String service = "Car & Bike Care";
                initService(service);
            }
        });binding.beutyPV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String service = "Beauty Services";
                initService(service);

            }
        });binding.itPV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String service = "IT Support";
                initService(service);
            }
        });binding.cleaningPV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String service = "Cleaning";
                initService(service);
            }
        });binding.mediPV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String service = "Medical";
                initService(service);
            }
        });binding.homePV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Service not avalible",Toast.LENGTH_SHORT).show();
            }
        });binding.shiftingPV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String service = "Shifting";
                initService(service);

            }
        });binding.repairPV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String service = "Appliance";
                initService(service);
            }
        });
    }

    private void imageBanner() {
        dbre= FirebaseDatabase.getInstance().getReference().child("dashboard_banner").child("middle");

        dbre.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {


                    p=snapshot.child("image").getValue(String.class);


                    try {
//                        Picasso.get().load(p).into(binding.bannerMiddle);
//                        Picasso.get().load(p).into(binding.bannerMiddle);
                        Glide.with(context).load(p).into(binding.bannerMiddle);
                    }catch (Exception e){
                        e.printStackTrace();
                    }





                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void search() {
        binding.searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.consLayMain.setVisibility(View.INVISIBLE);
                binding.recyRela.setVisibility(View.VISIBLE);
                searchConfirm(s.toString());
                if(start==0&& count==0)
                {
                    binding.consLayMain.setVisibility(View.VISIBLE);
                    binding.recyRela.setVisibility(View.GONE);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.searchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                binding.consLayMain.setVisibility(View.VISIBLE);
                binding.recyRela.setVisibility(View.GONE);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                binding.consLayMain.setVisibility(View.INVISIBLE);
                binding.recyRela.setVisibility(View.VISIBLE);
                searchConfirm(text.toString());


                Toast.makeText(context,text,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onButtonClicked(int buttonCode) {


            }
        });


    }

    private void searchConfirm(String s) {
        String text=s.toLowerCase();
        List<String> newL=new ArrayList<>();
        for(String n:name)
        {
            if(n.toLowerCase().contains(text))
            {
                newL.add(n);
            }
        }
        adapter.updateList(newL);

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
            String service = "Car & Bike Care";
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

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setCropToPadding(false);
            imageView.setImageResource(sampleImages[position]);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }


    private class GetLatestVersion extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                sLetestVersion= Jsoup
                        .connect("https://play.google.com/store/apps/details?id="
                        + context.getPackageName())
                        .timeout(3000)
                        .get()
                        .select("div.hAyfc:nth-child(4)>"+
                                "span:nth-child(2)> div:nth-child(1)"+
                                ">span:nth-child(1)")
                        .first()
                        .ownText();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sLetestVersion;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            sCurrentVersion= BuildConfig.VERSION_NAME;
            if(sLetestVersion!=null)
            {
                float cVersion=Float.parseFloat(sCurrentVersion);
                float lVersion=Float.parseFloat(sLetestVersion);
                if(lVersion>cVersion)
                {
                    UpdateAppsAlart(lVersion);
                }
            }
        }
    }

    private void UpdateAppsAlart(float lV) {
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Update Avalible");
        builder.setMessage("Update Version is: "+lV);
        builder.setCancelable(false);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(
                        "market://details?id="+context.getPackageName()
                )
                ));
                dialog.dismiss();
            }
        });
        builder.show();

    }
}
