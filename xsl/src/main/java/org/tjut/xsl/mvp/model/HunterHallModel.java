package org.tjut.xsl.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import org.tjut.xsl.mvp.contract.HunterHallContract;
import org.tjut.xsl.mvp.model.api.service.HunterService;
import org.tjut.xsl.mvp.model.entity.Hunter;

import java.util.List;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/19/2019 12:27
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class HunterHallModel extends BaseModel implements HunterHallContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public HunterHallModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<List<Hunter>> getHistoryHunterRq(String masterId, int size) {
        return mRepositoryManager.obtainRetrofitService(HunterService.class)
                .getHistoryHunter(masterId, size)
                .map(new ServerResponseFunc<>());
    }

    @Override
    public Observable<List<Hunter>> getHotHunterRq(String masterId, int i) {
        return mRepositoryManager.obtainRetrofitService(HunterService.class)
                .getHotHunter(masterId, i)
                .map(new ServerResponseFunc<>());
    }
}