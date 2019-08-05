package org.tjut.xsl.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import org.tjut.xsl.app.AccountManager;
import org.tjut.xsl.app.DataNullException;
import org.tjut.xsl.database.DatabaseManager;
import org.tjut.xsl.mvp.contract.UserInfoContract;
import org.tjut.xsl.mvp.model.api.service.UserService;
import org.tjut.xsl.mvp.model.entity.BaseResponse;
import org.tjut.xsl.mvp.model.entity.User;
import org.tjut.xsl.mvp.model.entity.UserDao;

import java.util.WeakHashMap;

import io.reactivex.Observable;
import io.reactivex.functions.Function;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/26/2019 18:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class UserInfoModel extends BaseModel implements UserInfoContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public UserInfoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<User> getUser(String userId) {
        return Observable.just(userId)
                .map(s -> {
                    UserDao userDao = DatabaseManager.getInstance().getUserDao();
                    return userDao.load(userId);
                });
    }

    @Override
    public Observable<String> updataInfo(WeakHashMap<String, Object> parmes) {
        return mRepositoryManager.obtainRetrofitService(UserService.class)
                .updataUserInfo(parmes)
                .map(stringBaseResponse -> {
                    if (stringBaseResponse.isSuccess()){
                        UserDao userDao = DatabaseManager.getInstance().getUserDao();
                        User user = userDao.load(AccountManager.getUserId());
                        if (parmes.containsKey("name")) {
                            user.setName((String) parmes.get("name"));
                        }
                        if (parmes.containsKey("password")) {
                            user.setPassword((String) parmes.get("password"));
                            AccountManager.setPassword((String) parmes.get("password"));
                        }
                        userDao.update(user);
                        return "ok";
                    }else {
                        throw  new DataNullException(stringBaseResponse.getCode(),stringBaseResponse.getMsg());
                    }
                });
    }

    @Override
    public Observable<String> signOut(String phone) {
        return mRepositoryManager.obtainRetrofitService(UserService.class)
                .signOut(phone)
                .map(new ServerResponseFunc<>())
                .map(s -> {
                    UserDao userDao = DatabaseManager.getInstance().getUserDao();
                    userDao.queryBuilder().where(UserDao.Properties.Phone.eq(phone))
                            .buildDelete()
                            .executeDeleteWithoutDetachingEntities();
                    return s;
                });

    }

    @Override
    public Observable<User> getUserInfoLoacal(String userId) {
        return null;
    }
}