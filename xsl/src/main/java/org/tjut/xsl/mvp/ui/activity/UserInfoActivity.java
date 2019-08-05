package org.tjut.xsl.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageConfig;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import org.tjut.xsl.app.AccountManager;
import org.tjut.xsl.di.component.DaggerUserInfoComponent;
import org.tjut.xsl.mvp.contract.UserInfoContract;
import org.tjut.xsl.mvp.model.entity.User;
import org.tjut.xsl.mvp.presenter.UserInfoPresenter;

import org.tjut.xsl.R;


import java.util.Objects;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/26/2019 18:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class UserInfoActivity extends BaseActivity<UserInfoPresenter> implements UserInfoContract.View {


    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    @BindView(R.id.group_person)
    QMUIGroupListView mGroupListView;
    @BindView(R.id.topbar_person_info)
    QMUITopBar mQMUITopBar;
    @BindView(R.id.bt_exit_account)
    Button mExitButton;

    private QMUICommonListItemView itemTx;
    private QMUICommonListItemView itemAccount;
    private QMUICommonListItemView itemName;
    private QMUICommonListItemView itemPasswd;
    private QMUICommonListItemView itemSex;
    private QMUICommonListItemView itemSchool;
    private QMUICommonListItemView itemCollege;
    private QMUICommonListItemView itemMajor;
    private QMUICommonListItemView itemSno;
    private QMUIRadiusImageView txView;

    QMUITipDialog mQmuiTipDialog;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUserInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_user_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initTopBar();
        initGroupView();
        mExitButton.setOnClickListener(onClickListener);
        mQmuiTipDialog = new QMUITipDialog.Builder(this)
                .setTipWord("正在加载")
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .create();
    }

    private void initTopBar() {
        mQMUITopBar.setTitle("个人资料");
        mQMUITopBar.addLeftBackImageButton().setOnClickListener(v -> onBackPressed());
    }



    private void initGroupView() {
        itemTx = mGroupListView.createItemView("头像");
        itemTx.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        txView = new QMUIRadiusImageView(this);
        txView.setBorderWidth(0);
        txView.setCircle(true);
        ViewGroup.LayoutParams lp =
                new ViewGroup.LayoutParams(100, 100);
        txView.setLayoutParams(lp);
        itemTx.addAccessoryCustomView(txView);

        itemAccount = mGroupListView.createItemView("账号");
        itemAccount.setDetailText("流年物语");

        itemName = mGroupListView.createItemView("昵称");
        itemName.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        itemName.setDetailText("流年物语");

        itemPasswd = mGroupListView.createItemView("密码");
        itemPasswd.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        itemPasswd.setDetailText("流年物语");


        itemSex = mGroupListView.createItemView("性别");
        itemSex.setDetailText("流年物语");

        itemSchool = mGroupListView.createItemView("学校");
        itemSchool.setDetailText("流年物语");

        itemCollege = mGroupListView.createItemView("学院");
        itemCollege.setDetailText("流年物语");

        itemMajor = mGroupListView.createItemView("专业");
        itemMajor.setDetailText("流年物语");

        itemSno = mGroupListView.createItemView("学号");
        itemSno.setDetailText("流年物语");


        QMUIGroupListView.newSection(this)
                .setTitle("个人信息")
                .addItemView(itemTx, onClickListener)
                .addItemView(itemAccount, onClickListener)
                .addItemView(itemName, onClickListener)
                .addItemView(itemPasswd, onClickListener)
                .addTo(mGroupListView);

        QMUIGroupListView.newSection(this)
                .setTitle("认证信息")
                .addItemView(itemSchool, onClickListener)
                .addItemView(itemCollege, onClickListener)
                .addItemView(itemMajor, onClickListener)
                .addItemView(itemSex, onClickListener)
                .addItemView(itemSno, onClickListener)
                .addTo(mGroupListView);

    }

    private View.OnClickListener onClickListener = v -> {
        if (v instanceof QMUICommonListItemView) {
            switch (((QMUICommonListItemView) v).getText().toString()) {
                case "头像":

                    break;
                case "昵称":
                    showEditNameDialog();
                    break;
                case "密码":
                    showEditPasswordDialog();
                    break;
                default:
                    break;
            }
        } else {
            switch (v.getId()) {
                case R.id.bt_exit_account:
                    Objects.requireNonNull(mPresenter).signOut();
                    break;
                default:
                    break;
            }
        }
    };

    private void showEditNameDialog() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(this);
        builder.setTitle("修改昵称")
                .setPlaceholder("在此输入您的昵称")
                .addAction("取消", (dialog, index) -> dialog.dismiss())
                .addAction("确定", (dialog, index) -> {
                    CharSequence text = builder.getEditText().getText();
                    if (text != null && text.length() > 0) {
                        itemName.setDetailText(text.toString().trim());
                        dialog.dismiss();
                        Objects.requireNonNull(mPresenter).upDataInfo("name", text.toString().trim());
                    } else {
                        showMessage("请填入昵称");
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    private void showEditPasswordDialog() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(this);
        builder.setTitle("修改密码")
                .setPlaceholder("在此输入您的信息")
                .setInputType(InputType.TYPE_CLASS_NUMBER)
                .addAction("取消", (dialog, index) -> dialog.dismiss())
                .addAction("确定", (dialog, index) -> {
                    CharSequence text = builder.getEditText().getText();
                    if (text != null && text.length() > 0) {
                        itemName.setDetailText(text.toString().trim());
                        dialog.dismiss();
                        Objects.requireNonNull(mPresenter).upDataInfo("password", text.toString().trim());
                    } else {
                        showMessage("请填入密码");
                    }
                })
                .create(mCurrentDialogStyle).show();
    }


    @Override
    public void showLoading() {
        mQmuiTipDialog.show();
    }

    @Override
    public void hideLoading() {
        mQmuiTipDialog.dismiss();
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
    public void showUserInfo(User user) {
        itemAccount.setDetailText(user.getPhone());
        itemName.setDetailText(user.getName());
        itemPasswd.setDetailText("**********");
        itemSex.setDetailText(user.getSex());
        itemSchool.setDetailText(user.getSchool());
        itemCollege.setDetailText(user.getCollege());
        itemMajor.setDetailText(user.getMajor());
        itemSno.setDetailText(user.getSno());
    }

    @Override
    public ImageConfig getTxConfig() {
        return ImageConfigImpl.builder()
                .url(AccountManager.getTxUrl())
                .imageView(txView)
                .placeholder("男".equals(AccountManager.getSex()) ? R.drawable.default_tx_man : R.drawable.default_tx_woman)
                .build();
    }

    @Override
    public void showSignOut() {
        AccountManager.logOut();
        Intent intent = new Intent(this, SignInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        launchActivity(intent);
    }
}
