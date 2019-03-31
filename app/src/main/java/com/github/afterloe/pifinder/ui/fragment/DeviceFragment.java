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

public class DeviceFragment extends Fragment implements Serializable {

    private final String[] mTitles = {"远程设备列表", "附近设备", "巡检历史"};
    private Context context;
    private TabLayout tl_devices;
    private ViewPager vp_content;
    protected CompositeDisposable mSubscriptions;

    public static DeviceFragment newInstance() {
        return new DeviceFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_list, container, false);
        tl_devices = view.findViewById(R.id.tl_devices);
        vp_content = view.findViewById(R.id.vp_content);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        mSubscriptions = new CompositeDisposable();
        TabFragmentPagerAdapter adapter = new TabFragmentPagerAdapter(this.getChildFragmentManager());
        vp_content.setAdapter(adapter);
        tl_devices.setupWithViewPager(vp_content);
    }

    private class TabFragmentPagerAdapter extends FragmentPagerAdapter {

        private TabFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // 选择加载 fragment
            switch (position) {
                case 0: return CloudDeviceFragment.newInstance();
                default:
                    return CloudDeviceFragment.newInstance();
            }
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
