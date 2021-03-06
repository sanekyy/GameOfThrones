package com.example.ihb.aleksandryurkovskiy.data.storage.models;

import java.util.List;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ALIASES".
*/
public class AliasDao extends AbstractDao<Alias, Long> {

    public static final String TABLENAME = "ALIASES";

    /**
     * Properties of entity Alias.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property CharacterRemoteId = new Property(1, Long.class, "characterRemoteId", false, "CHARACTER_REMOTE_ID");
        public final static Property Alias = new Property(2, String.class, "alias", false, "ALIAS");
    };

    private DaoSession daoSession;

    private Query<Alias> character_AliasesQuery;

    public AliasDao(DaoConfig config) {
        super(config);
    }
    
    public AliasDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ALIASES\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"CHARACTER_REMOTE_ID\" INTEGER," + // 1: characterRemoteId
                "\"ALIAS\" TEXT);"); // 2: alias
        // Add Indexes
        db.execSQL("CREATE UNIQUE INDEX " + constraint + "IDX_ALIASES_CHARACTER_REMOTE_ID_ALIAS ON ALIASES" +
                " (\"CHARACTER_REMOTE_ID\" ASC,\"ALIAS\" ASC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ALIASES\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Alias entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long characterRemoteId = entity.getCharacterRemoteId();
        if (characterRemoteId != null) {
            stmt.bindLong(2, characterRemoteId);
        }
 
        String alias = entity.getAlias();
        if (alias != null) {
            stmt.bindString(3, alias);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Alias entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        Long characterRemoteId = entity.getCharacterRemoteId();
        if (characterRemoteId != null) {
            stmt.bindLong(2, characterRemoteId);
        }
 
        String alias = entity.getAlias();
        if (alias != null) {
            stmt.bindString(3, alias);
        }
    }

    @Override
    protected final void attachEntity(Alias entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Alias readEntity(Cursor cursor, int offset) {
        Alias entity = new Alias( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // characterRemoteId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2) // alias
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Alias entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCharacterRemoteId(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
        entity.setAlias(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Alias entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Alias entity) {
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
    
    /** Internal query to resolve the "aliases" to-many relationship of Character. */
    public List<Alias> _queryCharacter_Aliases(Long characterRemoteId) {
        synchronized (this) {
            if (character_AliasesQuery == null) {
                QueryBuilder<Alias> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.CharacterRemoteId.eq(null));
                character_AliasesQuery = queryBuilder.build();
            }
        }
        Query<Alias> query = character_AliasesQuery.forCurrentThread();
        query.setParameter(0, characterRemoteId);
        return query.list();
    }

}
