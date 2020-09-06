package com.sfbd.serviceforcebd.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sfbd.serviceforcebd.R;
import com.sfbd.serviceforcebd.adapter.StoryAdapter;

public class StoryBord_Screen extends AppCompatActivity {
    private ViewPager viewPager;
    private LinearLayout dot;
    private StoryAdapter storyAdapter;
    private TextView[] mDot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_bord__screen);
        viewPager=findViewById(R.id.viewPager);
        dot=findViewById(R.id.linear);
        storyAdapter=new StoryAdapter(getSupportFragmentManager());
        viewPager.setAdapter(storyAdapter);
        StoryDot(0);
        viewPager.addOnPageChangeListener(v );

    }

    private void StoryDot(int p) {
        mDot=new TextView[2];
        dot.removeAllViews();
        for(int i=0;i<mDot.length;i++)
        {
            mDot[i]=new TextView(this);
            mDot[i].setText(Html.fromHtml("&#8226"));
            mDot[i].setTextSize(35);

            mDot[i].setTextColor(getResources().getColor(R.color.colorPrimary));
            dot.addView(mDot[i]);

        }
            if(mDot.length>0) {
                mDot[p].setTextColor(getResources().getColor(R.color.white));
            }

    }
    ViewPager.OnPageChangeListener v=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            StoryDot(position);

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}