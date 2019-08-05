package org.tjut.xsl.mvp.model;

import android.app.Application;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import org.tjut.xsl.database.DatabaseManager;
import org.tjut.xsl.mvp.contract.AddTaskContract;
import org.tjut.xsl.mvp.model.api.service.TaskService;
import org.tjut.xsl.mvp.model.entity.Tag;
import org.tjut.xsl.mvp.model.entity.TagDao;
import org.tjut.xsl.mvp.model.entity.Task;
import org.tjut.xsl.mvp.model.entity.TaskAndTag;
import org.tjut.xsl.mvp.model.entity.TaskAndTagDao;
import org.tjut.xsl.mvp.model.entity.UserAndTag;
import org.tjut.xsl.mvp.model.entity.UserAndTagDao;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/26/2019 10:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class AddTaskModel extends BaseModel implements AddTaskContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public AddTaskModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<String> upLoadTask(Task task) {
        return mRepositoryManager.obtainRetrofitService(TaskService.class)
                .upLoadTask(RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), JSON.toJSONString(task)))
                .map(new ServerResponseFunc<>());
    }

    @Override
    public Observable<List<String>> saveTaskTags(List<Tag> tags, String local_current_task) {
        return Observable.just(tags)
                .map(tags1 -> {
                    TagDao tagDao = DatabaseManager.getInstance().getTagDao();
                    tagDao.insertOrReplaceInTx(tags1);
                    TaskAndTagDao taskAndTagDao = DatabaseManager.getInstance().getTaskAndTagDao();
                    TaskAndTag taskAndTag;
                    ArrayList<String> tagNames = new ArrayList<>();
                    taskAndTagDao.queryBuilder().where(TaskAndTagDao.Properties.TaskId.eq(local_current_task))
                            .buildDelete()
                            .executeDeleteWithoutDetachingEntities();
                    for (Tag tag :
                            tags1) {
                        taskAndTag = new TaskAndTag();
                        taskAndTag.setTaskId(local_current_task);
                        taskAndTag.setTagid(tag.getTagid());
                        taskAndTagDao.insertOrReplace(taskAndTag);
                        tagNames.add(tag.getTagName());
                    }
                    return tagNames;
                });
    }
}