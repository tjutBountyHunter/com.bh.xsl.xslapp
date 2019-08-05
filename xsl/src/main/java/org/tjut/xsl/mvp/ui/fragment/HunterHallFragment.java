package org.tjut.xsl.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.tjut.xsl.app.AccountManager;
import org.tjut.xsl.di.component.DaggerHunterHallComponent;
import org.tjut.xsl.mvp.contract.HunterHallContract;
import org.tjut.xsl.mvp.model.entity.Hunter;
import org.tjut.xsl.mvp.presenter.HunterHallPresenter;

import org.tjut.xsl.R;
import org.tjut.xsl.mvp.ui.widget.LineButton;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/19/2019 12:27
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class HunterHallFragment extends BaseFragment<HunterHallPresenter> implements HunterHallContract.View {

    @BindView(R.id.relv_my_hunter)
    RecyclerView mMyHunterRecycle = null;
    @BindView(R.id.relv_hot_hunter)
    RecyclerView mHotHunterRecycle = null;
    @BindView(R.id.line_bt_my_hunter)
    LineButton mMyHunterButton = null;
    @BindView(R.id.line_bt_hot_hunter)
    LineButton mHotHunterButton = null;
    @BindView(R.id.toolbar_hall)
    Toolbar toolbar;

    private QMUITipDialog dialog;

    private final HotHunterAdapter mHotAdapter = new HotHunterAdapter(R.layout.item_hot_hunter_card);
    private final MyHunterRecyclerAdapter mMyAdapter = new MyHunterRecyclerAdapter(R.layout.item_hunter_my, null);

    public static HunterHallFragment newInstance() {
        HunterHallFragment fragment = new HunterHallFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerHunterHallComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hunter_hall, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mHotHunterButton.setLeftText("更多");
        mHotHunterButton.setTitleText("热门猎人");

        mMyHunterRecycle.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mMyHunterRecycle.setAdapter(mMyAdapter);

        mHotHunterRecycle.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mHotHunterRecycle.setAdapter(mHotAdapter);

        dialog = new QMUITipDialog.Builder(getContext())
                .setTipWord("正在加载")
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .create();
        Objects.requireNonNull(mPresenter).initDate();
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {
        dialog.show();
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
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
    public void showMyHunter(List<Hunter> hunters) {
        mMyAdapter.replaceData(hunters);
    }

    @Override
    public void showHotHunter(List<Hunter> hunters) {
        mHotAdapter.replaceData(hunters);
    }

    class MyHunterRecyclerAdapter extends BaseQuickAdapter<Hunter, BaseViewHolder> {


        MyHunterRecyclerAdapter(int layoutResId, @Nullable List<Hunter> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, Hunter item) {
            helper.setText(R.id.tv_my_hunter_name, item.getName());
            Objects.requireNonNull(mPresenter).loadMyHunterTx(ImageConfigImpl.builder()
                    .url(item.getTxUrl())
                    .imageView(helper.getView(R.id.imageView3))
                    .placeholder("男".equals(AccountManager.getSex()) ? R.drawable.default_tx_man : R.drawable.default_tx_woman)
                    .build());
        }
    }

    class HotHunterAdapter extends BaseQuickAdapter<Hunter, BaseViewHolder> {

        HotHunterAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, Hunter item) {
            helper.setText(R.id.tv_title, String.format("%s级猎人", item.getLevel()));
            String name = item.getName();
            if (name.length() > 5) {
                name = name.substring(name.length() - 4) + "*";
            }
            helper.setText(R.id.tv_name, name);
            Objects.requireNonNull(mPresenter).loadMyHunterTx(ImageConfigImpl.builder()
                    .url(item.getTxUrl())
                    .imageView(helper.getView(R.id.image_touxiang))
                    .placeholder("男".equals(AccountManager.getSex()) ? R.drawable.default_tx_man : R.drawable.default_tx_woman)
                    .build());

        }

    }
}
