package org.tjut.xsl.mvp.model.api.service;

import org.tjut.xsl.mvp.model.api.Api;
import org.tjut.xsl.mvp.model.entity.BaseResponse;
import org.tjut.xsl.mvp.model.entity.Tag;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface TagService {

    @POST(Api.TASK_TAG)
    Observable<BaseResponse<List<Tag>>> getTags(@Body RequestBody body);
}
