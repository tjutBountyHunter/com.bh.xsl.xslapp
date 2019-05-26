package org.tjut.xsl.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import org.tjut.xsl.di.component.DaggerChangePasswordComponent;
import org.tjut.xsl.mvp.contract.ChangePasswordContract;
import org.tjut.xsl.mvp.presenter.ChangePasswordPresenter;

import org.tjut.xsl.R;


import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


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
public class ChangePasswordActivity extends BaseActivity<ChangePasswordPresenter> implements ChangePasswordContract.View, TextWatcher {

    @BindView(R.id.topbar_change_psd)
    QMUITopBar topBar = null;
    @BindView(R.id.ll_code)
    LinearLayout mLinearLayoutCode = null;
    @BindView(R.id.ll_psd)
    LinearLayout mLinearLayoutPsd = null;
    @BindView(R.id.bt_action_next)
    QMUIRoundButton mConfirmNext = null;
    @BindView(R.id.bt_action_previous)
    QMUIRoundButton mConfirmPrevious = null;
    @BindView(R.id.et_phone)
    EditText mPhone = null;
    @BindView(R.id.tv_ver_code)
    TextView mActionCode = null;
    @BindView(R.id.et_ver_code)
    EditText mCode = null;
    @BindView(R.id.et_new_psd)
    EditText mNewPassword = null;
    @BindView(R.id.et_re_psd)
    EditText mRePassword = null;

    private void initTopBar() {
        topBar.setTitle("重置密码");
        topBar.addLeftBackImageButton().setOnClickListener(v -> onBackPressed());
    }

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
    void nextAndConfirm() {
        assert mPresenter != null;

        String phone = mPhone.getText().toString().trim();
        String code = mCode.getText().toString().trim();
        String password = mNewPassword.getText().toString().trim();
        if (mConfirmNext.getText().toString().equals("下一步")) {
            if (checkPhoneFormat()) {
                mPresenter.requestConfirmCode(phone, code);
            } else {
                showMessage("手机号格式不正确");
            }
        } else {
            if (checkPasswordFormat()) {
                mPresenter.updatePassword(phone, password);
            } else {
                showMessage("密码输入不一致");
            }

        }
    }

    @OnClick(R.id.bt_action_previous)
    void toPrevious() {
        mLinearLayoutCode.setVisibility(View.VISIBLE);
        mLinearLayoutPsd.setVisibility(View.GONE);
        mConfirmNext.setText("下一步");
        mConfirmPrevious.setVisibility(View.GONE);
    }

    private boolean checkPasswordFormat() {
        String passwd = mNewPassword.getText().toString().trim();
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

    private void setListener() {
        mPhone.addTextChangedListener(this);
        mCode.addTextChangedListener(this);
        mNewPassword.addTextChangedListener(this);
        mRePassword.addTextChangedListener(this);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerChangePasswordComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_change_password; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initTopBar();
        setListener();
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
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (mConfirmNext.getText().toString().trim().equals("下一步")) {
            setNextEnable();
        } else {
            setConfirmEnable();
        }
    }

    private void setNextEnable() {
        String phone = mPhone.getText().toString().trim();
        String code = mCode.getText().toString().trim();
        if (phone.equals("") || phone.length() < 11 || code.equals("")) {
            mConfirmNext.setEnabled(false);
        } else {
            mConfirmNext.setEnabled(true);
        }
    }

    private void setConfirmEnable() {
        String psd = mNewPassword.getText().toString().trim();
        String rePsd = mRePassword.getText().toString().trim();
        if (psd.length() < 6 || rePsd.length() < 6) {
            mConfirmNext.setEnabled(false);
        } else {
            mConfirmNext.setEnabled(true);
        }
    }

    @Override
    public void onNextStep() {
        mConfirmNext.setText("重置密码");
        mConfirmPrevious.setVisibility(View.VISIBLE);
        mLinearLayoutCode.setVisibility(View.GONE);
        mLinearLayoutPsd.setVisibility(View.VISIBLE);
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
    public void onTimerComplete() {
        runOnUiThread(() -> {
            mActionCode.setEnabled(true);
            mActionCode.setTextColor(getResources().getColor(R.color.app_color_blue));
            mActionCode.setText("重新获取");
        });
    }
}
