package com.example.ihb.aleksandryurkovskiy.data.storage.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Unique;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.cacheColorHint;
import static android.R.attr.name;

/**
 * Created by ihb on 16.10.16.
 */

public class CharacterDTO implements Parcelable {

    private String name;
    private String born;
    private String died;
    private int home;
    private String words;
    private List<String> titles;
    private List<String> aliases;
    private Long father;
    private Long mother;

    public String getName() {
        return name;
    }

    public String getBorn() {
        return born;
    }

    public String getDied() {
        return died;
    }

    public int getHome() {
        return home;
    }

    public String getWords() {
        return words;
    }

    public List<String> getTitles() {
        return titles;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public Long getFather() {
        return father;
    }

    public Long getMother() {
        return mother;
    }

    public CharacterDTO(Character character){
        titles = new ArrayList<>();
        aliases = new ArrayList<>();

        name = character.getName();
        born = character.getBorn();
        died = character.getDied();
        home = character.getHome();
        words = character.getWords();
        for(Title title : character.getTitles()){
            titles.add(title.getTitle());
        }
        for(Aliase aliase : character.getAliases()){
            aliases.add(aliase.getAliase());
        }

        father = character.getFather();
        mother = character.getMother();
    }


    protected CharacterDTO(Parcel in) {
        name = in.readString();
        born = in.readString();
        died = in.readString();
        home = in.readInt();
        words = in.readString();
        if (in.readByte() == 0x01) {
            titles = new ArrayList<>();
            in.readList(titles, String.class.getClassLoader());
        } else {
            titles = null;
        }
        if (in.readByte() == 0x01) {
            aliases = new ArrayList<>();
            in.readList(aliases, String.class.getClassLoader());
        } else {
            aliases = null;
        }

        father = in.readLong();
        mother = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(born);
        dest.writeString(died);
        dest.writeInt(home);
        dest.writeString(words);

        if (titles == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(titles);
        }
        if (aliases == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(aliases);
        }

        dest.writeLong(father);
        dest.writeLong(mother);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<CharacterDTO> CREATOR = new Parcelable.Creator<CharacterDTO>() {
        @Override
        public CharacterDTO createFromParcel(Parcel in) {
            return new CharacterDTO(in);
        }

        @Override
        public CharacterDTO[] newArray(int size) {
            return new CharacterDTO[size];
        }
    };
}
