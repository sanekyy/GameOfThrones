package com.example.ihb.aleksandryurkovskiy.data.storage.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.example.ihb.aleksandryurkovskiy.data.network.res.CharacterModelRes;
import com.example.ihb.aleksandryurkovskiy.utils.Utils;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Unique;

import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by ihb on 14.10.16.
 */

@Entity(active = true, nameInDb = "CHARACTERS")
public class Character {

    @Id
    private Long id;

    @NonNull
    @Unique
    private Long remoteId;

    private String name;

    private String born;

    private String died;

    @NotNull
    private int home;

    @NonNull
    private String words;

    @ToMany(joinProperties = {
            @JoinProperty(name = "remoteId", referencedName = "characterRemoteId")
    })
    private List<Title> titles;

    @ToMany(joinProperties = {
            @JoinProperty(name = "remoteId", referencedName = "characterRemoteId")
    })
    private List<Aliase> aliases;

    private Long father;

    private Long mother;

    /** Used for active entity operations. */
    @Generated(hash = 898307126)
    private transient CharacterDao myDao;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;


    public Character(int starksHome,@NonNull String words,@NonNull Long remoteId) {
        home = starksHome;
        this.words = words;
        this.remoteId = remoteId;

        this.name = "";
        this.born = "";
        this.died = "";
        this.aliases = null;
        this.titles = null;
        this.father = 0L;
        this.mother = 0L;
    }

    public void fill(CharacterModelRes body) {
        this.name = body.name;
        this.born = body.born;
        this.died = body.died;
        this.father = Utils.getCharacterIdFromUrl(body.father);
        this.mother = Utils.getCharacterIdFromUrl(body.mother);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }


    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 731614754)
    public synchronized void resetAliases() {
        aliases = null;
    }


    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 2048441114)
    public List<Aliase> getAliases() {
        if (aliases == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            AliaseDao targetDao = daoSession.getAliaseDao();
            List<Aliase> aliasesNew = targetDao._queryCharacter_Aliases(remoteId);
            synchronized (this) {
                if(aliases == null) {
                    aliases = aliasesNew;
                }
            }
        }
        return aliases;
    }


    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1506933621)
    public synchronized void resetTitles() {
        titles = null;
    }


    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1657258360)
    public List<Title> getTitles() {
        if (titles == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TitleDao targetDao = daoSession.getTitleDao();
            List<Title> titlesNew = targetDao._queryCharacter_Titles(remoteId);
            synchronized (this) {
                if(titles == null) {
                    titles = titlesNew;
                }
            }
        }
        return titles;
    }


    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 162219484)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCharacterDao() : null;
    }


    public Long getMother() {
        return this.mother;
    }


    public void setMother(Long mother) {
        this.mother = mother;
    }


    public Long getFather() {
        return this.father;
    }


    public void setFather(Long father) {
        this.father = father;
    }


    public String getWords() {
        return this.words;
    }


    public void setWords(String words) {
        this.words = words;
    }


    public int getHome() {
        return this.home;
    }


    public void setHome(int home) {
        this.home = home;
    }


    public String getDied() {
        return this.died;
    }


    public void setDied(String died) {
        this.died = died;
    }


    public String getBorn() {
        return this.born;
    }


    public void setBorn(String born) {
        this.born = born;
    }


    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public Long getRemoteId() {
        return this.remoteId;
    }


    public void setRemoteId(Long remoteId) {
        this.remoteId = remoteId;
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    @Generated(hash = 870763612)
    public Character(Long id, @NonNull Long remoteId, String name, String born,
            String died, int home, @NonNull String words, Long father, Long mother) {
        this.id = id;
        this.remoteId = remoteId;
        this.name = name;
        this.born = born;
        this.died = died;
        this.home = home;
        this.words = words;
        this.father = father;
        this.mother = mother;
    }


    @Generated(hash = 1853959157)
    public Character() {
    }
}
