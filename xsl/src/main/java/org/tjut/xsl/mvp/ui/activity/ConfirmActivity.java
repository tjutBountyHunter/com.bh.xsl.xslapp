package org.tjut.xsl.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.qmuiteam.qmui.widget.QMUIFloatLayout;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import org.tjut.xsl.di.component.DaggerConfirmComponent;
import org.tjut.xsl.mvp.contract.ConfirmContract;
import org.tjut.xsl.mvp.model.entity.User;
import org.tjut.xsl.mvp.presenter.ConfirmPresenter;

import org.tjut.xsl.R;
import org.tjut.xsl.mvp.ui.widget.TagView;


import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/20/2019 02:02
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ConfirmActivity extends BaseActivity<ConfirmPresenter> implements ConfirmContract.View {

    public static final int REQUEST_CODE = 3;

    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    private static final String EORR_TEXT = "未填写";

    @BindView(R.id.topbar_auth)
    QMUITopBar mTopBar;
    @BindView(R.id.glv_auth)
    QMUIGroupListView mGroupListView;
    @BindView(R.id.fl_tags)
    QMUIFloatLayout mTagsFloatLayoutView;
    private QMUITipDialog tipDialog;
    private QMUICommonListItemView schoolItemView;
    private QMUICommonListItemView collegeItemView;
    private QMUICommonListItemView majorItemView;
    private QMUICommonListItemView sexItemView;
    private QMUICommonListItemView numberItemView;

    @OnClick(R.id.bt_add_tag)
    void onAddTags() {
        Intent intent = new Intent(this, SelectTagActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @OnClick(R.id.tv_enter)
    void onEnter() {
//        if (checkForm()) {
//            presenter.upLoadForm();
//        } else {
//            Toast.makeText(authActivity.this, "请补全信息", Toast.LENGTH_SHORT).show();
//        }
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerConfirmComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_confirm; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initTopBar();
        initGroupView();
        tipDialog = new QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载")
                .create();
    }

    private void initGroupView() {
        schoolItemView = mGroupListView.createItemView("学校");
        schoolItemView.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        collegeItemView = mGroupListView.createItemView("学院");
        collegeItemView.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        majorItemView = mGroupListView.createItemView("专业");
        majorItemView.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        sexItemView = mGroupListView.createItemView("性别");
        sexItemView.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);
        numberItemView = mGroupListView.createItemView("学号");
        numberItemView.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUIGroupListView.newSection(this)
                .setTitle("个人信息")
                .addItemView(schoolItemView, onClickListener)
                .addItemView(collegeItemView, onClickListener)
                .addItemView(majorItemView, onClickListener)
                .addItemView(sexItemView, onClickListener)
                .addItemView(numberItemView, onClickListener)
                .addTo(mGroupListView);

        QMUIGroupListView.newSection(this)
                .setTitle("技能标签")
                .addTo(mGroupListView);
    }

    View.OnClickListener onClickListener = v -> {
        if (v instanceof QMUICommonListItemView) {
            CharSequence text = ((QMUICommonListItemView) v).getText();
            if (((QMUICommonListItemView) v).getAccessoryType() == QMUICommonListItemView.ACCESSORY_TYPE_SWITCH) {
                ((QMUICommonListItemView) v).getSwitch().toggle();
            }
            if ("学校".contentEquals(text)) {
                Intent intent = new Intent(this, SchoolActivity.class);
                intent.putExtra("RequestType", SchoolActivity.REQUEST_TYPE_SCHOOL);
                launchActivity(intent);
            } else if ("学院".contentEquals(text)) {
                if (schoolItemView.getDetailText() == null || schoolItemView.getDetailText().toString().isEmpty())
                    showMessage("请先选择学校");
                else {
                    Intent intent = new Intent(this, SchoolActivity.class);
                    intent.putExtra("RequestType", SchoolActivity.REQUEST_TYPE_COLLEGE);
                    startActivity(intent);
                }
            } else if ("专业".contentEquals(text)) {
                if (collegeItemView.getDetailText() == null || collegeItemView.getDetailText().toString().isEmpty())
                    showMessage("请先选择学校/学院");
                else {
                    Intent intent = new Intent(this, SchoolActivity.class);
                    intent.putExtra("RequestType", SchoolActivity.REQUEST_TYPE_MAJOR);
                    startActivity(intent);
                }
            } else if ("性别".contentEquals(text)) {
                showSingleChoiceDialog();
            } else if ("学号".contentEquals(text)) {
                showEditTextDialog();
            }
        }
    };

    private void addItemToLayout(QMUIFloatLayout floatLayout, String name) {
        TagView textView;
        textView = new TagView(this);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        textView.setText(name);
        textView.setSelected(true);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        floatLayout.addView(textView, lp);
    }

    private void showSingleChoiceDialog() {
        final String[] items = new String[]{"男", "女"};
        final int checkedIndex = 0;
        new QMUIDialog.CheckableDialogBuilder(this)
                .setCheckedIndex(checkedIndex)
                .addItems(items, (dialog, which) -> {
                    sexItemView.setDetailText(items[which]);
                    dialog.dismiss();
                    Objects.requireNonNull(mPresenter).upDataSex(items[which]);
                })
                .create(mCurrentDialogStyle).show();
    }

    private void showEditTextDialog() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(this);
        builder.setTitle("学号")
                .setPlaceholder("在此输入您的学号")
                .setInputType(InputType.TYPE_CLASS_NUMBER)
                .addAction("取消", (dialog, index) -> dialog.dismiss())
                .addAction("确定", (dialog, index) -> {
                    CharSequence text = builder.getEditText().getText();
                    if (text != null && text.length() > 0) {
//                        Toast.makeText(ConfirmActivity.this, "您的学号: " + text, Toast.LENGTH_SHORT).show();
                        numberItemView.setDetailText(text.toString().trim());
                        dialog.dismiss();
                        Objects.requireNonNull(mPresenter).upDataNumber(text.toString().trim());
                    } else {
                        showMessage("请填入学号");
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    @Override
    public void showUserInfo(User user) {
        String text = null;
        text = user.getSchool();
        schoolItemView.setDetailText((text == null || text.isEmpty()) ? EORR_TEXT : text);
        text = user.getCollege();
        collegeItemView.setDetailText((text == null || text.isEmpty()) ? EORR_TEXT : text);
        text = user.getMajor();
        majorItemView.setDetailText((text == null || text.isEmpty()) ? EORR_TEXT : text);
        text = user.getSex();
        sexItemView.setDetailText((text == null || text.isEmpty()) ? EORR_TEXT : text);
        text = user.getSno();
        numberItemView.setDetailText((text == null || text.isEmpty()) ? EORR_TEXT : text);
    }

    @Override
    public void showLoading() {
        tipDialog.show();
    }

    @Override
    public void hideLoading() {
        tipDialog.dismiss();
    }

    private void initTopBar() {
        mTopBar.setTitle("认证");
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> onBackPressed());
        tipDialog = new QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载")
                .create();
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
}
