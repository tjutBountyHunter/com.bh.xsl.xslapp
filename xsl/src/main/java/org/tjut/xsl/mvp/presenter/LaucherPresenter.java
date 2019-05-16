package org.tjut.xsl.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Intent;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import timber.log.Timber;

import javax.inject.Inject;

import org.tjut.xsl.app.AccountManager;
import org.tjut.xsl.app.LaucherConfigkeys;
import org.tjut.xsl.app.TeaPreference;
import org.tjut.xsl.mvp.contract.LaucherContract;
import org.tjut.xsl.mvp.ui.activity.LaucherActivity;
import org.tjut.xsl.mvp.ui.activity.SignInActivity;
import org.tjut.xsl.mvp.ui.activity.scroll.ScrollActivity;

import java.text.MessageFormat;
import java.util.Timer;
import java.util.TimerTask;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/11/2019 02:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class LaucherPresenter extends BasePresenter<LaucherContract.Model, LaucherContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    private Timer mTimer = null;
    private Integer mCount = 5;


//    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
//    private void initTimer() {
//        mTimer = new Timer();
//        final BaseTimerTask task = new BaseTimerTask();
//        mTimer.schedule(task, 0, 1000);
//    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    private void onPasue() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private void onResume() {
        mTimer = new Timer();
        final BaseTimerTask task = new BaseTimerTask();
        mTimer.schedule(task, 0, 1000);
    }

    @Inject
    public LaucherPresenter(LaucherContract.Model model, LaucherContract.View rootView) {
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


    public class BaseTimerTask extends TimerTask {
        @Override
        public void run() {
            if (mCount >= 0) {
                mRootView.onTimer(MessageFormat.format("跳过\n{0}s", mCount--));
            } else {
                if (mTimer != null) {
                    mTimer.cancel();
                    mTimer = null;
                }
                checkState();
            }
        }
    }

    public void checkState() {
        if (mAppManager.getCurrentActivity() != null) {
            mAppManager.getCurrentActivity().runOnUiThread(() -> {
                if (!TeaPreference.getAppFlag(LaucherConfigkeys.HAS_FIRST_LAUNCHER_APP.name())) { // 显示滑动页
                    mRootView.onShowScroll();
                } else if (AccountManager.isSignIn()) {             // 已登录
                    mRootView.onSignIn();
                } else {                                    // 未登陆
                    mRootView.onNotSignIn();
                }
            });
        }
    }
}
