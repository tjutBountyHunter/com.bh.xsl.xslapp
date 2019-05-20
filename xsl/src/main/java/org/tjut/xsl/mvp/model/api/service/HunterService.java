package org.tjut.xsl.mvp.model.api.service;

import org.tjut.xsl.mvp.model.api.Api;
import org.tjut.xsl.mvp.model.entity.BaseResponse;
import org.tjut.xsl.mvp.model.entity.Hunter;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface HunterService {

    @FormUrlEncoded
    @POST(Api.MY_HISTORY_HUNTER)
    Observable<BaseResponse<List<Hunter>>> getHistoryHunter(@Field("masterId") String phone, @Field("size") Integer size);
}
