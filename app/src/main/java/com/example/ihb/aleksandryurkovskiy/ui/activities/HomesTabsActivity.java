package com.example.ihb.aleksandryurkovskiy.ui.activities;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.ihb.aleksandryurkovskiy.BuildConfig;
import com.example.ihb.aleksandryurkovskiy.R;
import com.example.ihb.aleksandryurkovskiy.mvp.presenters.HomesTabsPresenter;
import com.example.ihb.aleksandryurkovskiy.mvp.presenters.IHomesTabsPresenter;
import com.example.ihb.aleksandryurkovskiy.mvp.views.IHomesTabsView;
import com.example.ihb.aleksandryurkovskiy.ui.fragments.CharacterListFragment;
import com.example.ihb.aleksandryurkovskiy.utils.ConstantManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomesTabsActivity extends BaseActivity implements IHomesTabsView {

    IHomesTabsPresenter mPresenter = HomesTabsPresenter.getInstance();

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

    //region ============== Life cycle ==============

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homes_tabs);

        ButterKnife.bind(this);

        mPresenter.takeView(this);
        mPresenter.initView();

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.dropView();
    }

    //endregion

    //region ============== IHomeTabsView ==============

    @Override
    public void showMessage(String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError(Throwable e) {
        if(BuildConfig.DEBUG){
            showMessage(e.getMessage());
            e.printStackTrace();
        } else {
            showMessage("Извините, что-то пошло не так, попробуйте позже");
        }
    }

    @Override
    public IHomesTabsPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void setCurrentPage(int pageId) {
        vpPager.setCurrentItem(pageId);
    }

    @Override
    public void startActivityForResult(Class clazz, Long characterId) {
        Intent profileIntent = new Intent(this, clazz);
        profileIntent.putExtra(CharacterActivity.CHARACTER_ID, characterId);
        startActivityForResult(profileIntent, ConstantManager.NOTHING);
    }

    //endregion

    @Override
    public void onBackPressed() {
        if (mNavigationView.isShown()) {
            mNavigationDrawer.closeDrawer(GravityCompat.START);
        } else {
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
                mPresenter.onNavigationItemClicked(item);
                item.setChecked(true);
                mNavigationDrawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
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
                case ConstantManager.STARKS_PAGE:
                    return CharacterListFragment.newInstance(ConstantManager.STARKS_HOME);
                case ConstantManager.LANNISTERS_PAGE:
                    return CharacterListFragment.newInstance(ConstantManager.LANNISTERS_HOME);
                case ConstantManager.TARGARYENS_PAGE:return CharacterListFragment.newInstance(ConstantManager.TARGARYENS_HOME);
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case ConstantManager.STARKS_PAGE: return "STARKS";
                case ConstantManager.LANNISTERS_PAGE: return "LANNISTERS";
                case ConstantManager.TARGARYENS_PAGE: return "TARGARYENS";
            }
            return "_error_";
        }
    }
}
