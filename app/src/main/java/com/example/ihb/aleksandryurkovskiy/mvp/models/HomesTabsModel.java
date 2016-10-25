package com.example.ihb.aleksandryurkovskiy.mvp.models;

import com.example.ihb.aleksandryurkovskiy.data.managers.DataManager;
import com.example.ihb.aleksandryurkovskiy.data.storage.models.Character;
import com.example.ihb.aleksandryurkovskiy.ui.adapters.CharactersAdapter;
import com.example.ihb.aleksandryurkovskiy.ui.fragments.CharacterListRetainFragment;
import com.example.ihb.aleksandryurkovskiy.utils.ConstantManager;

import java.util.List;

/**
 * Created by ihb on 24.10.16.
 */

public class HomesTabsModel {

    private CharactersAdapter mCharactersAdapter;
    private List<Character> mCharacters;
    private CharacterListRetainFragment mRetainFragment;
    
    private List<Character> mStarksList;
    private List<Character> mLannistersList;
    private List<Character> mTargaryensList;

    public HomesTabsModel(){
        mStarksList = DataManager.getInstance().getCharacterListFromDbByHomeId(ConstantManager.STARKS_HOME);
        mLannistersList = DataManager.getInstance().getCharacterListFromDbByHomeId(ConstantManager.LANNISTERS_HOME);
        mTargaryensList = DataManager.getInstance().getCharacterListFromDbByHomeId(ConstantManager.TARGARYENS_HOME);
    }

    public boolean isEmpty(int homeId) {
        switch (homeId){
            case ConstantManager.STARKS_HOME: return mStarksList.isEmpty();
            case ConstantManager.LANNISTERS_HOME: return mLannistersList.isEmpty();
            case ConstantManager.TARGARYENS_HOME: return mTargaryensList.isEmpty();
        }
        return true;
    }

    public List<Character> getStarksList() {
        return mStarksList;
    }

    public List<Character> getLannistersList() {
        return mLannistersList;
    }

    public List<Character> getTargaryensList() {
        return mTargaryensList;
    }
}
