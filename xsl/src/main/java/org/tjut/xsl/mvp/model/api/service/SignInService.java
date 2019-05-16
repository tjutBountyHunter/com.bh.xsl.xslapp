package org.tjut.xsl.mvp.model.api.service;


import org.tjut.xsl.mvp.model.api.Api;
import org.tjut.xsl.mvp.model.entity.BaseResponse;
import org.tjut.xsl.mvp.model.entity.User;


import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SignInService {

    @FormUrlEncoded
    @POST(Api.LOGIN)
    Observable<BaseResponse> getUser(@Field("phone") String phone, @Field("password") String passwd, @Field("token") String token);

}
