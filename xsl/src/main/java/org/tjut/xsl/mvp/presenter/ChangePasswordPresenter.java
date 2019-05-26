package org.tjut.xsl.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.RxLifecycleUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import timber.log.Timber;

import javax.inject.Inject;

import org.tjut.xsl.mvp.contract.ChangePasswordContract;
import org.tjut.xsl.mvp.model.entity.BaseResponse;

import java.text.MessageFormat;
import java.util.Timer;
import java.util.TimerTask;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/25/2019 20:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class ChangePasswordPresenter extends BasePresenter<ChangePasswordContract.Model, ChangePasswordContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private Timer mTimer = null;
    private Integer mCount = 60;

    @Inject
    public ChangePasswordPresenter(ChangePasswordContract.Model model, ChangePasswordContract.View rootView) {
        super(model, rootView);
    }

    public void getCode(String phone) {
        mTimer = new Timer();
        final ChangePasswordPresenter.BaseTimerTask task = new ChangePasswordPresenter.BaseTimerTask();
        mTimer.schedule(task, 0, 1000);

        mModel.getCode(phone)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mRootView.showLoading())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<String>(mErrorHandler) {
                    @Override
                    public void onNext(String string) {
                        mRootView.showMessage(string);
                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        mRootView.showMessage("请重试");
                    }
                });
    }

    public void requestConfirmCode(String phone, String code) {
        mModel.requestConfirmCode(phone, code)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mRootView.showLoading())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        if (baseResponse.getCode() == 200 && baseResponse.getOk()) {
                            mRootView.onNextStep();
                        } else {
                            mRootView.showMessage(baseResponse.getMsg());
                        }
                    }
                });

    }

    //TODO 待测试
    public void updatePassword(String phone, String password) {
        mModel.updatePassword(phone, password)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mRootView.showLoading())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseResponse>(mErrorHandler) {
                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        mRootView.showMessage(baseResponse.getData().toString());
                        if (baseResponse.getCode() == 200 && baseResponse.getOk()) {
                            mRootView.showMessage("密码重置成功，请使用新密码重新登陆");
                        } else {
                            mRootView.showMessage(baseResponse.getMsg());
                        }
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
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    public class BaseTimerTask extends TimerTask {
        @Override
        public void run() {
            if (mCount >= 0) {
                mRootView.onTimer(MessageFormat.format("重新获取{0}s", mCount--));
            } else {
                if (mTimer != null) {
                    mTimer.cancel();
                    mTimer = null;
                }
                mRootView.onTimerComplete();
            }
        }
    }
}
