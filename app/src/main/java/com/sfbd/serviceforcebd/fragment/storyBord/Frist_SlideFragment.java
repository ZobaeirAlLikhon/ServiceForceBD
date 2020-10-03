package com.sfbd.serviceforcebd.fragment.storyBord;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.UserProfileChangeRequest;
import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.activity.MainActivity;
import com.sfbd.serviceforcebd.activity.SignInActivity;
import com.sfbd.serviceforcebd.activity.StoryBord_Screen;
import com.sfbd.serviceforcebd.activity.WelcomeActivity;

public class Frist_SlideFragment extends Fragment {
    ViewPager viewPager;
    TextView next,skip,sb_signup;
    Context context;
    EditText sb_phone;

    public Frist_SlideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_frist__slide, container, false);
        viewPager=getActivity().findViewById(R.id.viewPager);
        next=view.findViewById(R.id.sb_next);
        skip=view.findViewById(R.id.sb_skip);
        sb_phone = view.findViewById(R.id.sb_phone);
        sb_signup = view.findViewById(R.id.sb_signup);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                String phone = sb_phone.getText().toString();
                if (phone.length()==0){
                    sb_phone.setError("Please Enter Your Phone Number!");
                }
                else if(phone.length()<11){
                    sb_phone.setError("Please Enter Valid Phone Number!");
                }
                else
                    {
                b.putString("phone",phone);
                viewPager.setCurrentItem(1);
                }
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(getContext(),SignInActivity.class);
                startActivity(mainIntent);


            }
        });
        sb_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SignInActivity.class));
            }
        });
        return view;
    }
}