package com.example.ihb.aleksandryurkovskiy.ui.activities;

import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ihb.aleksandryurkovskiy.BuildConfig;
import com.example.ihb.aleksandryurkovskiy.R;
import com.example.ihb.aleksandryurkovskiy.mvp.presenters.CharacterPresenter;
import com.example.ihb.aleksandryurkovskiy.mvp.presenters.ICharacterPresentor;
import com.example.ihb.aleksandryurkovskiy.mvp.views.ICharacterView;
import com.example.ihb.aleksandryurkovskiy.utils.ConstantManager;
import com.example.ihb.aleksandryurkovskiy.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class CharacterActivity extends BaseActivity implements ICharacterView, View.OnClickListener {
    private static final String TAG = ConstantManager.TAG_PREFIX + " ProfileUserActivit";
    public static final String CHARACTER_ID = "CHARACTER_ID";

    CharacterPresenter mPresenter = new CharacterPresenter();

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.navigation_drawer)
    DrawerLayout mNavigationDrawer;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    @BindView(R.id.home_main_iv)
    ImageView mHomeImg;

    @BindView(R.id.words_tv)
    TextView mWords;
    @BindView(R.id.born_tv)
    TextView mBorn;
    @BindView(R.id.died_tv)
    TextView mDied;
    @BindView(R.id.titles_tv)
    TextView mTitles;
    @BindView(R.id.aliases_tv)
    TextView mAliases;
    @BindView(R.id.father_btn)
    Button mFather;
    @BindView(R.id.mother_btn)
    Button mMother;

    @BindViews({R.id.home_1_iv, R.id.home_2_iv, R.id.home_3_iv, R.id.home_4_iv, R.id.home_5_iv})
    List<ImageView> mIcons;

    //region ============== Life cycle ==============

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        ButterKnife.bind(this);

        setupToolbar();
        setupDrawer();

        mPresenter.takeView(this);
        mPresenter.setCharacterId(getIntent().getLongExtra(CHARACTER_ID,0));
        mPresenter.initView();

        mFather.setOnClickListener(this);
        mMother.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onDestroy() {
        mPresenter.dropView();
        super.onDestroy();
    }

    //endregion

    //region ============== IAuthView ==============

    @Override
    public void showMessage(String message) {
        Snackbar.make(findViewById(R.id.main_layout), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError(Throwable e) {
        if(BuildConfig.DEBUG){
            showMessage(e.getMessage());
            e.printStackTrace();
        } else {
            showMessage("Извините, что-то пошло не так, попробуйз позже");
        }
    }

    @Override
    public ICharacterPresentor getPresenter() {
        return mPresenter;
    }

    @Override
    public void startActivity(Class clazz, Bundle bundle) {
        Intent intent = new Intent(CharacterActivity.this, clazz);
        intent.putExtras(bundle);
        startActivityForResult(intent, ConstantManager.NOTHING);
    }

    @Override
    public void startActivityAndFinish(Class clazz, Bundle bundle, int resultCode) {
        startActivity(clazz, bundle);
        setResult(resultCode);
        finish();
    }

    @Override
    public void setTitle(String characterName) {
        mCollapsingToolbarLayout.setTitle(characterName);
    }

    @Override
    public void setHomeImg(int imgId) {
        ViewGroup.LayoutParams layoutParams = mHomeImg.getLayoutParams();

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);

        Drawable drawable = getResources().getDrawable(imgId);

        layoutParams.height = (int) ((double) size.x * (double) drawable.getMinimumHeight()/ (double) drawable.getMinimumWidth());
        mHomeImg.setLayoutParams(layoutParams);

        mHomeImg.requestLayout();

        mHomeImg.setImageResource(imgId);
    }

    @Override
    public void setHomeIcons(int iconId) {
        for(ImageView view : mIcons) {
            view.setImageResource(iconId);
            view.setImageResource(iconId);
            view.setImageResource(iconId);
        }
    }

    @Override
    public void setWords(String words) {
        mWords.setText(words);
    }

    @Override
    public void setBorn(String born) {
        mBorn.setText(born);
    }

    @Override
    public void setDied(String died) {
        mDied.setText(died);
    }

    @Override
    public void setTitles(List<String> titles) {
        mTitles.setText(Utils.joinString(titles, "\n"));
    }

    @Override
    public void setAliases(List<String> aliases) {
        mAliases.setText(Utils.joinString(aliases, "\n"));
    }

    @Override
    public void setFather(String father) {
        mFather.setText(father);
    }

    @Override
    public void setMother(String mother) {
        mMother.setText(mother);
    }

    @Override
    public void hideWords() {
        ((LinearLayout) findViewById(R.id.main_layout)).removeView(findViewById(R.id.words_layout));
    }

    @Override
    public void hideBorn() {
        ((LinearLayout) findViewById(R.id.main_layout)).removeView(findViewById(R.id.born_layout));
    }

    @Override
    public void hideDied() {
        ((LinearLayout) findViewById(R.id.main_layout)).removeView(findViewById(R.id.died_layout));
    }

    @Override
    public void hideTitles() {
        ((LinearLayout) findViewById(R.id.main_layout)).removeView(findViewById(R.id.titles_layout));
    }

    @Override
    public void hideAliases() {
        ((LinearLayout) findViewById(R.id.main_layout)).removeView(findViewById(R.id.aliases_layout));
    }

    @Override
    public void hideFather() {
        ((LinearLayout) findViewById(R.id.main_layout)).removeView(findViewById(R.id.father_layout));
    }

    @Override
    public void hideMother() {
        ((LinearLayout) findViewById(R.id.main_layout)).removeView(findViewById(R.id.mother_layout));
    }

    //endregion


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==ConstantManager.OPEN_TABS){
            setResult(ConstantManager.OPEN_TABS);
            finish();
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
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void setupToolbar(){
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.father_btn: mPresenter.onFatherClicked();
                break;
            case R.id.mother_btn: mPresenter.onMotherClicked();
                break;
        }
    }
}
