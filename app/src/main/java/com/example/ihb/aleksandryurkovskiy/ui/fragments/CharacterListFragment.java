package com.example.ihb.aleksandryurkovskiy.ui.fragments;

import android.content.Intent;
import android.support.design.widget.Snackbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ihb.aleksandryurkovskiy.R;
import com.example.ihb.aleksandryurkovskiy.data.managers.DataManager;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.AliaseDao;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.Character;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.CharacterDTO;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.CharacterDao;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.TitleDao;
import com.example.ihb.aleksandryurkovskiy.ui.activities.CharacterActivity;
import com.example.ihb.aleksandryurkovskiy.ui.adapters.CharactersAdapter;
import com.example.ihb.aleksandryurkovskiy.utils.ConstantManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharacterListFragment extends Fragment {

    public static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    int houseNumber;

    @BindView(R.id.character_list)
    RecyclerView mRecyclerView;




    private DataManager mDataManager;
    private CharactersAdapter mCharactersAdapter;
    private List<Character> mCharacters;
    private CharacterListRetainFragment mRetainFragment;

    private AliaseDao mAliaseDao;
    private TitleDao mTitleDao;
    private CharacterDao mCharacterDao;

    public static CharacterListFragment newInstance(int page) {
        CharacterListFragment pageFragment = new CharacterListFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        houseNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);


        mDataManager = DataManager.getInstance();


        mAliaseDao = mDataManager.getDaoSession().getAliaseDao();
        mTitleDao = mDataManager.getDaoSession().getTitleDao();
        mCharacterDao = mDataManager.getDaoSession().getCharacterDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_character_list, null);

        ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        initUserList();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRetainFragment != null&&mCharactersAdapter != null) {
            mRetainFragment.setCharactersList(mCharactersAdapter.getCharacters());
        }
    }


    private void initUserList() {
        FragmentManager fm = getFragmentManager();
        mRetainFragment = (CharacterListRetainFragment) fm.findFragmentByTag(String.valueOf(houseNumber));
        if (mRetainFragment == null) {
            mRetainFragment = new CharacterListRetainFragment();
            fm.beginTransaction().add(mRetainFragment, "users_data").commit();
            loadCharactersFromDb();
        } else {
            mCharacters = mRetainFragment.getCharactersList();
            mCharactersAdapter = new CharactersAdapter(mCharacters, new CharactersAdapter.CharacterViewHolder.CustomClickListener() {
                @Override
                public void onUserItemClickListener(int position) {
                    CharacterDTO characterDTO = new CharacterDTO(mCharacters.get(position));

                    Intent profileIntent = new Intent(getActivity(), CharacterActivity.class);
                    profileIntent.putExtra(ConstantManager.PARCELABLE_KEY, characterDTO);

                    startActivity(profileIntent);
                }
            });
            mRecyclerView.setAdapter(mCharactersAdapter);
        }
    }

    private void loadCharactersFromDb() {

        List<Character> characters = mDataManager.getCharacterOfHouseListFromDb(houseNumber);

        if(characters.size() != 0){
            showUsers(characters);
        }
    }

    private void showUsers(List<Character> characters){
        mCharacters = characters;
        mCharactersAdapter = new CharactersAdapter(mCharacters, new CharactersAdapter.CharacterViewHolder.CustomClickListener() {
            @Override
            public void onUserItemClickListener(int position) {
                CharacterDTO characterDTO = new CharacterDTO(mCharacters.get(position));

                Intent profileIntent = new Intent(getActivity(), CharacterActivity.class);
                profileIntent.putExtra(ConstantManager.PARCELABLE_KEY, characterDTO);

                startActivity(profileIntent);
            }
        });
        mRecyclerView.swapAdapter(mCharactersAdapter, false);
        mRetainFragment.setCharactersList(mCharactersAdapter.getCharacters());
    }
}
