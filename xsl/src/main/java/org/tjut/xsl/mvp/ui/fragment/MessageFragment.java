package org.tjut.xsl.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUITopBar;

import org.tjut.xsl.app.AccountManager;
import org.tjut.xsl.di.component.DaggerMessageComponent;
import org.tjut.xsl.mvp.contract.MessageContract;
import org.tjut.xsl.mvp.model.entity.Message;
import org.tjut.xsl.mvp.presenter.MessagePresenter;

import org.tjut.xsl.R;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/26/2019 23:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MessageFragment extends BaseFragment<MessagePresenter> implements MessageContract.View {

    @BindView(R.id.msg_reclview)
    RecyclerView msgViews;

    @BindView(R.id.messahe_topbar)
    QMUITopBar topBar;
    private QMUIEmptyView mEmptyView;
    private MessageAdapter mMessageAdapter;
    @BindView(R.id.refresh_task)
    SwipeRefreshLayout refreshLayout;

    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerMessageComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        topBar.setTitle("消息");
        mEmptyView = new QMUIEmptyView(mContext);
        mMessageAdapter = new MessageAdapter(R.layout.item_message);
        msgViews.setLayoutManager(new LinearLayoutManager(getContext()));
        msgViews.setAdapter(mMessageAdapter);
        refreshLayout.setOnRefreshListener(() -> Objects.requireNonNull(mPresenter).initData());
        Objects.requireNonNull(mPresenter).initData();
    }


    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        refreshLayout.setRefreshing(false);
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
    public void showMessages(List<Message> messages) {
        mMessageAdapter.replaceData(messages);
    }

    @Override
    public void showEmptyView() {
        if (mMessageAdapter.getEmptyView() == null) {
            mEmptyView.setTitleText("暂时没有消息哦");
            mMessageAdapter.setEmptyView(mEmptyView);
        }
    }

    class MessageAdapter extends BaseQuickAdapter<Message, BaseViewHolder> {

        public MessageAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, Message item) {
            helper.setText(R.id.tv_title, item.getTitle())
                    .setText(R.id.tv_message, item.getMsg_content())
                    .setText(R.id.tv_time, item.getDate());
            Objects.requireNonNull(mPresenter).loadTx(ImageConfigImpl.builder()
                    .imageView(helper.getView(R.id.iv_tx))
                    .placeholder(R.mipmap.ic_launcher2)
                    .build());
        }
    }
}
