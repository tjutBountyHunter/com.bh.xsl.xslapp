package org.tjut.xsl.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;
import org.tjut.xsl.mvp.model.entity.DaoMaster;
import org.tjut.xsl.mvp.model.entity.DaoSession;
import org.tjut.xsl.mvp.model.entity.UserDao;

public class DatabaseManager {

    private DaoSession mDaoSession;
    private UserDao mUserDao;

    private DatabaseManager() {
    }

    public DatabaseManager init(Context context) {
        initDao(context);
        return this;
    }

    public static DatabaseManager getInstance() {
        return Holder.INSTANCE;
    }

    private static final class Holder {
        private static final DatabaseManager INSTANCE = new DatabaseManager();
    }

    private void initDao(Context context) {
        final ReleaseOpenHelper helper = new ReleaseOpenHelper(context, "xsl.db");
        final Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
        mUserDao = mDaoSession.getUserDao();
    }

    public UserDao getUserDao() {
        return mUserDao;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}