package com.example.ihb.aleksandryurkovskiy.ui.activities;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.ihb.aleksandryurkovskiy.R;
import com.example.ihb.aleksandryurkovskiy.data.managers.DataManager;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.DaoSession;
import com.example.ihb.aleksandryurkovskiy.ui.fragments.CharacterListFragment;
import com.example.ihb.aleksandryurkovskiy.utils.ConstantManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomesTabsActivity extends BaseActivity {


    @BindView(R.id.main_coordinator_container)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.navigation_drawer)
    DrawerLayout mNavigationDrawer;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    @BindView(R.id.tabs)
    TabLayout mTabLayout;

    @BindView(R.id.view_pager)
    ViewPager vpPager;



    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homes_tabs);

        ButterKnife.bind(this);

        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        mTabLayout.setupWithViewPager(vpPager);

        setupToolbar();
        setupDrawer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getIntent().getExtras()!=null){
            int numPage = getIntent().getExtras().getInt(CharacterListFragment.ARGUMENT_PAGE_NUMBER,-1);
            if(numPage!=-1){
                vpPager.setCurrentItem(numPage);
            }
        }
        if(DataManager.getInstance().getDaoSession().getCharacterDao().count()==0){
            showSnackbar("Список героев не может быть получен");
        }
    }

    @Override
    public void onBackPressed() {
        if (mNavigationView.isShown()) {
            mNavigationDrawer.closeDrawer(GravityCompat.START);
        } else {
            //setResult(ConstantManager.EXIT_APP_CODE);
            finish();
        }
    }





    private void setupToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()){
                    case R.id.starks_menu: vpPager.setCurrentItem(0);
                        break;
                    case R.id.lannisters_menu: vpPager.setCurrentItem(1);
                        break;
                    case R.id.targaryens_menu: vpPager.setCurrentItem(2);
                        break;
                    default: showSnackbar(item.getTitle().toString());
                }
                item.setChecked(true);
                mNavigationDrawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void showSnackbar(String message){
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            mNavigationDrawer.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }






    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return CharacterListFragment.newInstance(ConstantManager.STARKS_HOME);
                case 1:
                    return CharacterListFragment.newInstance(ConstantManager.LANNISTERS_HOME);
                case 2:return CharacterListFragment.newInstance(ConstantManager.TARGARYENS_HOME);
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0: return "STARKS";
                case 1: return "LANNISTERS";
                case 2: return "TARGARYENS";
            }
            return "_error_";
        }

    }
}
