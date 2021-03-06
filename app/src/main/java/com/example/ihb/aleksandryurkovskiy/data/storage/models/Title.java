package com.example.ihb.aleksandryurkovskiy.data.storage.models;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Index;

/**
 * Created by ihb on 14.10.16.
 */

@Entity(active = true, nameInDb = "TITLES",
        indexes = {@Index(value = "characterRemoteId, title", unique = true)})
public class Title {

    @Id
    private Long id;

    private Long characterRemoteId;

    private String title;

    /** Used for active entity operations. */
    @Generated(hash = 1794908068)
    private transient TitleDao myDao;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    public Title(@NonNull Long characterId,@NonNull String title) {
        characterRemoteId = characterId;
        this.title = title;
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
    @Generated(hash = 1326705651)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTitleDao() : null;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @Generated(hash = 1431471766)
    public Title(Long id, Long characterRemoteId, String title) {
        this.id = id;
        this.characterRemoteId = characterRemoteId;
        this.title = title;
    }

    @Generated(hash = 177602963)
    public Title() {
    }

}
