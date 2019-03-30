package com.github.afterloe.pifinder.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.afterloe.pifinder.DataMoverConstant;
import com.github.afterloe.pifinder.R;
import com.github.afterloe.pifinder.ui.fragment.DeviceListFragment;
import com.github.afterloe.pifinder.utils.PackageUtils;
import com.github.afterloe.pifinder.utils.ResUtils;

import java.io.Serializable;

public class ConfigActivity extends AppCompatActivity implements Serializable, NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager mFgManager;
    private Toolbar toolbar;
    private DrawerLayout drawer_layout;
    private NavigationView nav_view;
    private TextView tv_nav_title;
    private ConstraintLayout cly_main_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFgManager = getSupportFragmentManager();
        initView();
        initData();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        nav_view = findViewById(R.id.nav_view);
        tv_nav_title = nav_view.getHeaderView(0).findViewById(R.id.tv_nav_title);
        drawer_layout = findViewById(R.id.drawer_layout);
        cly_main_content = findViewById(R.id.cly_main_content);

        setSupportActionBar(toolbar);
        nav_view.setItemIconTintList(null);
        nav_view.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initData() {
        mFgManager.beginTransaction().replace(R.id.cly_main_content,
                DeviceListFragment.newInstance(), DataMoverConstant.FG_LITTLE_SISTER).commit();
        toolbar.setTitle(ResUtils.getString(R.string.menu_see_little_sister));
        String version = PackageUtils.packageName();
        if(version != null) {
            String msg = String.format(ResUtils.getString(R.string.menu_drysister_version), version);
            tv_nav_title.setText(msg);
        }
    }
}
