package com.example.ihb.aleksandryurkovskiy.data.storage.models;

import android.support.annotation.NonNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import java.util.List;

/**
 * Created by ihb on 14.10.16.
 */

@Entity(active = true, nameInDb = "ALIASES")
public class Aliase {

    @Id
    private Long id;

    private Long characterRemoteId;

    private String aliase;

    /** Used for active entity operations. */
    @Generated(hash = 947773599)
    private transient AliaseDao myDao;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;


    public Aliase(@NonNull Long characterId,@NonNull String aliase) {
        characterRemoteId = characterId;
        this.aliase = aliase;
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


    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1907117852)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getAliaseDao() : null;
    }


    public String getAliase() {
        return this.aliase;
    }


    public void setAliase(String aliase) {
        this.aliase = aliase;
    }


    public Long getCharacterRemoteId() {
        return this.characterRemoteId;
    }


    public void setCharacterRemoteId(Long characterRemoteId) {
        this.characterRemoteId = characterRemoteId;
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    @Generated(hash = 1806143535)
    public Aliase(Long id, Long characterRemoteId, String aliase) {
        this.id = id;
        this.characterRemoteId = characterRemoteId;
        this.aliase = aliase;
    }


    @Generated(hash = 1566368553)
    public Aliase() {
    }
}
