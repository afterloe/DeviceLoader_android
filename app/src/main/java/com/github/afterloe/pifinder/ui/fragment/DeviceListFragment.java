package com.github.afterloe.pifinder.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.afterloe.pifinder.R;

import java.io.Serializable;

import io.reactivex.disposables.CompositeDisposable;

public class DeviceListFragment extends Fragment implements Serializable {

    private Context mContext;
    private TabLayout tl_little_sister;
    private ViewPager vp_content;
    protected CompositeDisposable mSubscriptions;

    public static DeviceListFragment newInstance() {
        DeviceListFragment fragment = new DeviceListFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_list, container, false);
        tl_little_sister = view.findViewById(R.id.tl_little_sister);
        vp_content = view.findViewById(R.id.vp_content);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        mSubscriptions = new CompositeDisposable();
        TabFragmentPagerAdapter adapter = new TabFragmentPagerAdapter(this.getChildFragmentManager());
        vp_content.setAdapter(adapter);
        tl_little_sister.setupWithViewPager(vp_content);
    }

    private class TabFragmentPagerAdapter extends FragmentPagerAdapter {
        private final String[] mTitles = {"Gank.io"};

        private TabFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return GankMZFragment.newInstance();
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
}
