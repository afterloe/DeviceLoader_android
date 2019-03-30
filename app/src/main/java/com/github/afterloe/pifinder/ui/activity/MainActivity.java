package com.github.afterloe.pifinder.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.afterloe.pifinder.DataMoverConstant;
import com.github.afterloe.pifinder.R;
import com.github.afterloe.pifinder.ui.fragment.DeviceFragment;
import com.github.afterloe.pifinder.utils.PackageUtils;
import com.github.afterloe.pifinder.utils.ResUtils;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements Serializable, NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fgManager;
    private Toolbar toolbar;
    private DrawerLayout drawer_layout;
    private NavigationView nav_view;
    private TextView tv_nav_title;
    private ConstraintLayout cly_main_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fgManager = getSupportFragmentManager();
        initView();
        initData();
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
        fgManager.beginTransaction().replace(R.id.cly_main_content,
                DeviceFragment.newInstance(), DataMoverConstant.FG_DEVICE).commit();
        toolbar.setTitle(ResUtils.getString(R.string.menu_person_task));
        String version = PackageUtils.packageName();
        if(version != null) {
            String msg = String.format(ResUtils.getString(R.string.app_name), version);
            tv_nav_title.setText(msg);
        }
    }

    @Override
    // 选择native 中的 项目 实现切换fragment
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.nav_see_little_sister:
//                if (mFgManager.findFragmentByTag(DryConstant.FG_LITTLE_SISTER) == null) {
//                    mFgManager.beginTransaction().replace(R.id.cly_main_content,
//                            LittleSisterFragment.newInstance(), DryConstant.FG_LITTLE_SISTER).commit();
//                    toolbar.setTitle(ResUtils.getString(R.string.menu_see_little_sister));
//                }
//                break;
//            case R.id.nav_see_news:
//                if (mFgManager.findFragmentByTag(DryConstant.FG_NEWS) == null) {
//                    mFgManager.beginTransaction().replace(R.id.cly_main_content,
//                            NewsFragment.newInstance(), DryConstant.FG_NEWS).commit();
//                    toolbar.setTitle(ResUtils.getString(R.string.menu_see_news));
//                }
//                break;
//            case R.id.nav_use_check_weather:
//                if (mFgManager.findFragmentByTag(DryConstant.FG_WEATHER) == null) {
//                    mFgManager.beginTransaction().replace(R.id.cly_main_content,
//                            WeatherFragment.newInstance(), DryConstant.FG_WEATHER).commit();
//                    toolbar.setTitle(ResUtils.getString(R.string.menu_use_check_weather));
//                }
//                break;
//            case R.id.nav_use_check_subway:
//                if (mFgManager.findFragmentByTag(DryConstant.FG_SUBWAY) == null) {
//                    mFgManager.beginTransaction().replace(R.id.cly_main_content,
//                            SubwayFragment.newInstance(), DryConstant.FG_SUBWAY).commit();
//                    toolbar.setTitle(ResUtils.getString(R.string.menu_use_check_subway));
//                }
//                break;
//            case R.id.nav_use_tools:
//                if (mFgManager.findFragmentByTag(DryConstant.FG_TOOLS) == null) {
//                    mFgManager.beginTransaction().replace(R.id.cly_main_content,
//                            ToolsFragment.newInstance(), DryConstant.FG_TOOLS).commit();
//                    toolbar.setTitle(ResUtils.getString(R.string.menu_use_tools));
//                }
//                break;
//            case R.id.nav_else_setting:
//                startActivity(new Intent(this, SettingActivity.class));
//                break;
//            case R.id.nav_else_about:
//                startActivity(new Intent(this, AboutActivity.class));
//                break;
//        }
//        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
