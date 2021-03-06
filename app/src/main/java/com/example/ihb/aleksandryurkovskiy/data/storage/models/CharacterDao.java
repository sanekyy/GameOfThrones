package com.example.ihb.aleksandryurkovskiy.data.storage.models;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CHARACTERS".
*/
public class CharacterDao extends AbstractDao<Character, Long> {

    public static final String TABLENAME = "CHARACTERS";

    /**
     * Properties of entity Character.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property RemoteId = new Property(1, long.class, "remoteId", false, "REMOTE_ID");
        public final static Property Name = new Property(2, String.class, "name", false, "NAME");
        public final static Property Born = new Property(3, String.class, "born", false, "BORN");
        public final static Property Died = new Property(4, String.class, "died", false, "DIED");
        public final static Property Home = new Property(5, int.class, "home", false, "HOME");
        public final static Property Words = new Property(6, String.class, "words", false, "WORDS");
        public final static Property Father = new Property(7, Long.class, "father", false, "FATHER");
        public final static Property Mother = new Property(8, Long.class, "mother", false, "MOTHER");
    };

    private DaoSession daoSession;


    public CharacterDao(DaoConfig config) {
        super(config);
    }
    
    public CharacterDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CHARACTERS\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"REMOTE_ID\" INTEGER NOT NULL UNIQUE ," + // 1: remoteId
                "\"NAME\" TEXT," + // 2: name
                "\"BORN\" TEXT," + // 3: born
                "\"DIED\" TEXT," + // 4: died
                "\"HOME\" INTEGER NOT NULL ," + // 5: home
                "\"WORDS\" TEXT NOT NULL ," + // 6: words
                "\"FATHER\" INTEGER," + // 7: father
                "\"MOTHER\" INTEGER);"); // 8: mother
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CHARACTERS\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Character entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getRemoteId());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
 
        String born = entity.getBorn();
        if (born != null) {
            stmt.bindString(4, born);
        }
 
        String died = entity.getDied();
        if (died != null) {
            stmt.bindString(5, died);
        }
        stmt.bindLong(6, entity.getHome());
        stmt.bindString(7, entity.getWords());
 
        Long father = entity.getFather();
        if (father != null) {
            stmt.bindLong(8, father);
        }
 
        Long mother = entity.getMother();
        if (mother != null) {
            stmt.bindLong(9, mother);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Character entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getRemoteId());
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(3, name);
        }
 
        String born = entity.getBorn();
        if (born != null) {
            stmt.bindString(4, born);
        }
 
        String died = entity.getDied();
        if (died != null) {
            stmt.bindString(5, died);
        }
        stmt.bindLong(6, entity.getHome());
        stmt.bindString(7, entity.getWords());
 
        Long father = entity.getFather();
        if (father != null) {
            stmt.bindLong(8, father);
        }
 
        Long mother = entity.getMother();
        if (mother != null) {
            stmt.bindLong(9, mother);
        }
    }

    @Override
    protected final void attachEntity(Character entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Character readEntity(Cursor cursor, int offset) {
        Character entity = new Character( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // remoteId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // name
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // born
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // died
            cursor.getInt(offset + 5), // home
            cursor.getString(offset + 6), // words
            cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7), // father
            cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8) // mother
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Character entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setRemoteId(cursor.getLong(offset + 1));
        entity.setName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setBorn(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDied(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setHome(cursor.getInt(offset + 5));
        entity.setWords(cursor.getString(offset + 6));
        entity.setFather(cursor.isNull(offset + 7) ? null : cursor.getLong(offset + 7));
        entity.setMother(cursor.isNull(offset + 8) ? null : cursor.getLong(offset + 8));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Character entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Character entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
