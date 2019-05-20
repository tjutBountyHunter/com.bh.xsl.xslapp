package org.tjut.xsl.mvp.ui.widget;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;

import org.tjut.xsl.R;


public class TaskTagView extends android.support.v7.widget.AppCompatTextView {
    public TaskTagView(Context context) {
        super(context);
        setup(context, null);
    }

    public TaskTagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs);

    }

    public TaskTagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context, attrs);
    }

    private void setup(Context context, AttributeSet attrs) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.setBackground(getResources().getDrawable(R.drawable.bk_tv_task_tag));
        } else
            this.setBackgroundColor(getResources().getColor(R.color.app_color_blue));
        this.setTextColor(getResources().getColor(R.color.app_color_blue));
        this.setPadding(4, 2, 4, 2);
        this.setTextSize(10);
    }
}
