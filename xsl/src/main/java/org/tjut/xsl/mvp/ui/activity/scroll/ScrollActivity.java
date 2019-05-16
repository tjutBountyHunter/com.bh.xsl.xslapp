package org.tjut.xsl.mvp.ui.activity.scroll;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;


import org.tjut.xsl.R;
import org.tjut.xsl.app.LaucherConfigkeys;
import org.tjut.xsl.app.TeaPreference;
import org.tjut.xsl.app.utils.WindonwUtil;
import org.tjut.xsl.mvp.ui.activity.SignInActivity;
import org.tjut.xsl.mvp.ui.activity.scroll.LauncherHolderCreator;


import java.util.ArrayList;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/11/2019 04:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class ScrollActivity extends BaseActivity implements OnItemClickListener {


    private ConvenientBanner<Integer> mConvenientBanner = null;
    private final ArrayList<Integer> INTEGERS = new ArrayList<>();

    private void initBanner() {
        mConvenientBanner = new ConvenientBanner<>(this);
        INTEGERS.add(R.drawable.launcher_01);
        INTEGERS.add(R.drawable.launcher_02);
        INTEGERS.add(R.drawable.launcher_03);
        mConvenientBanner
                .setPages(new LauncherHolderCreator(), INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(false);
    }


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return 0; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        WindonwUtil.setFullScreen(this);
        initBanner();
        setContentView(mConvenientBanner);
    }

    @Override
    public void onItemClick(int position) {
        // 如果点击的是最后一个
        if (position == INTEGERS.size() - 1) {
            TeaPreference.setAppFlag(LaucherConfigkeys.HAS_FIRST_LAUNCHER_APP.name(), true);
            Intent intent = new Intent(this, SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
