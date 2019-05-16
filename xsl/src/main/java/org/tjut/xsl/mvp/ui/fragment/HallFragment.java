package org.tjut.xsl.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageConfig;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.ImageLoader_Factory;
import com.jess.arms.http.imageloader.glide.GlideImageLoaderStrategy;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.integration.ConfigModule;
import com.jess.arms.utils.ArmsUtils;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.QMUIFloatLayout;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import org.tjut.xsl.app.AccountManager;
import org.tjut.xsl.app.Tea;
import org.tjut.xsl.di.component.DaggerHallFragmentComponent;
import org.tjut.xsl.mvp.contract.HallFragmentContract;
import org.tjut.xsl.mvp.model.entity.HallTaskCard;
import org.tjut.xsl.mvp.presenter.HallFragmentPresenter;

import org.tjut.xsl.R;

import java.text.DateFormat;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

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
    public final static String TAG = "HallFragment";
    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    private ImageConfigImpl mImageConfig;

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
        refresh.setOnRefreshListener(refreshListener);
        setAdapter();
        Objects.requireNonNull(mPresenter).initData();
    }


    private void setAdapter() {
        adapter = new TaskAdapter(R.layout.item_task_card);
        View header = LayoutInflater.from(getContext()).inflate(R.layout.item_hall_header, null, false);
        adapter.addHeaderView(header);
        mEmptyView = new QMUIEmptyView(mContext);
        if (true) {
            mEmptyView.setTitleText("少侠何门何派");
            mEmptyView.setButton("速去登记", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        } else if (AccountManager.NORMAL_STATE == AccountManager.getState()) {
            mEmptyView.setTitleText("没有更多数据啦");
        }
        adapter.setEmptyView(mEmptyView);

        adapter.setOnItemClickListener((adapter, view, position) -> {
        });

        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view instanceof QMUIRoundButton) {
            }
        });

        tasksView.setLayoutManager(new LinearLayoutManager(getContext()));
        tasksView.setAdapter(adapter);

    }


    SwipeRefreshLayout.OnRefreshListener refreshListener = () -> {
    };

    /**
     * 通过此方法可以使 Fragment 能够与外界做一些交互和通信, 比如说外部的 Activity 想让自己持有的某个 Fragment 对象执行一些方法,
     * 建议在有多个需要与外界交互的方法时, 统一传 {@link Message}, 通过 what 字段来区分不同的方法, 在 {@link #setData(Object)}
     * 方法中就可以 {@code switch} 做不同的操作, 这样就可以用统一的入口方法做多个不同的操作, 可以起到分发的作用
     * <p>
     * 调用此方法时请注意调用时 Fragment 的生命周期, 如果调用 {@link #setData(Object)} 方法时 {@link Fragment#onCreate(Bundle)} 还没执行
     * 但在 {@link #setData(Object)} 里却调用了 Presenter 的方法, 是会报空的, 因为 Dagger 注入是在 {@link Fragment#onCreate(Bundle)} 方法中执行的
     * 然后才创建的 Presenter, 如果要做一些初始化操作,可以不必让外部调用 {@link #setData(Object)}, 在 {@link #initData(Bundle)} 中初始化就可以了
     * <p>
     * Example usage:
     * <pre>
     * public void setData(@Nullable Object data) {
     *     if (data != null && data instanceof Message) {
     *         switch (((Message) data).what) {
     *             case 0:
     *                 loadData(((Message) data).arg1);
     *                 break;
     *             case 1:
     *                 refreshUI();
     *                 break;
     *             default:
     *                 //do something
     *                 break;
     *         }
     *     }
     * }
     *
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
    public ImageConfig getImageConfig() {
        if (mImageConfig == null) {
            mImageConfig = ImageConfigImpl.builder()
                    .url(AccountManager.getTxUrl())
                    .imageView(mTouXiangImageView)
                    .placeholder("男".equals(AccountManager.getSex()) ? R.drawable.default_tx_man : R.drawable.default_tx_woman)
                    .build();
        }
        return mImageConfig;
    }


    class TaskAdapter extends BaseQuickAdapter<HallTaskCard, BaseViewHolder> {

        public TaskAdapter(int layoutResId, @Nullable List<HallTaskCard> data) {
            super(layoutResId, data);
        }

        public TaskAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, HallTaskCard item) {

        }
    }
}
