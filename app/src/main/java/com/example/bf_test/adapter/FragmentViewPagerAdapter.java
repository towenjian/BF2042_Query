package com.example.bf_test.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FragmentViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    public FragmentViewPagerAdapter(@NonNull @NotNull FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList=fragmentList;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList == null ? null : fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList ==null ? 0 :fragmentList.size();
    }
}
