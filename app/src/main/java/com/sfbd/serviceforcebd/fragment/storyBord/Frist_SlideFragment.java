package com.sfbd.serviceforcebd.fragment.storyBord;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.sfbd.serviceforcebd.activity.SignUpActivity;
import com.sfbd.serviceforcebd.activity.StoryBord_Screen;
import com.sfbd.serviceforcebd.activity.WelcomeActivity;

public class Frist_SlideFragment extends Fragment {
    ViewPager viewPager;
    TextView next,skip,sb_signup;
    Context context;
    EditText sb_phone;
    String phone;

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

        SharedPreferences preferences=getActivity().getSharedPreferences("ppp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean("story",false);
        editor.apply();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                Third_SlideFragment tf = new Third_SlideFragment();
                phone = sb_phone.getText().toString().trim();
                if (phone.length()==0){
                    sb_phone.setError("Please Enter Your Phone Number!");
                }
                else if(phone.length()<11){
                    sb_phone.setError("Please Enter Valid Phone Number!");
                }
                else
                    {
                b.putString("u_phone",phone);
                tf.setArguments(b);
                viewPager.setCurrentItem(1);
                }
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(getContext(), SignUpActivity.class);
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