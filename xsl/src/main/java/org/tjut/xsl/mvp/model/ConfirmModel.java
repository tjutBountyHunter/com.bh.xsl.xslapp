package org.tjut.xsl.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import org.tjut.xsl.app.AccountManager;
import org.tjut.xsl.database.DatabaseManager;
import org.tjut.xsl.mvp.contract.ConfirmContract;
import org.tjut.xsl.mvp.model.entity.User;
import org.tjut.xsl.mvp.model.entity.UserDao;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/20/2019 02:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ConfirmModel extends BaseModel implements ConfirmContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ConfirmModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<Boolean> setUserSex(String item) {
        return Observable.just(item)
                .map(s -> {
                    UserDao userDao = DatabaseManager.getInstance().getUserDao();
                    User user = userDao.load(AccountManager.getUserId());
                    user.setSex(item);
                    userDao.update(user);
                    AccountManager.setSex(item);
                    return true;
                });
    }

    @Override
    public Observable<Boolean> setUserSno(String trim) {

        return Observable.just(trim)
                .map(s -> {
                    UserDao userDao = DatabaseManager.getInstance().getUserDao();
                    User user = userDao.load(AccountManager.getUserId());
                    user.setSno(trim);
                    userDao.update(user);
                    AccountManager.setSno(trim);
                    return true;
                });
    }

    @Override
    public Observable<User> getUserInfo(String userId) {
        return Observable.just(userId)
                .map(s -> {
                    UserDao userDao = DatabaseManager.getInstance().getUserDao();
                    return userDao.load(AccountManager.getUserId());
                });
    }
}