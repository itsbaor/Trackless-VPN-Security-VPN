package com.rbs.studio.trackless.vpn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.rbs.studio.trackless.vpn.R;
import com.rbs.studio.trackless.vpn.adapter.IntroViewPagerAdapter;
import com.rbs.studio.trackless.vpn.databinding.ActivityFirstBoardingBinding;
import com.rbs.studio.trackless.vpn.utils.Const;
import com.rbs.studio.trackless.vpn.utils.ScreenItem;
import com.rbs.studio.trackless.vpn.utils.SessionManager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class FirstBoardingActivity extends BaseActivity {
    ActivityFirstBoardingBinding binding;
    IntroViewPagerAdapter introViewPagerAdapter;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_first_boarding);
        sessionManager = new SessionManager(this);

        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem(
                getString(R.string.first_boarding_title),
                getString(R.string.first_boarding_description),
                R.drawable.first_boarding_logo,
                getString(R.string.btn_allow_vpn),
                getString(R.string.btn_exit)
        ));
        mList.add(new ScreenItem(
                getString(R.string.second_boarding_title),
                getString(R.string.second_boarding_description),
                R.drawable.second_boarding_logo,
                getString(R.string.btn_continue),
                getString(R.string.btn_skip)
        ));
        mList.add(new ScreenItem(
                getString(R.string.third_boarding_title),
                getString(R.string.third_boarding_description),
                R.drawable.third_boarding_logo,
                getString(R.string.btn_continue),
                getString(R.string.btn_skip)
        ));
        introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        binding.tablayout.setupWithViewPager(binding.viewpager, true);
        binding.viewpager.setAdapter(introViewPagerAdapter);
        binding.viewpager.setCurrentItem(0);
        binding.firstBoardingBt.setOnClickListener(view -> binding.viewpager.setCurrentItem(1));
        binding.tablayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    binding.firstBoardingBt.setVisibility(View.VISIBLE);
                    binding.secondBoardingBt.setVisibility(View.GONE);
                    binding.secondBoardingSkip.setVisibility(View.GONE);
                    binding.thirdBoardingBt.setVisibility(View.GONE);
                    binding.thirdBoardingSkip.setVisibility(View.GONE);

                    binding.firstBoardingBt.setOnClickListener(view -> binding.viewpager.setCurrentItem(1));
                } else if (tab.getPosition() == 1) {
                    binding.secondBoardingBt.setVisibility(View.VISIBLE);
                    binding.firstBoardingBt.setVisibility(View.GONE);
                    binding.firstBoardingSkip.setVisibility(View.GONE);
                    binding.thirdBoardingBt.setVisibility(View.GONE);
                    binding.thirdBoardingSkip.setVisibility(View.GONE);
                    binding.secondBoardingBt.setOnClickListener(view -> binding.viewpager.setCurrentItem(2));
                } else if (tab.getPosition() == 2) {
                    binding.thirdBoardingBt.setVisibility(View.VISIBLE);
                    binding.firstBoardingBt.setVisibility(View.GONE);
                    binding.firstBoardingSkip.setVisibility(View.GONE);
                    binding.secondBoardingBt.setVisibility(View.GONE);
                    binding.secondBoardingSkip.setVisibility(View.GONE);
                    binding.thirdBoardingBt.setOnClickListener(view -> {
                        sessionManager.saveBooleanValue(Const.onBoarding, true);
                        startActivity(new Intent(FirstBoardingActivity.this, HomeActivity.class));
                    });
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}