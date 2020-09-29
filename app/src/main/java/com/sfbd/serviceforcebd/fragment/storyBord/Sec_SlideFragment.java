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
import android.widget.TextView;

import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.activity.MainActivity;


public class Sec_SlideFragment extends Fragment {
    ViewPager viewPager;
    TextView finish,skip;
    Context context;

    public Sec_SlideFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sec__slide, container, false);
        viewPager=getActivity().findViewById(R.id.viewPager);
        finish=view.findViewById(R.id.Snext);
        skip=view.findViewById(R.id.Sskip);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences=getActivity().getSharedPreferences("ppp", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putBoolean("story",false);
                editor.apply();
                Intent mainIntent = new Intent(getContext(), MainActivity.class);
                startActivity(mainIntent);
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });

        return view;
    }
}