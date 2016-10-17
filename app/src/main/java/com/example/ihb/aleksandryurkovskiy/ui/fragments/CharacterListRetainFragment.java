package com.example.ihb.aleksandryurkovskiy.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.ihb.aleksandryurkovskiy.data.storage.models.Character;

import java.util.List;

/**
 * Created by ihb on 20.07.16.
 */
public class CharacterListRetainFragment extends Fragment {
    private List<Character> mCharacters;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void setCharactersList(List<Character> users) {
        mCharacters = users;
    }

    public List<Character> getCharactersList() {
        return mCharacters;
    }

}
