package org.tjut.xsl.database;

import android.content.Context;

import org.greenrobot.greendao.database.Database;
import org.tjut.xsl.mvp.model.entity.DaoMaster;
import org.tjut.xsl.mvp.model.entity.DaoSession;
import org.tjut.xsl.mvp.model.entity.MessageDao;
import org.tjut.xsl.mvp.model.entity.TagDao;
import org.tjut.xsl.mvp.model.entity.TaskAndTag;
import org.tjut.xsl.mvp.model.entity.TaskAndTagDao;
import org.tjut.xsl.mvp.model.entity.UserAndTag;
import org.tjut.xsl.mvp.model.entity.UserAndTagDao;
import org.tjut.xsl.mvp.model.entity.UserDao;

public class DatabaseManager {

    private DaoSession mDaoSession;
    private UserDao mUserDao;
    private TagDao mTagDao;
    private UserAndTagDao mUserAndTagDao;
    private TaskAndTagDao mTaskAndTagDao;
    private MessageDao mMessageDao;

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
        mTagDao = mDaoSession.getTagDao();
        mUserAndTagDao = mDaoSession.getUserAndTagDao();
        mTaskAndTagDao = mDaoSession.getTaskAndTagDao();
        mMessageDao = mDaoSession.getMessageDao();
    }

    public UserDao getUserDao() {
        return mUserDao;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public TagDao getTagDao() {
        return mTagDao;
    }

    public UserAndTagDao getUserAndTagDao() {
        return mUserAndTagDao;
    }

    public TaskAndTagDao getTaskAndTagDao() {
        return mTaskAndTagDao;
    }

    public MessageDao getMessageDao() {
        return mMessageDao;
    }

}