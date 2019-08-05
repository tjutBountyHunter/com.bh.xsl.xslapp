package org.tjut.xsl.mvp.model.api.service;

import org.tjut.xsl.mvp.model.api.Api;
import org.tjut.xsl.mvp.model.entity.BaseResponse;
import org.tjut.xsl.mvp.model.entity.Hunter;
import org.tjut.xsl.mvp.model.entity.Task;
import org.tjut.xsl.mvp.model.entity.TaskDetail;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface TaskService {

    @POST(Api.RELEASE_TASK)
    Observable<BaseResponse<String>> upLoadTask(@Body RequestBody body);

    @FormUrlEncoded
    @POST(Api.TASK_DETAIL)
    Observable<BaseResponse<TaskDetail>> getTask(@Field("taskId") String taskId);

    @FormUrlEncoded
    @POST(Api.TASK_ALLAREAD)
    Observable<BaseResponse<List<Task>>> getSendTask(@Field("masterid") String masterId, @Field("page") int page, @Field("rows") int rows);

    @FormUrlEncoded
    @POST(Api.TASK_ALLRECIVE)
    Observable<BaseResponse<List<Task>>> getReciveTask(@Field("hunterid") String hunterId, @Field("page") int page, @Field("rows") int rows);

    @FormUrlEncoded
    @POST(Api.TASK_RECIVE)
    Observable<BaseResponse<String>> reciveTask(@Field("taskId") String taskId, @Field("hunterid") String hunterId);

    @FormUrlEncoded
    @POST(Api.CANCEL_TASK)
    Observable<BaseResponse<String>> cancelTask(@Field("taskId") String taskId);

    @FormUrlEncoded
    @POST(Api.HUNTER_COMFIRME_COMPLET)
    Observable<BaseResponse<Hunter>> hunterComplet(@Field("taskId") String taskId, @Field("hunterid") String hunterId);

    @FormUrlEncoded
    @POST(Api.MATER_COMPLET)
    Observable<BaseResponse<Hunter>> MasterComplet(@Field("taskId") String taskId, @Field("hunterid") String hunterId);
}
