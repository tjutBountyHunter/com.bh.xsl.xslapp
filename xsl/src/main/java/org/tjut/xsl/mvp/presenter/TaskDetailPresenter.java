package org.tjut.xsl.mvp.presenter;

import android.app.Application;

import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.utils.RxLifecycleUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import org.tjut.xsl.app.AccountManager;
import org.tjut.xsl.app.DataNullException;
import org.tjut.xsl.mvp.contract.TaskDetailContract;
import org.tjut.xsl.mvp.model.entity.BaseResponse;
import org.tjut.xsl.mvp.model.entity.Hunter;
import org.tjut.xsl.mvp.model.entity.Task;
import org.tjut.xsl.mvp.model.entity.TaskDetail;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/26/2019 16:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class TaskDetailPresenter extends BasePresenter<TaskDetailContract.Model, TaskDetailContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;
    @Inject
    AppManager mAppManager;

    @Inject
    public TaskDetailPresenter(TaskDetailContract.Model model, TaskDetailContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void initData(String taskId) {
        mModel.getTask(taskId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mRootView.showLoading())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<TaskDetail>(mErrorHandler) {
                    @Override
                    public void onNext(TaskDetail task) {
//                        mImageLoader.loadImage(mApplication, mRootView.getTxConfig());
                        mRootView.showTask(task);
                    }
                });
    }

    public void reciveTask(String taskId) {
        if (AccountManager.getState() != AccountManager.NORMAL_STATE){
            mRootView.showMessage("你还未认证或登录状态异常");
            return;
        }

        mModel.reciveTask(taskId, AccountManager.getHunterId())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mRootView.showLoading())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<String>(mErrorHandler) {
                    @Override
                    public void onNext(String s) {
                        mRootView.showReciveComplet();
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (t instanceof DataNullException) {
                            mRootView.showReciveComplet();
                        } else {
                            super.onError(t);
                        }
                    }
                });
    }

    public void cancelTask(String taskId) {
        mModel.cancelTask(taskId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mRootView.showLoading())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<String>(mErrorHandler) {
                    @Override
                    public void onNext(String s) {
                        mRootView.showCancelTaskComplet();
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (t instanceof DataNullException) {
                            mRootView.showCancelTaskComplet();
                        } else {
                            super.onError(t);
                        }
                    }
                });
    }

    public void hunterComplet(String taskId) {
        mModel.hunterComplet(taskId, AccountManager.getHunterId())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mRootView.showLoading())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<Hunter>(mErrorHandler) {
                    @Override
                    public void onNext(Hunter s) {
                        mRootView.showHunterCompleted();
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (t instanceof DataNullException) {
                            mRootView.showHunterCompleted();
                        } else {
                            super.onError(t);
                        }
                    }
                });
    }

    public void MasterComplet(String taskId, String hunterId) {
        mModel.MasterComplet(taskId, hunterId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mRootView.showLoading())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(() -> mRootView.hideLoading())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<Hunter>(mErrorHandler) {
                    @Override
                    public void onNext(Hunter s) {
                        mRootView.showMasterComplet();
                    }

                    @Override
                    public void onError(Throwable t) {
                        if (t instanceof DataNullException) {
                            mRootView.showMasterComplet();
                        } else {
                            super.onError(t);
                        }
                    }
                });
    }

    public void loadHunterTx(ImageConfigImpl build) {
        mImageLoader.loadImage(mApplication, build);
    }

    public void loadMasterTx(ImageConfigImpl build) {
        mImageLoader.loadImage(mApplication, build);
    }
}
