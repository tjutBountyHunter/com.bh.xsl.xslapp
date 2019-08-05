package org.tjut.xsl.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import org.tjut.xsl.app.AccountManager;
import org.tjut.xsl.database.DatabaseManager;
import org.tjut.xsl.mvp.contract.UserCenterContract;
import org.tjut.xsl.mvp.model.api.service.UserService;
import org.tjut.xsl.mvp.model.entity.BaseResponse;
import org.tjut.xsl.mvp.model.entity.User;
import org.tjut.xsl.mvp.model.entity.UserDao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/26/2019 17:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class UserCenterModel extends BaseModel implements UserCenterContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public UserCenterModel(IRepositoryManager repositoryManager) {
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
    public Observable<User> getUserLevel(User userLocal) {
        return mRepositoryManager.obtainRetrofitService(UserService.class)
                .getUser(userLocal.getUserid())
                .map(new ServerResponseFunc<>())
                .map(user -> {
                    userLocal.setMasterlevel(user.getMasterlevel());
                    userLocal.setHunterlevel(user.getHunterlevel());
                    userLocal.setMasterEmpirical(user.getMasterEmpirical());
                    userLocal.setHunterEmpirical(user.getHunterEmpirical());
                    UserDao userDao = DatabaseManager.getInstance().getUserDao();
                    userDao.update(userLocal);
                    return userLocal;
                });
    }

    @Override
    public Observable<String> updataTx(String userId, File file) {
        return Observable.just(file)
                .map(file1 -> {
                    File newFile = Luban.with(mApplication).load(file1).get().get(0);
                    List<MultipartBody.Part> parts = new ArrayList<>();
                    final RequestBody requestBody =
                            RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()), newFile);
                    final MultipartBody.Part body =
                            MultipartBody.Part.createFormData("uploadFile", newFile.getName(), requestBody);
                    parts.add(body);

                    MultipartBody.Part bodyExtr;
                    bodyExtr = MultipartBody.Part.createFormData("userid", AccountManager.getUserId());
                    parts.add(bodyExtr);
                    return parts;
                })
                .flatMap((Function<List<MultipartBody.Part>, ObservableSource<BaseResponse<String>>>)
                        parts -> mRepositoryManager.obtainRetrofitService(UserService.class)
                                .upload(parts))
                .map(new ServerResponseFunc<>())
                .map(s -> {
                    UserDao userDao = DatabaseManager.getInstance().getUserDao();
                    User user = userDao.load(AccountManager.getUserId());
                    if (user != null) {
                        user.setTxUrl(s);
                        userDao.update(user);
                        AccountManager.setTxUrl(s);
                    }
                    return s;
                });
    }
}