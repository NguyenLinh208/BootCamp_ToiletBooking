package com.example.android.toiletbooking.activity;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.example.android.toiletbooking.fragment.ListToilets;
import com.example.android.toiletbooking.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {


    AppSectionsPagerAdapter mAppSectionsPagerAdapter;

    ViewPager mViewPager;

    Handler mHandler;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

        final ActionBar actionBar = getActionBar();

        actionBar.setHomeButtonEnabled(false);

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            if ( i == 0){
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                            .setIcon(R.drawable.male)
                            .setTabListener(this));
            }
            if ( i == 1){
                actionBar.addTab(
                        actionBar.newTab()
                                .setText(mAppSectionsPagerAdapter.getPageTitle(i))
                                .setIcon(R.drawable.female)
                                .setTabListener(this));
            }


        }

//        this.mHandler = new Handler();
//        refresh.run();
//        mHandler.postDelayed(refresh,500);

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

//    private final Runnable refresh = new Runnable()
//    {
//        public void run()
//
//        {
//           Toast.makeText(MainActivity.this,"in runnable", Toast.LENGTH_SHORT).show();
//           // MainActivity.this.mHandler.postDelayed(refresh,500);
//        }
//    };

    public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

        public AppSectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return new ListToilets();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 1:
                    return "WOMEN";
                default:
                    return "MEN";
            }
        }


    }

//    public void setHttpPost(String url) {
//        HttpPost httpPost = new HttpPost(url);
//        DefaultHttpClient client = new DefaultHttpClient();
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("title", title);
//            jsonObject.put("date", dateString);
//            jsonObject.put("body", body);
//            StringEntity se = new xStringEntity(jsonObject.toString());
//            httpPost.setEntity(se);
//            httpPost.setHeader("Accept", "application/json");
//            httpPost.setHeader("Content-Type", "application/json");
//            HttpResponse response = client.execute(httpPost);
//        }
//    }

}
