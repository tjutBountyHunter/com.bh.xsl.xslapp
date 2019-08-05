package org.tjut.xsl.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;

import org.tjut.xsl.app.AccountManager;
import org.tjut.xsl.di.component.DaggerSendTaskComponent;
import org.tjut.xsl.mvp.contract.SendTaskContract;
import org.tjut.xsl.mvp.model.entity.Task;
import org.tjut.xsl.mvp.presenter.SendTaskPresenter;

import org.tjut.xsl.R;
import org.tjut.xsl.mvp.ui.activity.TaskDetailActivity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/26/2019 17:47
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SendTaskFragment extends BaseFragment<SendTaskPresenter> implements SendTaskContract.View {


    SentTaskAdapter mSentTaskAdapter;
    private QMUIEmptyView mEmptyView;
    @BindView(R.id.rrecycler_sent)
    RecyclerView mRecyclerView;
    private String TAG = this.getClass().getSimpleName();


    public static SendTaskFragment newInstance() {
        SendTaskFragment fragment = new SendTaskFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerSendTaskComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_send_task, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mSentTaskAdapter = new SentTaskAdapter(R.layout.item_sent_task);
        mSentTaskAdapter.setOnItemClickListener((adapter, view, position) -> {
            Task taskBean = (Task) adapter.getData().get(position);
            String taskId = null;
            if (taskBean != null) {
                taskId = taskBean.getTaskId();
            }
            Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
            intent.putExtra("taskId", taskId);
            startActivity(intent);
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mSentTaskAdapter);
        mEmptyView = new QMUIEmptyView(mContext);
        Objects.requireNonNull(mPresenter).initData();
    }


    @Override
    public void setData(@Nullable Object data) {

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

    }

    @Override
    public void showTask(List<Task> tasks) {
        mSentTaskAdapter.replaceData(tasks);
    }

    @Override
    public void showEmptyView() {
        if (mSentTaskAdapter.getEmptyView() == null) {
            mEmptyView.setTitleText("暂时没有已发任务，快去发布吧");
            mSentTaskAdapter.setEmptyView(mEmptyView);
        }
    }

    class SentTaskAdapter extends BaseQuickAdapter<Task, BaseViewHolder> {

        public SentTaskAdapter(int layoutResId, @Nullable List<Task> data) {
            super(layoutResId, data);
        }

        public SentTaskAdapter(@Nullable List<Task> data) {
            super(data);
        }

        public SentTaskAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, Task item) {
            helper.setText(R.id.tv_title, item.getTaskTitle());
            helper.setText(R.id.tv_content, item.getContent());
            helper.setText(R.id.tv_create_date, String.format("发布时间：%s", item.getCreateDate()));
            helper.setText(R.id.tv_deadline_data, String.format("截止时间：%s", item.getDeadLineDate()));
//            helper.getView(R.id.layout_hunter).setVisibility(item.getState() == 2 ? View.VISIBLE : View.GONE);


            TextView states = helper.getView(R.id.tv_status_text);
            ImageView iv_states = helper.getView(R.id.iv_status);
            if (2 == item.getState()) {
                states.setText("已接");
                iv_states.setBackground(getResources().getDrawable(R.drawable.dot_status_yijie));
            } else if (4 == item.getState()) {
                states.setText("待确认");
                iv_states.setBackground(getResources().getDrawable(R.drawable.dot_status_daiqueren));
            } else if (3 == item.getState()) {
                states.setText("完成");
                iv_states.setBackground(getResources().getDrawable(R.drawable.dot_status_wanchen));
            } else if (-2 == item.getState()) {
                states.setText("已取消");
                iv_states.setBackground(getResources().getDrawable(R.drawable.dot_status_cancel));
            }
//            if (item.getState() == 2 || item.getState() == 4 || item.getState() == 3) {
//                QMUIRadiusImageView imageView = helper.getView(R.id.iv_hunter_touxiang);
//                mPresenter.loadHunterTx(ImageConfigImpl.builder()
//                        .url(AccountManager.getTxUrl())
//                        .imageView(imageView)
//                        .placeholder("男".equals(AccountManager.getSex()) ? R.drawable.default_tx_man : R.drawable.default_tx_woman)
//                        .build());
//                helper.setText(R.id.tv_hunnter_name,item.get)

//            }
        }
    }
}
