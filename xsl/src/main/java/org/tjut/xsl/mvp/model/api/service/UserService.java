package org.tjut.xsl.mvp.model.api.service;

import org.tjut.xsl.mvp.model.api.Api;
import org.tjut.xsl.mvp.model.entity.BaseResponse;
import org.tjut.xsl.mvp.model.entity.Tag;
import org.tjut.xsl.mvp.model.entity.User;

import java.util.List;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

public interface UserService {

    @POST(Api.AUTHENTICATION)
    Observable<BaseResponse<Integer>> updataUser(@Body RequestBody body);

    @FormUrlEncoded
    @POST(Api.UPDATA_INFO)
    Observable<BaseResponse<String>> updataUserInfo(@FieldMap WeakHashMap<String,Object> parmes);

    @FormUrlEncoded
    @POST(Api.SIGN_OUT)
    Observable<BaseResponse<String>> signOut(@Field("phone") String phone);

    @FormUrlEncoded
    @POST(Api.REQUEST_LEVEL)
    Observable<BaseResponse<User>> getUser(@Field("userid") String userid);

    @Multipart
    @POST(Api.UPLOAD_USER_TX)
    Observable<BaseResponse<String>> upload(@Part List<MultipartBody.Part> files);
}
