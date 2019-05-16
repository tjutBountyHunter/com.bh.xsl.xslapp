package org.tjut.xsl.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.tjut.xsl.app.utils.WindonwUtil;
import org.tjut.xsl.di.component.DaggerLaucherComponent;
import org.tjut.xsl.mvp.contract.LaucherContract;
import org.tjut.xsl.mvp.presenter.LaucherPresenter;

import org.tjut.xsl.R;
import org.tjut.xsl.mvp.ui.activity.scroll.ScrollActivity;


import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


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
public class LaucherActivity extends BaseActivity<LaucherPresenter> implements LaucherContract.View {

    @BindView(R.id.tv_welcome_timer)
    AppCompatTextView mTimerText;

    @OnClick(R.id.tv_welcome_timer)
    void onClickSkip() {
        if (mPresenter != null) {
            mPresenter.checkState();
        }
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerLaucherComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_laucher; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        WindonwUtil.setFullScreen(this);
        if (savedInstanceState != null) {
            Integer count = savedInstanceState.getInt("count");
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void onTimer(String msg) {
        runOnUiThread(() -> mTimerText.setText(msg));
    }

    @Override
    public void onShowScroll() {
        Intent intent = new Intent(this, ScrollActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        launchActivity(intent);
    }

    @Override
    public void onNotSignIn() {
        Intent intent = new Intent(this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        launchActivity(intent);
    }

    @Override
    public void onSignIn() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        launchActivity(intent);
    }
}
