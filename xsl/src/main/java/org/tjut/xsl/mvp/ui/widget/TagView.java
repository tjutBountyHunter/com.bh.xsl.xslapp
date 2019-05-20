package org.tjut.xsl.mvp.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;

import org.tjut.xsl.R;


public class TagView extends android.support.v7.widget.AppCompatTextView {
    private boolean isShowTip;

    private Bitmap cha;

    public TagView(Context context) {
        super(context);
        setup(context, null);
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs);
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context, attrs);
    }

    private void setup(Context context, AttributeSet attrs) {

        this.setClickable(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.setBackground(getResources().getDrawable(R.drawable.tag_view));
        } else {
            if (this.isSelected()) {
                this.setBackgroundColor(getResources().getColor(R.color.app_color_theme_4));
            } else {
                this.setBackgroundColor(getResources().getColor(R.color.qmui_config_color_gray_8));
            }
        }
        setGravity(Gravity.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isShowTip) {
//            Paint paint = getPaint();
//            float x = getWidth() - 32;
//            float y = 0;
//            canvas.drawBitmap(cha, x, y, paint);
        }
    }


    public void setShowTipable(boolean show) {
        this.isShowTip = show;
    }
}
