package com.sfbd.serviceforcebd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.fragment.storyBord.Frist_SlideFragment;
import com.sfbd.serviceforcebd.fragment.storyBord.Sec_SlideFragment;
import com.sfbd.serviceforcebd.fragment.storyBord.Third_SlideFragment;

public class StoryAdapter extends FragmentPagerAdapter {


    public StoryAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return  new Frist_SlideFragment();
            case 1:
                return new Sec_SlideFragment();
            case 2:
                return new Third_SlideFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
