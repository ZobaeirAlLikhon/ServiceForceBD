package com.sfbd.serviceforcebd.fragment.storyBord;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.activity.MainActivity;

public class Frist_SlideFragment extends Fragment {
    ViewPager viewPager;
    TextView next,skip;
    Context context;

    public Frist_SlideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_frist__slide, container, false);
        viewPager=getActivity().findViewById(R.id.viewPager);
        next=view.findViewById(R.id.Snext);
        skip=view.findViewById(R.id.Sskip);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(getContext(), MainActivity.class);
                startActivity(mainIntent);


            }
        });
        return view;
    }
}