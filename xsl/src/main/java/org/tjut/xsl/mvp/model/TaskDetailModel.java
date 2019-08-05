package org.tjut.xsl.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import org.tjut.xsl.mvp.contract.TaskDetailContract;
import org.tjut.xsl.mvp.model.api.service.TaskService;
import org.tjut.xsl.mvp.model.entity.Hunter;
import org.tjut.xsl.mvp.model.entity.TaskDetail;

import io.reactivex.Observable;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/26/2019 16:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class TaskDetailModel extends BaseModel implements TaskDetailContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public TaskDetailModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<TaskDetail> getTask(String taskId) {
        return mRepositoryManager.obtainRetrofitService(TaskService.class)
                .getTask(taskId)
                .map(new ServerResponseFunc<>());
    }

    @Override
    public Observable<String> reciveTask(String taskId, String hunterId) {
        return mRepositoryManager.obtainRetrofitService(TaskService.class)
                .reciveTask(taskId, hunterId)
                .map(new ServerResponseFunc<>());
    }

    @Override
    public Observable<String> cancelTask(String taskId) {
        return mRepositoryManager.obtainRetrofitService(TaskService.class)
                .cancelTask(taskId)
                .map(new ServerResponseFunc<>());
    }

    @Override
    public Observable<Hunter> hunterComplet(String taskId, String hunterId) {
        return mRepositoryManager.obtainRetrofitService(TaskService.class)
                .hunterComplet(taskId, hunterId)
                .map(new ServerResponseFunc<>());
    }

    @Override
    public Observable<Hunter> MasterComplet(String taskId, String hunterId) {
        return mRepositoryManager.obtainRetrofitService(TaskService.class)
                .MasterComplet(taskId, hunterId)
                .map(new ServerResponseFunc<>());
    }
}