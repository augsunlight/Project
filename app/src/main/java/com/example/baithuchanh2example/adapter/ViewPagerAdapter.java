package com.example.baithuchanh2example.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.baithuchanh2example.fragment.FragmentHistory;
import com.example.baithuchanh2example.fragment.FragmentHome;
import com.example.baithuchanh2example.fragment.FragmentProfile;
import com.example.baithuchanh2example.fragment.FragmentSearch;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new FragmentHome();
            case 1 : return new FragmentHistory();
            case 2 : return new FragmentSearch();
            case 3 : return new FragmentProfile();
            default: return new FragmentHome();
        }
    }


    @Override
    public int getCount() {
        return 4;
    }
}
