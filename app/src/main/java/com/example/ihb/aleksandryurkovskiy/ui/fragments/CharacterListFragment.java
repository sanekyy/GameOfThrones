package com.example.ihb.aleksandryurkovskiy.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ihb.aleksandryurkovskiy.R;
import com.example.ihb.aleksandryurkovskiy.mvp.presenters.HomesTabsPresenter;
import com.example.ihb.aleksandryurkovskiy.mvp.presenters.IHomesTabsPresenter;
import com.example.ihb.aleksandryurkovskiy.ui.adapters.CharactersAdapter;
import com.example.ihb.aleksandryurkovskiy.utils.ConstantManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharacterListFragment extends Fragment {

    IHomesTabsPresenter mPresenter = HomesTabsPresenter.getInstance();

    public static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    int homeId;

    @BindView(R.id.character_list)
    RecyclerView mRecyclerView;



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
        homeId = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
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
    }

    private void initUserList() {
        CharactersAdapter mCharactersAdapter = new CharactersAdapter(mPresenter.getCharactersList(homeId), new CharactersAdapter.CharacterViewHolder.CustomClickListener() {
            @Override
            public void onCharacterItemClickListener(int position) {
                mPresenter.onCharacterItemClicked(homeId, position);
            }
        });
        mRecyclerView.setAdapter(mCharactersAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==ConstantManager.OPEN_TABS) {
            getActivity().finish();
        }
    }
}
