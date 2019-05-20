package org.tjut.xsl.mvp.model;

import android.app.Application;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import org.tjut.xsl.app.AccountManager;
import org.tjut.xsl.database.DatabaseManager;
import org.tjut.xsl.mvp.contract.SchoolActivityContract;
import org.tjut.xsl.mvp.model.api.service.SchoolService;
import org.tjut.xsl.mvp.model.entity.College;
import org.tjut.xsl.mvp.model.entity.Major;
import org.tjut.xsl.mvp.model.entity.School;
import org.tjut.xsl.mvp.model.entity.User;
import org.tjut.xsl.mvp.model.entity.UserDao;
import org.tjut.xsl.mvp.ui.activity.SchoolActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/19/2019 15:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SchoolActivityModel extends BaseModel implements SchoolActivityContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    private static List<School> SCHOOLS = new ArrayList<>();
    private static List<College> COLLEGES = new ArrayList<>();
    private static List<Major> MAJORS = new ArrayList<>();

    @Inject
    public SchoolActivityModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<List<String>> getSchoolsRq() {
        List<String> names = new ArrayList<>();
        if (SCHOOLS != null) {
            if (!SCHOOLS.isEmpty()) {
                return Observable.fromIterable(SCHOOLS)
                        .map(school -> {
                            names.add(school.getSchoolname());
                            return names;
                        });
            } else {
                SCHOOLS.clear();
            }
        }
        return mRepositoryManager.obtainRetrofitService(SchoolService.class)
                .getSchools()
                .map(new ServerResponseFunc<SchoolService.Schools>())
                .flatMap((Function<SchoolService.Schools, ObservableSource<School>>)
                        schools -> Observable.fromIterable(JSON.parseArray(schools.getSchools(), School.class)))
                .map(school -> {
                    SCHOOLS.add(school);
                    names.add(school.getSchoolname());
                    return names;
                });

    }

    @Override
    public Observable<List<String>> getCollegesRq(Long SchoolId) {
        List<String> names = new ArrayList<>();
        if (COLLEGES != null) {
            if (!COLLEGES.isEmpty()) {
                return Observable.fromIterable(COLLEGES)
                        .map(college -> {
                            names.add(college.getCollegename());
                            return names;
                        });
            } else {
                COLLEGES.clear();
            }
        }
        return mRepositoryManager.obtainRetrofitService(SchoolService.class)
                .getColleges(SchoolId)
                .map(new ServerResponseFunc<SchoolService.Colleges>())
                .flatMap((Function<SchoolService.Colleges, ObservableSource<College>>)
                        colleges -> Observable.fromIterable(JSON.parseArray(colleges.getColleges(), College.class)))
                .map(college -> {
                    COLLEGES.add(college);
                    names.add(college.getCollegename());
                    return names;
                });
    }

    @Override
    public Observable<List<String>> getMajorsRq(Long collegeId) {
        List<String> names = new ArrayList<>();
        if (MAJORS != null) {
            if (!MAJORS.isEmpty()) {
                return Observable.fromIterable(MAJORS)
                        .map(major -> {
                            names.add(major.getMajorname());
                            return names;
                        });
            } else {
                MAJORS.clear();
            }
        }
        return mRepositoryManager.obtainRetrofitService(SchoolService.class)
                .getMajors(collegeId)
                .map(new ServerResponseFunc<SchoolService.Majors>())
                .flatMap((Function<SchoolService.Majors, ObservableSource<Major>>)
                        majors -> Observable.fromIterable(JSON.parseArray(majors.getMajors(), Major.class)))
                .map(major -> {
                    MAJORS.add(major);
                    names.add(major.getMajorname());
                    return names;
                });
    }

    @Override
    public Observable<Boolean> setUserSchool(String parm) {
        return Observable.fromIterable(SCHOOLS)
                .filter(school -> parm.equals(school.getSchoolname()))
                .map(school -> {
                    UserDao userDao = DatabaseManager.getInstance().getUserDao();
                    User user = userDao.load(AccountManager.getUserId());
                    user.setSchool(school.getSchoolname());
                    userDao.update(user);
                    AccountManager.setSchoolId(school.getId());
                    AccountManager.setSchoolName(school.getSchoolname());
                    return true;
                });
    }

    @Override
    public Observable<Boolean> setUserMajor(String parm) {
        return Observable.fromIterable(MAJORS)
                .filter(major -> parm.equals(major.getMajorname()))
                .map(major -> {
                    UserDao userDao = DatabaseManager.getInstance().getUserDao();
                    User user = userDao.load(AccountManager.getUserId());
                    user.setMajor(major.getMajorname());
                    userDao.update(user);
                    AccountManager.setMajorId(major.getId());
                    AccountManager.setMajorName(major.getMajorname());
                    return true;
                });
    }

    @Override
    public Observable<Boolean> setUserCollege(String parm) {
        return Observable.fromIterable(COLLEGES)
                .filter(college -> parm.equals(college.getCollegename()))
                .map(college -> {
                    UserDao userDao = DatabaseManager.getInstance().getUserDao();
                    User user = userDao.load(AccountManager.getUserId());
                    user.setCollege(college.getCollegename());
                    userDao.update(user);
                    AccountManager.setCollegeId(college.getId());
                    AccountManager.setCollegeName(college.getCollegename());
                    return true;
                });
    }

    @Override
    public Observable<List<String>> matchSearchText(String mseachText, int searchType) {
        List<String> names = new ArrayList<>();
        Observable<List<String>> observable = null;
        if (searchType == SchoolActivity.REQUEST_TYPE_SCHOOL) {
            observable = Observable.fromIterable(SCHOOLS)
                    .filter(school -> school.getSchoolname().contains(mseachText))
                    .map(school -> {
                        names.add(school.getSchoolname());
                        return names;
                    });
        } else if (searchType == SchoolActivity.REQUEST_TYPE_COLLEGE) {
            observable = Observable.fromIterable(COLLEGES)
                    .filter(college -> college.getCollegename().contains(mseachText))
                    .map(college -> {
                        names.add(college.getCollegename());
                        return names;
                    });
        } else if (searchType == SchoolActivity.REQUEST_TYPE_MAJOR) {
            observable = Observable.fromIterable(MAJORS)
                    .filter(major -> major.getMajorname().contains(mseachText))
                    .map(major -> {
                        names.add(major.getMajorname());
                        return names;
                    });
        }
        return observable;
    }
}