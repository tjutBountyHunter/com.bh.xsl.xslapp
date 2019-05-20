package org.tjut.xsl.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.RxLifecycleUtils;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import timber.log.Timber;

import javax.inject.Inject;

import org.tjut.xsl.app.AccountManager;
import org.tjut.xsl.app.ServerException;
import org.tjut.xsl.mvp.contract.SignUpContract;
import org.tjut.xsl.mvp.model.entity.BaseResponse;
import org.tjut.xsl.mvp.model.entity.User;

import java.text.MessageFormat;
import java.util.Timer;
import java.util.TimerTask;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/09/2019 13:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class SignUpPresenter extends BasePresenter<SignUpContract.Model, SignUpContract.View> {
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
    public SignUpPresenter(SignUpContract.Model model, SignUpContract.View rootView) {
        super(model, rootView);
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

    public void getCode(String phone) {
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask();
        mTimer.schedule(task, 0, 1000);

        mModel.getCode(phone)
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
                    }
                });
    }

    public void requestSignUpAndSignIn(String phone, String passwd, String code) {
        mModel.requestConfirmCode(phone, code)
                .flatMap((Function<BaseResponse, ObservableSource<User>>) baseResponse -> {
                    if (!baseResponse.isSuccess()) {
                        throw new ServerException(baseResponse.getCode(), baseResponse.getMsg());
                    }
                    return mModel.getUser(phone, passwd, JPushInterface.getRegistrationID(mApplication));
                })
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mRootView.showLoading())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<User>(mErrorHandler) {
                    @Override
                    public void onNext(User user) {
                        AccountManager.setSignState(user, true);
                        mRootView.showMessage("登陆成功");
                        mRootView.showSuccess();
                    }
                });

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
                mRootView.onTimerComplet();
            }
        }
    }

}
