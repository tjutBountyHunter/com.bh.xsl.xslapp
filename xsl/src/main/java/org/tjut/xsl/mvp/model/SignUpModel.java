package org.tjut.xsl.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import org.tjut.xsl.database.DatabaseManager;
import org.tjut.xsl.mvp.contract.SignUpContract;
import org.tjut.xsl.mvp.model.api.service.SignInService;
import org.tjut.xsl.mvp.model.api.service.SignUpService;
import org.tjut.xsl.mvp.model.entity.BaseResponse;
import org.tjut.xsl.mvp.model.entity.User;
import org.tjut.xsl.mvp.model.entity.UserDao;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/09/2019 13:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SignUpModel extends BaseModel implements SignUpContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SignUpModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseResponse> getCode(String phone) {
        return mRepositoryManager.obtainRetrofitService(SignUpService.class)
                .getCode(phone);
    }

    @Override
    public Observable<BaseResponse> requestConfirmCode(String phone, String code) {
        return mRepositoryManager
                .obtainRetrofitService(SignUpService.class)
                .getCheckCode(phone, code);
    }

    @Override
    public Observable<User> getUser(String phone, String passwd, String token) {
        return mRepositoryManager
                .obtainRetrofitService(SignUpService.class)
                .getUser(phone, passwd, token)
                .map(new ServerResponseFunc<>(User.class))
                .map(user -> {
                    UserDao userDao = DatabaseManager.getInstance().getUserDao();
                    userDao.insertOrReplace(user);
                    return user;
                });
    }
}