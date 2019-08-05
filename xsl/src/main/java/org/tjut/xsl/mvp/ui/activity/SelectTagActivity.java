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
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import org.tjut.xsl.di.component.DaggerSelectTagComponent;
import org.tjut.xsl.mvp.contract.SelectTagContract;
import org.tjut.xsl.mvp.model.entity.Tag;
import org.tjut.xsl.mvp.presenter.SelectTagPresenter;

import org.tjut.xsl.R;
import org.tjut.xsl.mvp.ui.widget.TagView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/20/2019 03:26
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SelectTagActivity extends BaseActivity<SelectTagPresenter> implements SelectTagContract.View {

    public static final int RESULT_CODE = 200;

    private static final String SELECT_TAG = "SELECT_TAG";


    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    private HashMap<String, View> tag_view_map = new HashMap<>();


    @BindView(R.id.floatl_tags)
    QMUIFloatLayout tagsFloat;
    @BindView(R.id.floatl_selecte_tags)
    QMUIFloatLayout selectedTagsFloat;

    @BindView(R.id.container_layout)
    View mContainer;

    @BindView(R.id.empty_layout)
    View mEmptyView;

    @BindView(R.id.bt_create_tag_empty)
    QMUIRoundButton createTagEmptyView;

    @BindView(R.id.topbar)
    QMUITopBar topBar;

    private QMUITipDialog tipDialog = null;
    private QMUITipDialog countTipDialog = null;


    @OnClick(R.id.bt_create_tag)
    void createTag() {
        if (selectedTagsFloat.getChildCount() >= 5) {
            countTipDialog.show();
            mContainer.postDelayed(() -> countTipDialog.dismiss(), 1000);
        } else
            showEditTextDialog();
    }

    @OnClick(R.id.bt_create_tag_empty)
    void createTagEmpty() {
        if (selectedTagsFloat.getChildCount() >= 5) {
            countTipDialog.show();
            mContainer.postDelayed(() -> countTipDialog.dismiss(), 1000);
        } else
            showEditTextDialog();
    }

    @OnClick(R.id.bt_get_tag)
    void getOtherTags() {
        Objects.requireNonNull(mPresenter).getOtherTagsData();
    }

    @OnClick(R.id.bt_yes)
    void complete() {
        ArrayList<String> tagIds = new ArrayList<>();
        ArrayList<String> tagNams = new ArrayList<>();
        Tag tag;
        TagView tagView = null;
        for (int i = 0; i < selectedTagsFloat.getChildCount(); i++) {
            tagView = (TagView) selectedTagsFloat.getChildAt(i);
            tag = new Tag((String) tagView.getTag(), tagView.getText().toString());
            tagIds.add(tag.getTagid());
            tagNams.add(tag.getTagName());
        }
        Intent intent = new Intent();
        intent.putStringArrayListExtra("tagIds", tagIds);
        intent.putStringArrayListExtra("tagNams", tagNams);
        setResult(RESULT_CODE, intent);
        finish();
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSelectTagComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_select_tag; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mContainer.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        tipDialog = new QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载")
                .create();
        countTipDialog = new QMUITipDialog.Builder(this)
                .setTipWord("最多选择5个标签")
                .create();

        topBar.setTitle("添加标签");
        topBar.addLeftBackImageButton().setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void showLoading() {
        if (!tipDialog.isShowing()) {
            tipDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        tipDialog.dismiss();
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
    protected void onDestroy() {
        super.onDestroy();
        if (tag_view_map != null) {
            tag_view_map.clear();
            tag_view_map = null;
        }
    }

    public void showSelectTags(TagView tagTopView) {
        TagView textView = new TagView(this);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setText(tagTopView.getText());
        textView.setSelected(true);
        textView.setOnClickListener(selectListener);
        textView.setTag(tagTopView.getTag());
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        selectedTagsFloat.addView(textView, lp);
        tag_view_map.put((String) tagTopView.getTag(), tagTopView);
    }


    @Override
    public void showTags(List<Tag> tags) {
        if (tags != null && !tags.isEmpty()) {
            mContainer.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
            TagView textView;
            tagsFloat.removeAllViews();
            for (Tag tag :
                    tags) {
                textView = new TagView(this);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                textView.setText(tag.getTagName());
                textView.setOnClickListener(listener);
                textView.setTag(tag.getTagid());
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tagsFloat.addView(textView, lp);
                if (tag_view_map.containsKey(tag.getTagid())) {
                    textView.setSelected(true);
                    tag_view_map.remove(tag.getTagid());
                    tag_view_map.put(tag.getTagid(), textView);
                }
            }
        } else {
            mEmptyView.setVisibility(View.VISIBLE);
            mContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public void addTag(Tag tag) {
        TagView textView = new TagView(this);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        textView.setText(tag.getTagName());
        textView.setSelected(true);
        textView.setOnClickListener(listener);
        textView.setTag(tag.getTagid());
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tagsFloat.addView(textView, lp);
        showSelectTags(textView);
    }

    @Override
    public void showResult(ArrayList<String> tagNames) {

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof TagView && !v.isSelected()) {
                if (selectedTagsFloat.getChildCount() < 5) {
                    v.setSelected(true);
                    showSelectTags((TagView) v);
                } else {
                    countTipDialog.show();
                    v.postDelayed(() -> countTipDialog.dismiss(), 1000);
                }
            }
        }
    };

    private View.OnClickListener selectListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            selectedTagsFloat.removeView(v);
            View topTag = tag_view_map.remove((String) v.getTag());
            if (topTag != null) {
                topTag.setSelected(false);
            }
        }
    };

    private void showEditTextDialog() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(this);
        builder.setTitle("创建标签")
                .setPlaceholder("在此输入您的标签,5字以内")
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction("取消", (dialog, index) -> dialog.dismiss())
                .addAction("确定", (dialog, index) -> {
                    CharSequence text = builder.getEditText().getText();
                    if (text != null && text.length() > 0) {
                        if (text.length() > 5) {
                            showMessage("长度不能超过5");
                        }
                        dialog.dismiss();
                        Objects.requireNonNull(mPresenter).createTag(text);
                    } else {
                        showMessage("请输入标签");
                    }
                })
                .create(mCurrentDialogStyle).show();
    }


}
