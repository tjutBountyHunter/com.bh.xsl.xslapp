package org.tjut.xsl.mvp.ui.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.qmuiteam.qmui.widget.QMUIFloatLayout;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import org.tjut.xsl.app.utils.CheckUtil;
import org.tjut.xsl.di.component.DaggerAddTaskComponent;
import org.tjut.xsl.mvp.contract.AddTaskContract;
import org.tjut.xsl.mvp.model.entity.Tag;
import org.tjut.xsl.mvp.model.entity.Task;
import org.tjut.xsl.mvp.presenter.AddTaskPresenter;

import org.tjut.xsl.R;
import org.tjut.xsl.mvp.ui.widget.TagView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/26/2019 10:45
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class AddTaskActivity extends BaseActivity<AddTaskPresenter> implements AddTaskContract.View {


    public static final int REQUEST_CODE_GET_TAGS = 5;


    @BindView(R.id.topbar)
    QMUITopBar topBar = null;

    @BindView(R.id.tv_release_title)
    EditText tileEdit = null;

    @BindView(R.id.et_release)
    EditText contentEdit = null;

    @BindView(R.id.tv_release_createDate)
    TextView createDateButton;

    @BindView(R.id.tv_release_deadline)
    TextView deadlineButton;

    @BindView(R.id.et_release_money)
    EditText moneyEditText;

    @BindView(R.id.fl_tag_lay)
    QMUIFloatLayout tagsFloatLayout;


    @BindView(R.id.st_recommend)
    Switch mRecommendSwitch;

    private QMUITipDialog tipDialog;


    @OnClick(R.id.release_createDate)
    void onCreatDate() {
        showDatePickerDialog(this, createDateButton, Calendar.getInstance());
    }

    @OnClick(R.id.release_deadline)
    void onDeadlineDate() {
        showDatePickerDialog(this, deadlineButton, Calendar.getInstance());
    }

    @OnClick(R.id.tv_release)
    void onRelease() {
        Task task = checkForm();
        if (task != null) {
            Objects.requireNonNull(mPresenter).upLaodTask(task);
        }
    }


    @OnClick(R.id.rl_tag)
    void onSelectTag() {
        Intent intent = new Intent(AddTaskActivity.this, SelectTagActivity.class);
        startActivityForResult(intent, REQUEST_CODE_GET_TAGS);
    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerAddTaskComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_add_task; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        topBar.addLeftBackImageButton().setOnClickListener(v -> onBackPressed());
        topBar.setTitle("发布任务");
        tipDialog = new QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载")
                .create();
        mRecommendSwitch.setChecked(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    public static void showDatePickerDialog(final Context context, final TextView tv, Calendar calendar) {
        new DatePickerDialog(context, (view, year, month, dayOfMonth) -> {
            tv.setText(year + "-" + (month + 1) + "-" + dayOfMonth + " ");
            showTimePickerDialog(context, tv, Calendar.getInstance());
        }
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public static void showTimePickerDialog(Context ctx, final TextView tv, Calendar calendar) {
        new TimePickerDialog(ctx,
                (view, hourOfDay, minute) -> tv.append(hourOfDay + ":" + minute)
                // 设置初始时间
                , calendar.get(Calendar.HOUR_OF_DAY)
                , calendar.get(Calendar.MINUTE)
                , true).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_GET_TAGS) {
            if (resultCode == SelectTagActivity.RESULT_CODE) {
                if (data != null) {
                    ArrayList<String> tagNames;
                    ArrayList<String> tagIds;
                    tagIds = data.getStringArrayListExtra("tagIds");
                    tagNames = data.getStringArrayListExtra("tagNams");
                    List<Tag> tags = new ArrayList<>();
                    for (int i = 0; i < tagIds.size(); i++) {
                        tags.add(new Tag(tagIds.get(i), tagNames.get(i)));
                    }
                    Objects.requireNonNull(mPresenter).saveTaskTags(tags);
                }
            }
        }
    }

    private void addItemToLayout(QMUIFloatLayout floatLayout, String name) {
        TagView textView;
        textView = new TagView(this);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        textView.setText(name);
        textView.setSelected(true);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        floatLayout.addView(textView, lp);
    }

    private Task checkForm() {
        String title = tileEdit.getText().toString().trim();
        String content = contentEdit.getText().toString().trim();
        String createDate = createDateButton.getText().toString();
        String deadline = deadlineButton.getText().toString();
        String msg = null;
        if (!CheckUtil.notNull(title)) {
            msg = "标题不能为空";
        } else if (!CheckUtil.notNull(content)) {
            msg = "悬赏内容不能为空";
        } else if (!CheckUtil.notNull(createDate) || createDate.equals("点我设置")) {
            msg = "任务开始时间不能为空";
        } else if (!CheckUtil.notNull(deadline) || deadline.equals("点我设置")) {
            msg = "任务截止时间不能为空";
        } else if (tagsFloatLayout.getChildCount() == 0) {
            msg = "请添加任务标签";
        }
        Task task = null;
        if (msg != null) {
            showMessage(msg);
        } else {
            task = new Task();
            task.setTaskId("local_current_task");
            task.setTaskTitle(title);
            task.setContent(content);
            task.setIsRecommend(mRecommendSwitch.isChecked());
            task.setCreateDate(createDate);
            task.setDeadLineDate(deadline);
            task.setImages(new ArrayList<>());
            task.setMoney(moneyEditText.getText().toString().trim().isEmpty() ? "0" : moneyEditText.getText().toString().trim());
        }
        return task;
    }

    @Override
    public void showSelectTags(List<String> tagNames) {
        tagsFloatLayout.removeAllViews();
        for (String tagName :
                tagNames) {
            addItemToLayout(tagsFloatLayout, tagName);
        }
    }
}
