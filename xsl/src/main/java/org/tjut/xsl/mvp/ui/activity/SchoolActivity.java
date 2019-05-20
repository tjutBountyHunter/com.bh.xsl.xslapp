package org.tjut.xsl.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.qmuiteam.qmui.widget.QMUITopBar;

import org.tjut.xsl.app.AccountManager;
import org.tjut.xsl.di.component.DaggerSchoolActivityComponent;
import org.tjut.xsl.mvp.contract.SchoolActivityContract;
import org.tjut.xsl.mvp.model.entity.School;
import org.tjut.xsl.mvp.presenter.SchoolActivityPresenter;

import org.tjut.xsl.R;
import org.tjut.xsl.mvp.ui.widget.RecycleViewDivider;
import org.tjut.xsl.mvp.ui.widget.SearchButton;


import java.util.List;
import java.util.Objects;

import butterknife.BindView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/19/2019 15:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class SchoolActivity extends BaseActivity<SchoolActivityPresenter> implements SchoolActivityContract.View, SearchButton.ITextChang {

    public final static int RESULT_CODE = 2;
    public final static int REQUEST_TYPE_SCHOOL = 1;
    public final static int REQUEST_TYPE_COLLEGE = 2;
    public final static int REQUEST_TYPE_MAJOR = 3;

    @BindView(R.id.rcl_school)
    RecyclerView recyclerView = null;
    @BindView(R.id.topbar_schools)
    QMUITopBar topBar = null;
    @BindView(R.id.prb_loading)
    ProgressBar bar = null;
    @BindView(R.id.tv_school_tip)
    TextView tip = null;

    SchoolAdapter adapter = null;

    private int tag;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerSchoolActivityComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_school; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        String title = null;
        initAdapter();
        if (intent != null) {
            tag = intent.getIntExtra("RequestType", 0);
            switch (tag) {
                case REQUEST_TYPE_SCHOOL:
                    title = "搜索学校名";
                    Objects.requireNonNull(mPresenter).initSchoolsData();
                    break;
                case REQUEST_TYPE_COLLEGE:
                    title = "搜索学院名";
                    Objects.requireNonNull(mPresenter).initCollegesData();
                    break;
                case REQUEST_TYPE_MAJOR:
                    title = "搜索专业名";
                    Objects.requireNonNull(mPresenter).initMajorsData();
                    break;
                default:
                    title = "搜索";
                    break;
            }
        }
        initTopBar(title);
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.HORIZONTAL, 1,
                getResources().getColor(R.color.qmui_config_color_gray_9)));
        adapter = new SchoolAdapter(R.layout.item_school);
        adapter.setOnItemClickListener((adapter, view, position) -> {
            TextView textView = view.findViewById(R.id.tv_school);
            Objects.requireNonNull(mPresenter).setUserSchoolInfo(tag, textView.getText().toString().trim());
            setResult(RESULT_CODE);
            killMyself();
        });
        recyclerView.setAdapter(adapter);
    }

    private void initTopBar(String title) {
        topBar.addLeftBackImageButton().setOnClickListener(v -> onBackPressed());
        SearchButton searchButton = new SearchButton(this, this);
        searchButton.setHint(title);
        RelativeLayout.LayoutParams lp =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        topBar.addLeftView(searchButton, R.id.view_id, lp);
    }


    @Override
    public void showLoading() {
        bar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        bar.setVisibility(View.GONE);
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
    public void onTextChanged(CharSequence sequenc) {
        Objects.requireNonNull(mPresenter).setSearchText(sequenc.toString());
        if (!sequenc.toString().isEmpty()) {
            tip.setText("搜索的结果");
        }
    }

    @Override
    public void showData(List<String> itemNames) {
        adapter.replaceData(itemNames);
    }

    class SchoolAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public SchoolAdapter(int layoutResId) {
            super(layoutResId);
        }

        @Override
        protected void convert(BaseViewHolder helper, String itemName) {
            helper.setText(R.id.tv_school, itemName);
        }
    }
}
