package org.tjut.xsl.mvp.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageConfig;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.qmuiteam.qmui.widget.QMUIFloatLayout;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.textview.QMUILinkTextView;

import org.tjut.xsl.app.AccountManager;
import org.tjut.xsl.di.component.DaggerTaskDetailComponent;
import org.tjut.xsl.mvp.contract.TaskDetailContract;
import org.tjut.xsl.mvp.model.entity.Master;
import org.tjut.xsl.mvp.model.entity.TaskDetail;
import org.tjut.xsl.mvp.model.entity.TaskStats;
import org.tjut.xsl.mvp.presenter.TaskDetailPresenter;

import org.tjut.xsl.R;


import java.util.Objects;

import butterknife.BindView;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


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
public class TaskDetailActivity extends BaseActivity<TaskDetailPresenter> implements TaskDetailContract.View {

    private static String TASK_ID;
    private static String HUNTER_ID;
    private static int TASK_STATS;


    private QMUITipDialog tipDialog;

    @BindView(R.id.topbar_task_detail)
    QMUITopBar mTopBar;

    @BindView(R.id.layout_schedule)
    ConstraintLayout mLaoutSchedule;
    @BindView(R.id.layout_task_detail)
    NestedScrollView mLayoutRoot;
    @BindView(R.id.tv_task_detail_title)
    TextView mTaskTitle;
    @BindView(R.id.tv_task_detail_content)
    TextView mTaskContent;
    @BindView(R.id.fl_task_detail_tags)
    QMUIFloatLayout mTaskTagsFloatLayout;
    @BindView(R.id.iv_task_detail_tx)
    QMUIRadiusImageView mMasterTxView;
    @BindView(R.id.tv_master_name)
    TextView mMasterName;
    @BindView(R.id.bt_task_enter)
    Button mTaskEnterButton;
    @BindView(R.id.tv_phone_link)
    QMUILinkTextView mLinkTextView;
    @BindView(R.id.tv_task_detail_money)
    TextView mTaskMoney;
    @BindView(R.id.tv_task_createDate)
    TextView mTaskCreateDate;
    @BindView(R.id.tv_task_deadlineDate)
    TextView mDeadlineDate;
    @BindView(R.id.rl_master2)
    View mHunterLayout;
    @BindView(R.id.iv_task_detail_tx2)
    QMUIRadiusImageView mHunterTxView;
    @BindView(R.id.tv_hunter_name)
    TextView mHunterName;
    @BindView(R.id.tv_hunter_se)
    TextView mHunterSe;
    @BindView(R.id.tv_complet)
    TextView mStatsHunterCompletView;
    @BindView(R.id.view2)
    View mStatusHCom2MComView;
    @BindView(R.id.tv_enter)
    TextView mStatsMasterCompletView;
    @BindView(R.id.view)
    View mStatsRecive2HunterCompletView;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerTaskDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_task_detail; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
             TASK_ID = intent.getStringExtra("taskId");
            if (TASK_ID != null && !TASK_ID.isEmpty())
                Objects.requireNonNull(mPresenter).initData(TASK_ID);
        }

        setTopbar();
        tipDialog = new QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载")
                .create();
    }

    private void setTopbar() {
        mTopBar.setTitle("任务详情");
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void showLoading() {
        tipDialog.show();
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
    public void showTask(TaskDetail taskDetail) {

        mLayoutRoot.setVisibility(View.VISIBLE);
        TASK_STATS = taskDetail.getState();
        mTaskEnterButton.setOnClickListener(listener);

        if (taskDetail.getHunterInfo() != null) {
            HUNTER_ID = taskDetail.getHunterInfo().getHunterid();
//            Glide.with(this).load(taskDetail.getHunterInfo().getTxUrl()).into(mHunterTxView);
            mHunterName.setText(taskDetail.getHunterInfo().getName());
            mHunterLayout.setVisibility(View.VISIBLE);
            mHunterSe.setVisibility(View.VISIBLE);
            Objects.requireNonNull(mPresenter).loadHunterTx(ImageConfigImpl.builder()
                    .url(taskDetail.getHunterInfo().getTxUrl())
                    .imageView(mHunterTxView)
                    .placeholder("男".equals(AccountManager.getSex()) ? R.drawable.default_tx_man : R.drawable.default_tx_woman)
                    .build());
            mHunterName.setText(taskDetail.getHunterInfo().getName());
        } else {
            mHunterLayout.setVisibility(View.GONE);
            mHunterSe.setVisibility(View.GONE);
        }
        Timber.d(AccountManager.getHunterId() + " " + HUNTER_ID);

        String userId = "0";
        if (taskDetail.getMasterInfo() != null){
            Master master = taskDetail.getMasterInfo();
            userId = master.getUserid();

        }

        Objects.requireNonNull(mPresenter).loadMasterTx(ImageConfigImpl.builder()
                .url(taskDetail.getMasterInfo() != null ? taskDetail.getMasterInfo().getTxUrl() : "")
                .imageView(mMasterTxView)
                .placeholder("男".equals(AccountManager.getSex()) ? R.drawable.default_tx_man : R.drawable.default_tx_woman)
                .build());

        mLinkTextView.setVisibility(View.GONE);
        if (TASK_STATS == TaskStats.PENDING_RECEPTION.ordinal()   // 未接
                || TASK_STATS == TaskStats.PENDING_RECEPTION_ALGORITHM.ordinal()) {
            mLaoutSchedule.setVisibility(View.GONE);
            if (!AccountManager.getUserId().equals(userId)) {
                mTaskEnterButton.setText("接取任务");
                mTaskEnterButton.setTag(ButtonTag.JIE_QU);
                mTaskEnterButton.setVisibility(View.VISIBLE);
            } else {
                mTaskEnterButton.setText("取消任务");
                mTaskEnterButton.setTag(ButtonTag.QU_XIAO);
                mTaskEnterButton.setVisibility(View.VISIBLE);
            }
        } else if (TASK_STATS == TaskStats.PROCESSING.ordinal()) {  // 进行中
            if (AccountManager.getHunterId().equals(HUNTER_ID)) {
                mTaskEnterButton.setText("我已完成任务");
                mTaskEnterButton.setTag(ButtonTag.HUNTER_ENTER);
                mTaskEnterButton.setVisibility(View.VISIBLE);
                mLinkTextView.setVisibility(View.VISIBLE);
            } else {
                mTaskEnterButton.setVisibility(View.GONE);
            }

        } else if (TASK_STATS == TaskStats.HUNTER_CONFIRMATION.ordinal()) {  // 猎人确认
            if (AccountManager.getUserId().equals(userId)) {
                mTaskEnterButton.setText("确认完成");
                mTaskEnterButton.setTag(ButtonTag.MASTER_ENTER);
                mTaskEnterButton.setVisibility(View.VISIBLE);
                Drawable drawable = getResources().getDrawable(R.drawable.ic_recive);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mStatsHunterCompletView.setCompoundDrawables(null, drawable,
                        null, null);
                mStatsRecive2HunterCompletView.setBackgroundColor(getResources().getColor(R.color.app_color_blue));
            } else if (AccountManager.getHunterId().equals(HUNTER_ID)) {
                mTaskEnterButton.setText("等待雇主确认");
                mTaskEnterButton.setVisibility(View.VISIBLE);
                mTaskEnterButton.setEnabled(false);
                Drawable drawable = getResources().getDrawable(R.drawable.ic_recive);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                mStatsHunterCompletView.setCompoundDrawables(null, drawable,
                        null, null);
                mStatsRecive2HunterCompletView.setBackgroundColor(getResources().getColor(R.color.app_color_blue));
                mLinkTextView.setVisibility(View.VISIBLE);
            } else {
                mTaskEnterButton.setVisibility(View.GONE);
            }
        } else if (TASK_STATS == TaskStats.COMPLETE.ordinal()) {  // 完成
            mTaskEnterButton.setVisibility(View.GONE);

            Drawable drawable = getResources().getDrawable(R.drawable.ic_recive);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

            mStatsHunterCompletView.setCompoundDrawables(null, drawable,
                    null, null);
            mStatsMasterCompletView.setCompoundDrawables(null, drawable,
                    null, null);
            mStatsRecive2HunterCompletView.setBackgroundColor(getResources().getColor(R.color.app_color_blue));
            mStatusHCom2MComView.setBackgroundColor(getResources().getColor(R.color.app_color_blue));
        } else {     // 其他情况
            mTaskEnterButton.setVisibility(View.GONE);
        }

        mTaskTitle.setText(taskDetail.getTaskTitle());
        mTaskContent.setText(taskDetail.getContent());

        mMasterName.setText(taskDetail.getMasterInfo() != null ? taskDetail.getMasterInfo().getName() : "未知用户");
        String s = "联系电话：" + (taskDetail.getMasterInfo() != null ? taskDetail.getMasterInfo().getPhone() : "未知号码");
        mLinkTextView.setText(s);
        s = "¥" + taskDetail.getMoney();
        mTaskMoney.setText(s);
        mTaskCreateDate.setText(taskDetail.getCreateDate());
        mDeadlineDate.setText(taskDetail.getDeadLineDate());
    }

    @Override
    public ImageConfig getTxConfig() {
        return ImageConfigImpl.builder()
                .url(AccountManager.getTxUrl())
                .imageView(mMasterTxView)
                .placeholder("男".equals(AccountManager.getSex()) ? R.drawable.default_tx_man : R.drawable.default_tx_woman)
                .build();
    }

    private View.OnClickListener listener = v -> {
        switch (v.getId()) {
            case R.id.bt_task_enter:
                switch (((ButtonTag) v.getTag())) {
                    case JIE_QU:
                        Objects.requireNonNull(mPresenter).reciveTask(TASK_ID);
                        break;
                    case QU_XIAO:
                        Objects.requireNonNull(mPresenter).cancelTask(TASK_ID);
                        break;
                    case HUNTER_ENTER:
                        Objects.requireNonNull(mPresenter).hunterComplet(TASK_ID);
                        break;
                    case MASTER_ENTER:
                        Objects.requireNonNull(mPresenter).MasterComplet(TASK_ID, HUNTER_ID);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    };

    @Override
    public void showHunterCompleted() {
        Drawable drawable = getResources().getDrawable(R.drawable.ic_recive);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mStatsHunterCompletView.setCompoundDrawables(null, drawable,
                null, null);
        mStatsRecive2HunterCompletView.setBackgroundColor(getResources().getColor(R.color.app_color_blue));

        mTaskEnterButton.setText("等待雇主确认");
        mTaskEnterButton.setEnabled(false);
    }

    @Override
    public void showMasterComplet() {
        Drawable drawable = getResources().getDrawable(R.drawable.ic_recive);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        mStatsHunterCompletView.setCompoundDrawables(null, drawable,
                null, null);
        mStatsMasterCompletView.setCompoundDrawables(null, drawable,
                null, null);
        mStatsRecive2HunterCompletView.setBackgroundColor(getResources().getColor(R.color.app_color_blue));
        mStatusHCom2MComView.setBackgroundColor(getResources().getColor(R.color.app_color_blue));

        mTaskEnterButton.setText("恭喜你 任务已完成");
        mTaskEnterButton.setEnabled(false);
    }

    @Override
    public void showReciveComplet() {
        mTaskEnterButton.setText("任务接取成功 快去完成吧");
        mTaskEnterButton.setEnabled(false);
        mLaoutSchedule.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCancelTaskComplet() {
        mTaskEnterButton.setText("任务已取消");
        mTaskEnterButton.setEnabled(false);
        mLaoutSchedule.setVisibility(View.GONE);
    }

    private enum ButtonTag {
        JIE_QU,
        QU_XIAO,
        HUNTER_ENTER,
        MASTER_ENTER
    }

}
