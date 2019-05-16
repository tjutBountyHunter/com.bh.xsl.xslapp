package org.tjut.xsl.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import org.tjut.xsl.di.component.DaggerSignInComponent;
import org.tjut.xsl.mvp.contract.SignInContract;
import org.tjut.xsl.mvp.presenter.SignInPresenter;

import org.tjut.xsl.R;


import javax.inject.Inject;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/09/2019 12:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SignInActivity extends BaseActivity<SignInPresenter> implements SignInContract.View, View.OnClickListener, TextWatcher {


    private QMUITipDialog mTipDialog;


    @BindView(R.id.topbar_sign_in)
    QMUITopBar mTopbar;
    @BindView(R.id.et_sign_in_phone)
    EditText mPhoneEditText;
    @BindView(R.id.et_sign_in_password)
    EditText mPasswdEditText;
    @BindView(R.id.bt_action_sign_in)
    QMUIRoundButton mSignInButton;
    @BindView(R.id.bt_action_sign_up_link)
    QMUIRoundButton mSignUpButton;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSignInComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_sign_in; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initTopBar();
        initTipDialog();
        setListenter();
    }

    private void initTipDialog() {
        mTipDialog = new QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在登录")
                .create();
    }

    private void setListenter() {
        mSignInButton.setOnClickListener(this);
        mSignUpButton.setOnClickListener(this);
        mPhoneEditText.addTextChangedListener(this);
        mPasswdEditText.addTextChangedListener(this);
    }

    private void initTopBar() {
        mTopbar.setTitle("登录");
    }

    @Override
    public void showLoading() {
        mTipDialog.show();
    }

    @Override
    public void hideLoading() {
        mTipDialog.dismiss();
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_action_sign_in:
                if (mPresenter != null) {
                    mPresenter.requestSignIn(mPhoneEditText.getText().toString().trim(),
                            mPasswdEditText.getText().toString().trim(),
                            JPushInterface.getRegistrationID(this));
                }
                break;
            case R.id.bt_action_sign_up_link:
                launchActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void showSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        launchActivity(intent);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        setSignInButtonEnable(mPhoneEditText.getText().toString().trim(), mPasswdEditText.getText().toString().trim());
    }

    private void setSignInButtonEnable(String phone, String password) {
        if (phone == null || phone.length() < 11 || password == null || password.length() < 6) {
            mSignInButton.setEnabled(false);
        } else {
            mSignInButton.setEnabled(true);
        }
    }
}
