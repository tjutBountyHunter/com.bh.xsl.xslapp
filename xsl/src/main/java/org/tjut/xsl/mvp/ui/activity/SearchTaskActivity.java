package org.tjut.xsl.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUIFloatLayout;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import org.tjut.xsl.app.AccountManager;
import org.tjut.xsl.di.component.DaggerSearchTaskComponent;
import org.tjut.xsl.mvp.contract.SearchTaskContract;
import org.tjut.xsl.mvp.model.entity.Tag;
import org.tjut.xsl.mvp.model.entity.Task;
import org.tjut.xsl.mvp.presenter.SearchTaskPresenter;

import org.tjut.xsl.R;
import org.tjut.xsl.mvp.ui.fragment.HallFragment;
import org.tjut.xsl.mvp.ui.widget.SearchEditButton;
import org.tjut.xsl.mvp.ui.widget.TaskTagView;
import org.tjut.xsl.mvp.ui.widget.YMULevelText;


import java.text.DateFormat;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/29/2019 02:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SearchTaskActivity extends BaseActivity<SearchTaskPresenter> implements SearchTaskContract.View , SearchEditButton.ITextChang {

    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    @BindView(R.id.topbar_search)
    QMUITopBar mTopBar;
    @BindView(R.id.rcl_school)
    RecyclerView mRecyclerView;


    private SearchEditButton mSearchButton;

    private QMUITipDialog mQmuiTipDialog;
    private TaskAdapter adapter;
    private TextView footerView;
    private QMUIEmptyView mEmptyView;

    @OnClick(R.id.tv_search)
    void onSearch() {
        Objects.requireNonNull(mPresenter).searchData(mSearchButton.getText().trim());
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSearchTaskComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_search_task; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initTopbar();
        mEmptyView = new QMUIEmptyView(this);
        mSearchButton = new SearchEditButton(this, this);
        mSearchButton.setHint("搜索内容");
        RelativeLayout.LayoutParams lp =
                new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        lp.setMargins(0, QMUIDisplayHelper.dpToPx(14), QMUIDisplayHelper.dpToPx(48), QMUIDisplayHelper.dpToPx(14));
        mTopBar.addLeftView(mSearchButton, R.id.view_id, lp);
        mQmuiTipDialog = new QMUITipDialog.Builder(this)
                .setTipWord("正在搜索")
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .create();

        setAdapter();
    }

    private void setAdapter() {
        adapter = new TaskAdapter(R.layout.item_task_card);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(this, TaskDetailActivity.class);
            intent.putExtra("taskId", (String) view.getTag());
            launchActivity(intent);
        });
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view instanceof QMUIRoundButton) {
                Objects.requireNonNull(mPresenter).reciveTask((String) view.getTag());
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);


        footerView = new TextView(this);
        footerView.setText("没有更多数据啦");
        RelativeLayout.LayoutParams lp =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        footerView.setLayoutParams(lp);
        int padd = QMUIDisplayHelper.dp2px(this, 4);
        footerView.setPadding(0, padd, 0, padd);
        footerView.setGravity(Gravity.CENTER);
    }

    private void initTopbar() {
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> onBackPressed());
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
    public void showReciveSuccess(String taskId) {
        showMessagePositiveDialog(taskId);
    }

    @Override
    public void showEmptyView() {
        if (!adapter.getData().isEmpty()) {
            adapter.getData().clear();
            adapter.notifyDataSetChanged();
        }
        mEmptyView.setTitleText("没有符合条件的任务");
        adapter.setEmptyView(mEmptyView);
    }

    @Override
    public void showInitData(List<Task> tasks) {
        adapter.replaceData(tasks);
        adapter.setFooterView(footerView);
    }

    @Override
    public void onTextChanged(CharSequence sequenc) {

    }
    private void showMessagePositiveDialog(String taskId) {
        new QMUIDialog.MessageDialogBuilder(this)
                .setTitle("接取成功")
                .setMessage("任务接取成功，去看看任务进度吧")
                .addAction("取消", (dialog, index) -> dialog.dismiss())
                .addAction("任务进度", (dialog, index) -> {
                    dialog.dismiss();
                    Intent intent = new Intent(this, TaskDetailActivity.class);
                    intent.putExtra("taskId", taskId);
                    Objects.requireNonNull(this).startActivity(intent);
                })
                .create(mCurrentDialogStyle).show();
    }


    class TaskAdapter extends BaseQuickAdapter<Task, BaseViewHolder> {

        public TaskAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, Task item) {
            helper.itemView.setTag(item.getTaskId());
            QMUIRadiusImageView txView = helper.getView(R.id.iv_tx);
            ImageConfigImpl imageConfig = ImageConfigImpl.builder()
                    .url(item.getTxUrl())
                    .imageView(txView)
                    .placeholder("男".equals(AccountManager.getSex()) ? R.drawable.default_tx_man : R.drawable.default_tx_woman)
                    .build();
            Objects.requireNonNull(mPresenter).loadTaskItemTx(imageConfig);

            helper.setText(R.id.tv_name, item.getName());

            YMULevelText ymuLevelText = helper.getView(R.id.ym_level);
            ymuLevelText.setmText("雇主");
            ymuLevelText.setmNumText(String.valueOf(item.getMasterlevel()));

            helper.setText(R.id.tv_task_content, item.getContent());
            helper.setText(R.id.tv_task_title, item.getTaskTitle());
            helper.setText(R.id.tv_task_money, "¥" + item.getMoney());

            helper.setText(R.id.tv_deadline_time, "截止时间：" + item.getDeadLineDate());

            helper.setText(R.id.tv_task_time, item.getCreateDate());
            TaskTagView taskTagView;
            QMUIFloatLayout tagsFloatLayout = helper.getView(R.id.fl_task_tags);
            List<Tag> tags = item.getTags();
            tagsFloatLayout.removeAllViews();
            if (tags != null && !tags.isEmpty()) {
                for (Tag tag :
                        tags) {
                    taskTagView = new TaskTagView(getBaseContext());
                    taskTagView.setText(tag.getTagName());
                    tagsFloatLayout.addView(taskTagView);
                }
            }

            helper.setText(R.id.bt_recive_task, "接")
                    .addOnClickListener(R.id.bt_recive_task);
            helper.getView(R.id.bt_recive_task).setTag(item.getTaskId());

        }
    }
}
