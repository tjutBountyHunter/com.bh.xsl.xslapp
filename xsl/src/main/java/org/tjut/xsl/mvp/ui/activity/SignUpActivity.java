package org.tjut.xsl.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import org.tjut.xsl.di.component.DaggerSignUpComponent;
import org.tjut.xsl.mvp.contract.SignUpContract;
import org.tjut.xsl.mvp.presenter.SignUpPresenter;

import org.tjut.xsl.R;


import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


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
public class SignUpActivity extends BaseActivity<SignUpPresenter> implements SignUpContract.View, TextWatcher {


    @BindView(R.id.topbar_sign_up)
    QMUITopBar topBar = null;
    @BindView(R.id.et_phone)
    EditText mPhone = null;
    @BindView(R.id.tv_ver_code)
    TextView mActionCode = null;
    @BindView(R.id.et_ver_code)
    EditText mCode = null;
    @BindView(R.id.et_password)
    EditText mPassword = null;
    @BindView(R.id.et_re_password)
    EditText mRePassword = null;
    @BindView(R.id.bt_action_next)
    QMUIRoundButton mActionNext = null;
    @BindView(R.id.tv_signup_link)
    TextView mAgreementLink = null;

    private QMUITipDialog mQMUITipDialog;

    @OnClick(R.id.tv_ver_code)
    void onGetCode() {
        if (checkPhoneFormat()) {
            if (mPresenter != null) {
                mPresenter.getCode(mPhone.getText().toString().trim());
            }
        } else {
            showMessage("手机格式不正确");
        }
    }

    @OnClick(R.id.bt_action_next)
    void onSignUpAndSignIn() {
        if (checkPasswordFormat()) {
            String phone = mPhone.getText().toString().trim();
            String passwd = mPassword.getText().toString().trim();
            String code = mCode.getText().toString().trim();
            if (mPresenter != null) {
                mPresenter.requestSignUpAndSignIn(phone, passwd, code);
            }
        } else {
            showMessage("密码输入不一致");
        }
    }

    private boolean checkPasswordFormat() {
        String passwd = mPassword.getText().toString().trim();
        String passwdRe = mRePassword.getText().toString().trim();
        return passwd.equals(passwdRe);
    }

    private boolean checkPhoneFormat() {
        int lenght = mPhone.getText().toString().trim().length();
        if (lenght != 11) {
            return false;
        }
        return true;
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSignUpComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_sign_up; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    private void initTopBar() {
        topBar.setTitle("注册");
        topBar.addLeftBackImageButton().setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initTopBar();
        initLoadingView();
        setListener();
    }

    private void setListener() {
        mPhone.addTextChangedListener(this);
        mCode.addTextChangedListener(this);
        mPassword.addTextChangedListener(this);
        mRePassword.addTextChangedListener(this);
    }

    private void initLoadingView() {
        mQMUITipDialog = new QMUITipDialog.Builder(this)
                .setTipWord("正在加载")
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .create();
    }

    @Override
    public void showLoading() {
        mQMUITipDialog.show();
    }

    @Override
    public void hideLoading() {
        mQMUITipDialog.dismiss();
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        checkSignUpButtonEnable();
    }

    private void checkSignUpButtonEnable() {
        int phoneLength = mPhone.getText().toString().trim().length();
        int codeLength = mCode.getText().toString().trim().length();
        int passLength = mPassword.getText().toString().trim().length();
        int rePassLength = mRePassword.getText().toString().trim().length();
        if (phoneLength < 11 || codeLength < 1 || passLength < 6 || rePassLength < 6) {
            mActionNext.setEnabled(false);
        } else {
            mActionNext.setEnabled(true);
        }
    }

    @Override
    public void showSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        launchActivity(intent);
    }

    @Override
    public void onTimer(String format) {
        runOnUiThread(() -> {
            mActionCode.setEnabled(false);
            mActionCode.setTextColor(getResources().getColor(R.color.qmui_config_color_gray_5));
            mActionCode.setText(format);
        });

    }

    @Override
    public void onTimerComplet() {
        runOnUiThread(() -> {
            mActionCode.setEnabled(true);
            mActionCode.setTextColor(getResources().getColor(R.color.app_color_blue));
            mActionCode.setText("重新获取");
        });

    }
}
