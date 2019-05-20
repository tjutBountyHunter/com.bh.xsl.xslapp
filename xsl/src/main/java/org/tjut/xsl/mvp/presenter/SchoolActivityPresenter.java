package org.tjut.xsl.mvp.presenter;

import android.app.Application;
import android.content.Intent;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.RxLifecycleUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import org.tjut.xsl.app.AccountManager;
import org.tjut.xsl.mvp.contract.SchoolActivityContract;
import org.tjut.xsl.mvp.model.entity.School;
import org.tjut.xsl.mvp.model.entity.User;
import org.tjut.xsl.mvp.ui.activity.SchoolActivity;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


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
public class SchoolActivityPresenter extends BasePresenter<SchoolActivityContract.Model, SchoolActivityContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private static Timer timer = null;
    private static int SEARCH_TYPE;

    private static long preTime;
    private static long currTime;
    private static String mseachText;

    @Inject
    public SchoolActivityPresenter(SchoolActivityContract.Model model, SchoolActivityContract.View rootView) {
        super(model, rootView);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                currTime = System.currentTimeMillis();
                if (currTime - preTime > 500 && currTime - preTime < 1000) {
                    matchString();
                }
            }
        }, 500, 400);
    }

    private void matchString() {
        mModel.matchSearchText(mseachText, SEARCH_TYPE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<List<String>>(mErrorHandler) {
                    @Override
                    public void onNext(List<String> strings) {
                        mRootView.showData(strings);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initSchoolsData() {
        SEARCH_TYPE = SchoolActivity.REQUEST_TYPE_SCHOOL;
        mModel.getSchoolsRq()
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<List<String>>(mErrorHandler) {
                    @Override
                    public void onNext(List<String> schools) {
                        mRootView.showData(schools);
                    }
                });
    }

    public void initCollegesData() {
        SEARCH_TYPE = SchoolActivity.REQUEST_TYPE_COLLEGE;
        mModel.getCollegesRq(AccountManager.getSchoolId())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<List<String>>(mErrorHandler) {
                    @Override
                    public void onNext(List<String> datas) {
                        mRootView.showData(datas);
                    }
                });
    }

    public void initMajorsData() {
        SEARCH_TYPE = SchoolActivity.REQUEST_TYPE_MAJOR;
        mModel.getMajorsRq(AccountManager.getCollegeId())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mRootView.showLoading();
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))//使用 Rxlifecycle,使 Disposable 和 Activity 一起销毁
                .subscribe(new ErrorHandleSubscriber<List<String>>(mErrorHandler) {
                    @Override
                    public void onNext(List<String> majors) {
                        mRootView.showData(majors);
                    }
                });
    }

    public void setUserSchoolInfo(int tag, String parm) {
        if (tag == SchoolActivity.REQUEST_TYPE_SCHOOL) {
            mModel.setUserSchool(parm)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ErrorHandleSubscriber<Boolean>(mErrorHandler) {
                        @Override
                        public void onNext(Boolean aBoolean) {

                        }
                    });
        } else if (tag == SchoolActivity.REQUEST_TYPE_COLLEGE) {
            mModel.setUserCollege(parm)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ErrorHandleSubscriber<Boolean>(mErrorHandler) {
                        @Override
                        public void onNext(Boolean aBoolean) {

                        }
                    });
        } else if (tag == SchoolActivity.REQUEST_TYPE_MAJOR) {
            mModel.setUserMajor(parm)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ErrorHandleSubscriber<Boolean>(mErrorHandler) {
                        @Override
                        public void onNext(Boolean aBoolean) {

                        }
                    });
        }
    }

    public void setSearchText(String string) {
        preTime = System.currentTimeMillis();
        mseachText = string;
    }
}
