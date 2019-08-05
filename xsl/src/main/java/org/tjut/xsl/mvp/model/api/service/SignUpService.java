package org.tjut.xsl.mvp.model.api.service;

import org.tjut.xsl.mvp.model.api.Api;
import org.tjut.xsl.mvp.model.entity.BaseResponse;
import org.tjut.xsl.mvp.model.entity.User;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SignUpService {
    @GET(Api.REGIST_CODE)
    Observable<BaseResponse<String>> getCode(@Query("phone") String phone);

    @FormUrlEncoded
    @POST(Api.REGIST_CHECK_CODE)
    Observable<BaseResponse<String>> getCheckCode(@Field("phone") String phone, @Field("code") String code);

    @FormUrlEncoded
    @POST(Api.QUICK_REGISTER)
    Observable<BaseResponse<User>> getUser(@Field("phone") String phone, @Field("password") String passwd, @Field("token") String token);
}
