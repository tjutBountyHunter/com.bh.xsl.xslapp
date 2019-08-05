package org.tjut.xsl.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageConfig;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUIFloatLayout;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import org.tjut.xsl.app.AccountManager;
import org.tjut.xsl.di.component.DaggerHallFragmentComponent;
import org.tjut.xsl.mvp.contract.HallFragmentContract;
import org.tjut.xsl.mvp.model.entity.Tag;
import org.tjut.xsl.mvp.model.entity.Task;
import org.tjut.xsl.mvp.presenter.HallFragmentPresenter;

import org.tjut.xsl.R;
import org.tjut.xsl.mvp.ui.activity.AddTaskActivity;
import org.tjut.xsl.mvp.ui.activity.ConfirmActivity;
import org.tjut.xsl.mvp.ui.activity.SchoolActivity;
import org.tjut.xsl.mvp.ui.activity.SearchTaskActivity;
import org.tjut.xsl.mvp.ui.activity.TaskDetailActivity;
import org.tjut.xsl.mvp.ui.activity.UserCenterActivity;
import org.tjut.xsl.mvp.ui.widget.TaskTagView;
import org.tjut.xsl.mvp.ui.widget.YMULevelText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/17/2019 01:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HallFragment extends BaseFragment<HallFragmentPresenter> implements HallFragmentContract.View {

    public final static int SCHOOL_REQUEST_CODE = 1;
    public final static String TAG = HallFragment.class.getSimpleName();
    private static final int CONFIRM_REQUEST = 10;
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    @BindView(R.id.rev_task)
    RecyclerView tasksView = null;
    @BindView(R.id.refresh_task)
    SwipeRefreshLayout refresh;
    @BindView(R.id.toolbar_hall)
    Toolbar toolbar = null;


    TaskAdapter adapter = null;
    TextView schoolView = null;

    @BindView(R.id.iv_touxiang)
    QMUIRadiusImageView mTouXiangImageView;
    private QMUIEmptyView mEmptyView;
    private View header;
    private TextView footerView;

    private QMUITipDialog mConfirmDialog;

    @OnClick(R.id.iv_add_task)
    void ClickaddTask() {
        if (checkPermission()) {
            Intent intent = new Intent(getActivity(), AddTaskActivity.class);
            launchActivity(intent);
        } else {
            showNotConfirmView();
        }
    }

    @OnClick(R.id.iv_touxiang)
    void onTouXiang() {
        startActivity(new Intent(getActivity(), UserCenterActivity.class));
    }

    @OnClick(R.id.bt_search)
    void onSearch() {
        startActivity(new Intent(getActivity(), SearchTaskActivity.class));
    }

    private boolean checkPermission() {
        return AccountManager.NORMAL_STATE == AccountManager.getState();
    }

    private void showNotConfirmView() {
        new QMUIDialog.MessageDialogBuilder(getActivity())
                .setTitle("用户认证")
                .setMessage("你还没有认证，不能发布任务")
                .addAction("取消", (dialog, index) -> dialog.dismiss())
                .addAction("去认证", (dialog, index) -> {
                    dialog.dismiss();
                    startActivityForResult(new Intent(mContext, ConfirmActivity.class),CONFIRM_REQUEST);
                    launchActivity(new Intent(mContext, ConfirmActivity.class));
                })
                .create(mCurrentDialogStyle).show();
    }

    public static HallFragment newInstance() {
        HallFragment fragment = new HallFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHallFragmentComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hall, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mEmptyView = new QMUIEmptyView(mContext);
        refresh.setOnRefreshListener(refreshListener);
        setAdapter();
        Objects.requireNonNull(mPresenter).initData();
    }


    private void setAdapter() {
        adapter = new TaskAdapter(R.layout.item_task_card);
        adapter.setOnItemClickListener((adapter, view, position) -> {
                Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
                intent.putExtra("taskId", (String) view.getTag());
                launchActivity(intent);
        });
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view instanceof QMUIRoundButton) {
                Objects.requireNonNull(mPresenter).reciveTask((String) view.getTag());
            }
        });
        tasksView.setLayoutManager(new LinearLayoutManager(getContext()));
        tasksView.setAdapter(adapter);

        header = LayoutInflater.from(getContext()).inflate(R.layout.item_hall_header, null, false);

        footerView = new TextView(mContext);
        footerView.setText("没有更多数据啦");
        RelativeLayout.LayoutParams lp =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        footerView.setLayoutParams(lp);
        int padd = QMUIDisplayHelper.dp2px(mContext, 4);
        footerView.setPadding(0, padd, 0, padd);
        footerView.setGravity(Gravity.CENTER);
    }


    SwipeRefreshLayout.OnRefreshListener refreshListener = () -> {
        Objects.requireNonNull(mPresenter).initData();
    };


    /**
     * // call setData(Object):
     * Message data = new Message();
     * data.what = 0;
     * data.arg1 = 1;
     * fragment.setData(data);
     * </pre>
     *
     * @param data 当不需要参数时 {@code data} 可以为 {@code null}
     */
    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
        if (refresh != null)
            refresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        if (refresh != null)
            refresh.setRefreshing(false);
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

    }

    @Override
    public ImageConfig getImageConfig() {
        return ImageConfigImpl.builder()
                .url(AccountManager.getTxUrl())
                .imageView(mTouXiangImageView)
                .placeholder("男".equals(AccountManager.getSex()) ? R.drawable.default_tx_man : R.drawable.default_tx_woman)
                .build();
    }

    @Override
    public void showInitData(List<Task> tasks) {
        adapter.setHeaderView(header);
        adapter.replaceData(tasks);
        adapter.setFooterView(footerView);
    }

    @Override
    public void showNotSchool() {
        mEmptyView.setTitleText("少侠何门何派");
        mEmptyView.setButton("速去登记", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SchoolActivity.class);
                intent.putExtra("RequestType", SchoolActivity.REQUEST_TYPE_SCHOOL);
                startActivityForResult(intent, SCHOOL_REQUEST_CODE);
            }
        });
        adapter.setEmptyView(mEmptyView);
    }

    @Override
    public void showEmptyView() {
        if (!adapter.getData().isEmpty()) {
            adapter.getData().clear();
            adapter.notifyDataSetChanged();
        }
        mEmptyView = new QMUIEmptyView(mContext);
        mEmptyView.setTitleText("没有任务啦，快去发布任务吧");
        adapter.setEmptyView(mEmptyView);
    }

    @Override
    public void showReciveSuccess(String taskId) {
        showMessagePositiveDialog(taskId);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCHOOL_REQUEST_CODE) {
            if (resultCode == SchoolActivity.RESULT_CODE) {
                Objects.requireNonNull(mPresenter).initData();
            }
        }else if (requestCode == CONFIRM_REQUEST){
            if (resultCode == ConfirmActivity.REQUEST_CODE){
                Objects.requireNonNull(mPresenter).initData();
            }
        }
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
                    taskTagView = new TaskTagView(getContext());
                    taskTagView.setText(tag.getTagName());
                    tagsFloatLayout.addView(taskTagView);
                }
            }

            helper.setText(R.id.bt_recive_task, "接")
                    .addOnClickListener(R.id.bt_recive_task);
            helper.getView(R.id.bt_recive_task).setTag(item.getTaskId());

        }
    }

    private void showMessagePositiveDialog(String taskId) {
        new QMUIDialog.MessageDialogBuilder(getActivity())
                .setTitle("接取成功")
                .setMessage("去看看任务进度吧")
                .addAction("取消", (dialog, index) -> dialog.dismiss())
                .addAction("任务进度", (dialog, index) -> {
                    dialog.dismiss();
                    Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
                    intent.putExtra("taskId", taskId);
                    Objects.requireNonNull(getContext()).startActivity(intent);
                })
                .create(mCurrentDialogStyle).show();
    }
}
