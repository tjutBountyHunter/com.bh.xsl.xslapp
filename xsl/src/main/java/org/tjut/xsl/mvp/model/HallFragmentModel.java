package org.tjut.xsl.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.FragmentScope;

import javax.inject.Inject;

import org.tjut.xsl.app.AccountManager;
import org.tjut.xsl.mvp.contract.HallFragmentContract;
import org.tjut.xsl.mvp.model.api.service.HallTaskService;
import org.tjut.xsl.mvp.model.entity.HallTaskCard;
import org.tjut.xsl.mvp.model.entity.Task;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/17/2019 01:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
public class HallFragmentModel extends BaseModel implements HallFragmentContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public HallFragmentModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<List<Task>> requestInitData() {
        return mRepositoryManager.obtainRetrofitService(HallTaskService.class)
                .initTaskDataRq(AccountManager.getUserId(), AccountManager.getSchoolName(), 20)
                .map(new ServerResponseFunc<HallTaskCard>())
                .map(HallTaskCard::getTaskInfoVos);
    }
}