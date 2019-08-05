package org.tjut.xsl.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huantansheng.easyphotos.EasyPhotos;
import com.huantansheng.easyphotos.callback.SelectCallback;
import com.huantansheng.easyphotos.models.album.entity.Photo;
import com.huantansheng.easyphotos.setting.Setting;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageConfig;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUIRadiusImageView;
import com.qmuiteam.qmui.widget.QMUITabSegment;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.tjut.xsl.app.AccountManager;
import org.tjut.xsl.app.utils.GlideImageEngine;
import org.tjut.xsl.di.component.DaggerUserCenterComponent;
import org.tjut.xsl.mvp.contract.UserCenterContract;
import org.tjut.xsl.mvp.model.entity.User;
import org.tjut.xsl.mvp.presenter.UserCenterPresenter;

import org.tjut.xsl.R;
import org.tjut.xsl.mvp.ui.fragment.RecivedTaskFragment;
import org.tjut.xsl.mvp.ui.fragment.SendTaskFragment;
import org.tjut.xsl.mvp.ui.widget.YMULevelProgresBar;
import org.tjut.xsl.mvp.ui.widget.YMULevelText;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/26/2019 17:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class UserCenterActivity extends BaseActivity<UserCenterPresenter> implements UserCenterContract.View {

    private Map<ContentPage, Fragment> mPageMap = new HashMap<>();

    private ContentPage mDestPage = ContentPage.Item1;

    @BindView(R.id.personal_center_contentViewPager)
    ViewPager mContentViewPager;
    @BindView(R.id.tabSegment_personal_center)
    QMUITabSegment mTagSegment;
    @BindView(R.id.imageView_personal_touxiang)
    QMUIRadiusImageView mTouXinagView;
    @BindView(R.id.tv_personal_center_nichen)
    TextView mNiChenView;
    @BindView(R.id.image_sex)
    ImageView mSexView;
    @BindView(R.id.leveltext_boss)
    YMULevelText mMasterLevelText;
    @BindView(R.id.leveltext_hunter)
    YMULevelText mHunterLevelText;
    @BindView(R.id.levelbar_boss)
    YMULevelProgresBar mMasterLevelbar;
    @BindView(R.id.levelbar_hunter)
    YMULevelProgresBar mHunterLevelbar;
    @BindView(R.id.bt_account_information)
    TextView mAccountInfoView;
    @BindView(R.id.topbar_personal_center)
    QMUITopBar mQMUITopBar;

    private QMUITipDialog tipDialog;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUserCenterComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_user_center; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        QMUIStatusBarHelper.translucent(this);
        initTopbar();
        initTabAndPager();
    }

    private void initTopbar() {
        mQMUITopBar.setTitle("个人中心");
        mQMUITopBar.showTitleView(false);
        mQMUITopBar.addLeftBackImageButton().setOnClickListener(v -> onBackPressed());
        tipDialog = new QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载")
                .create();
        mTouXinagView.setOnClickListener(clickListener);
        mAccountInfoView.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = v -> {
        switch (v.getId()) {
            case R.id.imageView_personal_touxiang:
                EasyPhotos.createAlbum(UserCenterActivity.this, true,
                        GlideImageEngine.getInstance())//参数说明：上下文，是否显示相机按钮，[配置Glide为图片加载引擎](https://github.com/HuanTanSheng/EasyPhotos/wiki/12-%E9%85%8D%E7%BD%AEImageEngine%EF%BC%8C%E6%94%AF%E6%8C%81%E6%89%80%E6%9C%89%E5%9B%BE%E7%89%87%E5%8A%A0%E8%BD%BD%E5%BA%93)
                        .setFileProviderAuthority("org.tjut.xsl_bs")//参数说明：见下方`FileProvider的配置`
                        .setGif(false)
                        .setVideo(false)
                        .setPuzzleMenu(false)
                        .setCameraLocation(Setting.LIST_FIRST)
                        .start(new SelectCallback() {
                            @Override
                            public void onResult(ArrayList<Photo> photos, ArrayList<String> paths, boolean isOriginal) {
                                if (!paths.isEmpty()) {
                                    mPresenter.UpDateTx(paths.get(0));
                                }
                            }
                        });

                break;

            case R.id.bt_account_information:
                Intent intent = new Intent(this, UserInfoActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    };

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

    private void initTabAndPager() {
        mContentViewPager.setAdapter(mPagerAdapter);
        mContentViewPager.setCurrentItem(mDestPage.getPosition(), false);

        mTagSegment.addTab(new QMUITabSegment.Tab("已发任务"));
        mTagSegment.addTab(new QMUITabSegment.Tab("已接任务"));
        mTagSegment.setupWithViewPager(mContentViewPager, false);
        mTagSegment.setMode(QMUITabSegment.MODE_FIXED);
    }

    private FragmentPagerAdapter mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int i) {
            ContentPage page = ContentPage.getPage(i);
            return getPageView(page);
        }

        @Override
        public int getCount() {
            return ContentPage.SIZE;
        }
    };

    @Override
    public void killMyself() {
        finish();
    }

    private Fragment getPageView(ContentPage page) {
        Fragment fragment = mPageMap.get(page);
        if (fragment == null) {
            if (page == ContentPage.Item1) {
                fragment = SendTaskFragment.newInstance();
            } else if (page == ContentPage.Item2) {
                fragment = RecivedTaskFragment.newInstance();
            }
            mPageMap.put(page, fragment);
        }
        return fragment;
    }

    @Override
    public void showUserInfo(User user) {
        String name = user.getName();
        if (name.length() > 5)
            name = name.subSequence(0, 4).toString() + "***";
        mNiChenView.setText(name);
        int masterLevel = user.getMasterlevel();
        int hunterLevel = user.getHunterlevel();
        mMasterLevelText.setmNumText(String.valueOf(masterLevel));
        mHunterLevelText.setmNumText(String.valueOf(hunterLevel));
        if (user.getMasterEmpirical() != null && user.getHunterEmpirical() != null) {
            mMasterLevelbar.setMaxValue(getEmpiricalMax(masterLevel));
            mMasterLevelbar.setProgress(user.getMasterEmpirical());
            mHunterLevelbar.setMaxValue(getEmpiricalMax(hunterLevel));
            mHunterLevelbar.setProgress(user.getHunterEmpirical());
        }
    }

    @Override
    public ImageConfig getTxComfig() {
        return ImageConfigImpl.builder()
                .url(AccountManager.getTxUrl())
                .imageView(mTouXinagView)
                .placeholder("男".equals(AccountManager.getSex()) ? R.drawable.default_tx_man : R.drawable.default_tx_woman)
                .build();
    }

   
    private int getEmpiricalMax(int level) {
        int max = 100;
        switch (level) {
            case 1:
                max = 100;
                break;
            case 2:
                max = 200;
                break;
            case 3:
                max = 300;
                break;
            case 4:
                max = 500;
                break;
            case 5:
                max = 800;
                break;
            default:
                max = 1300;
                break;
        }
        return max;
    }


    public enum ContentPage {
        Item1(0),
        Item2(1);
        public static final int SIZE = 2;
        private final int position;

        ContentPage(int pos) {
            position = pos;
        }

        public static ContentPage getPage(int position) {
            switch (position) {
                case 0:
                    return Item1;
                case 1:
                    return Item2;
                default:
                    return Item1;
            }
        }

        public int getPosition() {
            return position;
        }
    }
}
