package newsdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import davidtgnewsproject.newsdemo.com.davidtgnewsproject.R;
import newsdemo.fragment.AboutDavidFragment;
import newsdemo.fragment.AppleFragment;
import newsdemo.fragment.SportsFragment;
import newsdemo.fragment.TechFragment;

public class TGActivity extends FragmentActivity {
    private List<String> stringList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private TabLayout mTabLayout;
    private ViewPager mviewPager;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tg);
        initView();
        stringList.add("早间新闻");
        stringList.add("午间新闻");
        stringList.add("晚间新闻");
        stringList.add("天气预报");

        fragmentList.add(new SportsFragment());
        fragmentList.add(new TechFragment());
        fragmentList.add(new AppleFragment());
        fragmentList.add(new AboutDavidFragment());

        myAdapter = new MyAdapter(getSupportFragmentManager());
        mviewPager.setAdapter(myAdapter);
        mviewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mviewPager);
        mTabLayout.setTabsFromPagerAdapter(myAdapter);
    }


    class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return stringList.get(position);
        }
    }

    private void initView() {
        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mviewPager = (ViewPager) findViewById(R.id.mViewPager);
    }



}
