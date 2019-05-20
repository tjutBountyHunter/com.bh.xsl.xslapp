package org.tjut.xsl.mvp.model.api.service;

import org.tjut.xsl.mvp.model.api.Api;
import org.tjut.xsl.mvp.model.entity.BaseResponse;
import org.tjut.xsl.mvp.model.entity.HallTaskCard;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HallTaskService {

    @FormUrlEncoded
    @POST(Api.REQUEST_TASKS_INIT)
    Observable<BaseResponse<HallTaskCard>> initTaskDataRq(@Field("userid") String userid, @Field("schoolName") String schoolName, @Field("size") Integer size);
}
