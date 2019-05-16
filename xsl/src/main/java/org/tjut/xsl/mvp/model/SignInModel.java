package org.tjut.xsl.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import org.tjut.xsl.app.ServerException;
import org.tjut.xsl.database.DatabaseManager;
import org.tjut.xsl.mvp.contract.SignInContract;
import org.tjut.xsl.mvp.model.api.Api;
import org.tjut.xsl.mvp.model.api.service.SignInService;
import org.tjut.xsl.mvp.model.entity.BaseResponse;
import org.tjut.xsl.mvp.model.entity.User;
import org.tjut.xsl.mvp.model.entity.UserDao;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.ActionsList;
import timber.log.Timber;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/09/2019 12:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SignInModel extends BaseModel implements SignInContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SignInModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<User> getUser(String phone, String passwd, String token) {
        return mRepositoryManager.obtainRetrofitService(SignInService.class)
                .getUser(phone, passwd, token)
                .map(new ServerResponseFunc<>(User.class))
                .map(user -> {
                    UserDao userDao = DatabaseManager.getInstance().getUserDao();
                    userDao.insertOrReplace(user);
                    return user;
                });
    }
}