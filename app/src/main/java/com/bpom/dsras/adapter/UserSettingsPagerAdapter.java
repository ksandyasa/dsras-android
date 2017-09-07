package com.bpom.dsras.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bpom.dsras.fragment.ChangePasswordFragment;
import com.bpom.dsras.fragment.ChangeProfileFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apridosandyasa on 8/6/16.
 */
public class UserSettingsPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private List<Fragment> fragmentList;

    public UserSettingsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        initializeFragmentList();
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragmentList.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return this.fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (position == 0) ? "PROFILE" : "PASSWORD";
    }

    private void initializeFragmentList() {
        this.fragmentList = new ArrayList<>();
        this.fragmentList.add(new ChangeProfileFragment(this.context));
        this.fragmentList.add(new ChangePasswordFragment(this.context));
    }

}
