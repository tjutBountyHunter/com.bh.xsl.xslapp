package org.tjut.xsl.mvp.model.api.service;

import org.tjut.xsl.mvp.model.api.Api;
import org.tjut.xsl.mvp.model.entity.BaseResponse;
import org.tjut.xsl.mvp.model.entity.School;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SchoolService {

    @GET(Api.REQUEST_SCHOOLS)
    Observable<BaseResponse<Schools>> getSchools();

    @FormUrlEncoded
    @POST(Api.REQUEST_COLLEGE)
    Observable<BaseResponse<Colleges>> getColleges(@Field("schoolid") Long schoolId);

    @FormUrlEncoded
    @POST(Api.REQUEST_MAJOR)
    Observable<BaseResponse<Majors>> getMajors(@Field("collegeid") Long collegeId);

    class Schools {
        String version;
        String schools;

        public String getSchools() {
            return schools;
        }
    }

    class Colleges {
        String version;
        String colleges;

        public String getColleges() {
            return colleges;
        }
    }

    class Majors {
        String version;
        String majors;

        public String getMajors() {
            return majors;
        }
    }
}
