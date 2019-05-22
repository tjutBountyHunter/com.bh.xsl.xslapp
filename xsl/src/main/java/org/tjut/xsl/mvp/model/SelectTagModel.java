package org.tjut.xsl.mvp.model;

import android.app.Application;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import org.tjut.xsl.mvp.contract.SelectTagContract;
import org.tjut.xsl.mvp.model.api.service.TagService;
import org.tjut.xsl.mvp.model.entity.BaseResponse;
import org.tjut.xsl.mvp.model.entity.Tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/20/2019 03:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SelectTagModel extends BaseModel implements SelectTagContract.Model {

    private static final List<Tag> TAG_BEANS = new ArrayList<>();

    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public SelectTagModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<List<Tag>> getTags(int size, boolean isInit) {
        List<String> tagNames = new ArrayList<>();

        return Observable.just(TAG_BEANS)
                .map(tags -> {
                    if (isInit)
                        tags.clear();
                    return tags;
                })
                .map(tags -> {
                    for (Tag tag : tags) {
                        tagNames.add(tag.getTagid());
                    }
                    return tagNames;
                })
                .flatMap((Function<List<String>, ObservableSource<BaseResponse<List<Tag>>>>) strings -> {
                    JSONObject jsonData = new JSONObject();
                    jsonData.put("tagNum", size);
                    jsonData.put("obtainedTags", strings);
                    return mRepositoryManager.obtainRetrofitService(TagService.class)
                            .getTags(RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), jsonData.toJSONString()));
                })
                .map(new ServerResponseFunc<List<Tag>>())
                .map(tags -> {
                    TAG_BEANS.addAll(tags);
                    return tags;
                });
    }
}