package org.tjut.xsl.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import org.tjut.xsl.di.component.DaggerMainComponent;
import org.tjut.xsl.mvp.contract.MainContract;
import org.tjut.xsl.mvp.presenter.MainPresenter;

import org.tjut.xsl.R;
import org.tjut.xsl.mvp.ui.fragment.HallFragment;


import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/11/2019 04:38
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    private Map<ContentPage, Fragment> mPageMap = new HashMap<>();
    private android.support.v4.app.FragmentPagerAdapter mAdapter;

    @BindView(R.id.nav_view)
    BottomNavigationView mNavView;

    @BindView(R.id.fragment_vp)
    ViewPager mViewPager;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initViewPage();
        initNavBotton();
    }

    private void initViewPage() {
        // init view pager
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(mPageChangeListener);
    }

    private void initNavBotton() {
        mNavView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
        finish();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                mViewPager.setCurrentItem(0);
                return true;
            case R.id.navigation_dashboard:
                mViewPager.setCurrentItem(1);
                return true;
            case R.id.navigation_notifications:
                mViewPager.setCurrentItem(2);
                return true;
        }
        return false;
    };

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            mNavView.getMenu().getItem(i).setChecked(true);
        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }
    };


    private class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {


        public FragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ContentPage page = ContentPage.getPage(position);
            return getPageView(page);
        }

        @Override
        public int getCount() {
            return ContentPage.SIZE;
        }
    }

    private Fragment getPageView(ContentPage page) {
        Fragment fragment = mPageMap.get(page);
        if (fragment == null) {
            if (page == ContentPage.Item1) {
                fragment = HallFragment.newInstance();
            } else if (page == ContentPage.Item2) {
                fragment = HallFragment.newInstance();
            } else if (page == ContentPage.Item3) {
                fragment = HallFragment.newInstance();
            }
            mPageMap.put(page, fragment);
        }
        return fragment;
    }

    public enum ContentPage {
        Item1(0),
        Item2(1),
        Item3(2);
        public static final int SIZE = 3;
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
                case 2:
                    return Item3;
                default:
                    return Item1;
            }
        }

        public int getPosition() {
            return position;
        }
    }
}
