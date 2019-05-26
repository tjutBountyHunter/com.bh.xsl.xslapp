package org.tjut.xsl.mvp.model.api.service;

import org.tjut.xsl.mvp.model.api.Api;
import org.tjut.xsl.mvp.model.entity.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 项目名：   com.bh.xsl.xslapp
 * 包名：     org.tjut.xsl.mvp.model.api.service
 * 文件名：   ChangePasswordService
 * 创建者：   PaulZhang
 * 创建时间： 2019/5/26 0:23
 * 描述：     TODO
 */
public interface ChangePasswordService {

    @GET(Api.REGIST_CODE)
    Observable<BaseResponse<String>> getCode(@Query("phone") String phone);

    @FormUrlEncoded
    @POST(Api.REGIST_CHECK_CODE)
    Observable<BaseResponse> getCheckCode(@Field("phone") String phone, @Field("code") String code);

    @FormUrlEncoded
    @POST(Api.CHANGE_PASSWORD)
    Observable<BaseResponse> changePassword(@Field("phone") String phone, @Field("password") String psd);
}
