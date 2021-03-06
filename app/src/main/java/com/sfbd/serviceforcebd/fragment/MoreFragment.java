package com.sfbd.serviceforcebd.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.activity.About_sfbd;
import com.sfbd.serviceforcebd.activity.AddressActivity;
import com.sfbd.serviceforcebd.activity.PorfileInfo;
import com.sfbd.serviceforcebd.activity.ProUpdateInfo;
import com.sfbd.serviceforcebd.activity.SignInActivity;
import com.sfbd.serviceforcebd.activity.Silk;
import com.sfbd.serviceforcebd.databinding.FragmentMoreBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {

    private FragmentMoreBinding binding;
    private Context context;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference,dbref;
    private FirebaseUser firebaseUser;
    Fragment fragment;
    ProgressDialog progressDialog;
    private ReviewManager manager;

    public MoreFragment() {
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
        binding = FragmentMoreBinding.inflate(inflater,container,false);
        progressDialog=new ProgressDialog(context);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        binding.editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PorfileInfo.class);
                context.startActivity(intent);
            }
        });
        binding.feedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(),"FeedBack",Toast.LENGTH_SHORT).show();
               askRatting();
            }
        });
        binding.about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, About_sfbd.class);
                startActivity(intent);
            }
        });
        initLogout();
        initFireBase();
        SharedPreferences preferences=this.getActivity().getSharedPreferences("prefs",Context.MODE_PRIVATE);
        boolean fristTime=preferences.getBoolean("fristTime",false);
        if(fristTime)
        {
//            rattingApps();
        }

        return binding.getRoot();
    }

    private void askRatting() {
        manager = ReviewManagerFactory.create(context);
        Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // We can get the ReviewInfo object
                ReviewInfo reviewInfo = task.getResult();
                Task<Void> flow = manager.launchReviewFlow((Activity) context, reviewInfo);
                flow.addOnCompleteListener(task2 -> {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
                });
            } else {
                // There was some problem, continue regardless of the result.
            }
        });
    }


    private void rattingApps() {
        RatingFragment ratingFragment=new RatingFragment();
        ratingFragment.show(getFragmentManager(),"ex");

    }

    private void initFireBase() {

        String userId;

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null){
            userId = currentUser.getUid();
            databaseReference= FirebaseDatabase.getInstance().getReference().child("users").child(userId);
            binding.nameTV.setText(currentUser.getDisplayName());
            binding.phoneTV.setText(currentUser.getEmail());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child("image").exists())
                    {
                        try {
//                            Picasso.get().load(snapshot.child("image").getValue().toString()).into(binding.profileIV);
                            Glide.with(context).load(snapshot.child("image").getValue().toString()).into(binding.profileIV);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }

                    progressDialog.dismiss();
                    }
                    progressDialog.dismiss();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }



    }

    private void initLogout() {

        binding.logoutCL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(context, SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}
