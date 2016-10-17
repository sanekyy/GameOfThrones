package com.example.ihb.aleksandryurkovskiy.ui.activities;
;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ihb.aleksandryurkovskiy.R;
import com.example.ihb.aleksandryurkovskiy.data.managers.DataManager;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.CharacterDTO;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.CharacterDao;
import com.example.ihb.aleksandryurkovskiy.ui.fragments.CharacterListFragment;
import com.example.ihb.aleksandryurkovskiy.utils.ConstantManager;
import com.example.ihb.aleksandryurkovskiy.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class CharacterActivity extends BaseActivity {

    private static final String TAG = ConstantManager.TAG_PREFIX + " ProfileUserActivit";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.navigation_drawer)
    DrawerLayout mNavigationDrawer;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    @BindView(R.id.home_main_iv)
    ImageView mHomeImage;

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
    @BindView(R.id.father_tv)
    TextView mFather;
    @BindView(R.id.mother_tv)
    TextView mMother;

    @BindViews({R.id.home_1_iv, R.id.home_2_iv, R.id.home_3_iv, R.id.home_4_iv, R.id.home_5_iv})
    List<ImageView> mIcons;

    CharacterDao mCharacterDao;

    String died;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        ButterKnife.bind(this);

        mCharacterDao = DataManager.getInstance().getDaoSession().getCharacterDao();

        setupToolbar();
        initCharacterData();
        setupHomeImage();
        setupDrawer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(died!=null&&!"".equals(died)){
            showSnackbar("Died " + died);
        }
    }

    void setupHomeImage(){
        ViewGroup.LayoutParams layoutParams = mHomeImage.getLayoutParams();

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);

        Drawable drawable = getResources().getDrawable(R.drawable.stark);

        layoutParams.height = (int) ((double) size.x * (double) drawable.getMinimumHeight()/ (double) drawable.getMinimumWidth());
        mHomeImage.setLayoutParams(layoutParams);

        mHomeImage.requestLayout();
    }

    private void setupDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                Bundle bundle = new Bundle();
                switch (item.getItemId()){
                    case R.id.starks_menu: bundle.putInt(CharacterListFragment.ARGUMENT_PAGE_NUMBER, 0);
                        break;
                    case R.id.lannisters_menu: bundle.putInt(CharacterListFragment.ARGUMENT_PAGE_NUMBER, 1);
                        break;
                    case R.id.targaryens_menu: bundle.putInt(CharacterListFragment.ARGUMENT_PAGE_NUMBER, 2);
                        break;
                    default: showSnackbar(item.getTitle().toString());
                }
                item.setChecked(true);
                mNavigationDrawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(CharacterActivity.this, HomesTabsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                return false;
            }
        });
    }

    private void showSnackbar(String message){
        Snackbar.make(findViewById(R.id.main_layout), message, Snackbar.LENGTH_LONG).show();
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

    private void initCharacterData(){
        final CharacterDTO characterDTO = getIntent().getParcelableExtra(ConstantManager.PARCELABLE_KEY);

        mCollapsingToolbarLayout.setTitle(characterDTO.getName());

        switch (characterDTO.getHome()){
            case ConstantManager.LANNISTERS_HOME: mHomeImage.setImageResource(R.drawable.lannister);
                break;
            case ConstantManager.STARKS_HOME: mHomeImage.setImageResource(R.drawable.stark);
                break;
            case ConstantManager.TARGARYENS_HOME: mHomeImage.setImageResource(R.drawable.targarien);
                break;
        }

        for(ImageView view : mIcons){
            switch (characterDTO.getHome()){
                case ConstantManager.LANNISTERS_HOME: view.setImageResource(R.drawable.lanister_icon);
                    break;
                case ConstantManager.STARKS_HOME: view.setImageResource(R.drawable.stark_icon);
                    break;
                case ConstantManager.TARGARYENS_HOME: view.setImageResource(R.drawable.targaryens_icon);
                    break;
            }
        }


        if(characterDTO.getWords()==null||"".equals(characterDTO.getWords())){
            ((LinearLayout) findViewById(R.id.main_layout)).removeView(findViewById(R.id.words_layout));
        } else {
            mWords.setText(characterDTO.getWords());
        }

        if(characterDTO.getBorn()==null||"".equals(characterDTO.getBorn())){
            ((LinearLayout) findViewById(R.id.main_layout)).removeView(findViewById(R.id.born_layout));
        } else {
            mBorn.setText(characterDTO.getBorn());
        }

        if(characterDTO.getDied()==null||"".equals(characterDTO.getDied())){
            ((LinearLayout) findViewById(R.id.main_layout)).removeView(findViewById(R.id.died_layout));
        } else {
            mDied.setText(characterDTO.getDied());
        }

        if(characterDTO.getTitles()==null||characterDTO.getTitles().size()==0){
            ((LinearLayout) findViewById(R.id.main_layout)).removeView(findViewById(R.id.titles_layout));
        } else {
            mTitles.setText(Utils.joinString(characterDTO.getTitles(), "\n"));
        }

        if(characterDTO.getAliases()==null||characterDTO.getAliases().size()==0){
            ((LinearLayout) findViewById(R.id.main_layout)).removeView(findViewById(R.id.aliases_layout));
        } else {
            mAliases.setText(Utils.joinString(characterDTO.getAliases(), "\n"));
        }

        if(characterDTO.getFather()==null||characterDTO.getFather().equals(0L)||mCharacterDao.queryBuilder().where(CharacterDao.Properties.RemoteId.between(characterDTO.getFather()*1000, (characterDTO.getFather()+1)*1000)).count()==0){
            ((LinearLayout) findViewById(R.id.main_layout)).removeView(findViewById(R.id.father_layout));
        } else {
            mFather.setText(mCharacterDao.queryBuilder().where(CharacterDao.Properties.RemoteId.between(characterDTO.getFather()*1000, (characterDTO.getFather()+1)*1000)).unique().getName());
            mFather.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Long fatherId = ((CharacterDTO) getIntent().getParcelableExtra(ConstantManager.PARCELABLE_KEY)).getFather();

                    CharacterDTO characterDTO = new CharacterDTO(mCharacterDao.queryBuilder().where(CharacterDao.Properties.RemoteId.between(fatherId*1000, (fatherId+1)*1000)).unique());

                    Intent profileIntent = new Intent(CharacterActivity.this, CharacterActivity.class);
                    profileIntent.putExtra(ConstantManager.PARCELABLE_KEY, characterDTO);

                    startActivity(profileIntent);
                }
            });
        }

        if(characterDTO.getMother()==null||characterDTO.getMother().equals(0L)||mCharacterDao.queryBuilder().where(CharacterDao.Properties.RemoteId.between(characterDTO.getMother()*1000, (characterDTO.getMother()+1)*1000)).count()==0){
            ((LinearLayout) findViewById(R.id.main_layout)).removeView(findViewById(R.id.mother_layout));
        } else {
            mMother.setText(mCharacterDao.queryBuilder().where(CharacterDao.Properties.RemoteId.between(characterDTO.getMother()*1000, (characterDTO.getMother()+1)*1000)).unique().getName());
            mMother.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Long motherId = ((CharacterDTO) getIntent().getParcelableExtra(ConstantManager.PARCELABLE_KEY)).getMother();

                    CharacterDTO characterDTO = new CharacterDTO(mCharacterDao.queryBuilder().where(CharacterDao.Properties.RemoteId.between(motherId*1000, (motherId+1)*1000)).unique());

                    Intent profileIntent = new Intent(CharacterActivity.this, CharacterActivity.class);
                    profileIntent.putExtra(ConstantManager.PARCELABLE_KEY, characterDTO);

                    startActivity(profileIntent);
                }
            });
        }

    }
}
