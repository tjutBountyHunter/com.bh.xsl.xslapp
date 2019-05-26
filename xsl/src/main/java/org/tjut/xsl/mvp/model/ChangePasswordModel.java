package org.tjut.xsl.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import org.tjut.xsl.mvp.contract.ChangePasswordContract;
import org.tjut.xsl.mvp.model.api.service.ChangePasswordService;
import org.tjut.xsl.mvp.model.entity.BaseResponse;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/25/2019 20:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ChangePasswordModel extends BaseModel implements ChangePasswordContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public ChangePasswordModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<String> getCode(String phone) {
        return mRepositoryManager
                .obtainRetrofitService(ChangePasswordService.class)
                .getCode(phone)
                .map(new ServerResponseFunc<>());
    }

    @Override
    public Observable<BaseResponse> requestConfirmCode(String phone, String code) {
        return mRepositoryManager
                .obtainRetrofitService(ChangePasswordService.class)
                .getCheckCode(phone, code);
    }

    @Override
    public Observable<BaseResponse> updatePassword(String phone, String psd) {
        return mRepositoryManager
                .obtainRetrofitService(ChangePasswordService.class)
                .changePassword(phone, psd);
    }
}