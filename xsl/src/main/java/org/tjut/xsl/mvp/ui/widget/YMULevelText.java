package org.tjut.xsl.mvp.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import org.tjut.xsl.R;


public class YMULevelText extends View {

    private int mHeight;
    private int mWidth;
    private float mLeadHeight;
    private float textWidth;

    private RectF mBgRecF;
    private RectF mTopBgRecF;

    private Paint mBackgroundPaint = new Paint();
    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mNumTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int mTextColor;
    private int mbackgroundColor;
    private String mText = "猎人";
    private String mNumText = "8";
    private float mNumTextWidth;
    public static final float DEFAULT_LEAD_HEIGHT = 100f;
    public static final int DEFAULT_LEVEL = 1;
    public static final int DEFAULT_TEXT_COLOR = R.color.qmui_config_color_white;
    public static final int DEFAULT_BACKGROUND_COLOR = R.color.app_color_blue_2;

    public YMULevelText(Context context) {
        super(context);
        setup(context, null);
    }

    public YMULevelText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs);
    }

    public YMULevelText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context, attrs);
    }

    public void setup(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.YMULevelText);
        mLeadHeight = array.getDimension(R.styleable.YMULevelText_leadHeight, DEFAULT_LEAD_HEIGHT);
        mText = array.getString(R.styleable.YMULevelText_text);
        mNumText = String.valueOf(array.getInt(R.styleable.YMULevelText_levelText, DEFAULT_LEVEL));
        mTextColor = array.getColor(R.styleable.YMULevelText_textColor, getResources().getColor(DEFAULT_TEXT_COLOR));
        mbackgroundColor = array.getColor(R.styleable.YMULevelText_backgroundColor, getResources().getColor(DEFAULT_BACKGROUND_COLOR));

        configPaint();
        array.recycle();
    }

    private void configPaint() {
        mBackgroundPaint.setColor(mbackgroundColor);

        mTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        mNumTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mNumTextPaint.setColor(mTextColor);
        mNumTextPaint.setTextAlign(Paint.Align.LEFT);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mLeadHeight -= getPaddingTop() - getPaddingBottom();
        mHeight = (int) (mLeadHeight * 10 / 9);

        mTextPaint.setTextSize(mLeadHeight * 5 / 6);
        textWidth = mTextPaint.measureText(mText);
        mNumTextPaint.setTextSize(mHeight * 1.1f);
        mNumTextWidth = mNumTextPaint.measureText(mNumText);

        mWidth = (int) (getPaddingRight() + mNumTextWidth + textWidth + mHeight / 3);

        setMeasuredDimension(mWidth, mHeight);
        congfigShap();

    }

    private void congfigShap() {
        mBgRecF = new RectF(getPaddingLeft(), getPaddingTop() + (mHeight - mLeadHeight), getPaddingRight() + mWidth, getPaddingBottom() + mHeight);
        mTopBgRecF = new RectF(getPaddingLeft() + textWidth + mHeight / 6, getPaddingTop(), getPaddingRight() + mWidth, getPaddingBottom() + mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(mBgRecF, mLeadHeight / 12, mLeadHeight / 12, mBackgroundPaint);
        canvas.drawRect(mTopBgRecF.left, mBgRecF.top, mTopBgRecF.right, mBgRecF.bottom, mBackgroundPaint);

        canvas.drawRoundRect(mTopBgRecF, mHeight / 12, mHeight / 12, mBackgroundPaint);

        if (mText != null && mText.length() > 0) {
            Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
            float baseline = mBgRecF.top + (mBgRecF.height() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
            canvas.drawText(mText, mBgRecF.left + mBgRecF.bottom / 12, baseline, mTextPaint);
            canvas.drawText(mNumText, mTopBgRecF.left + mHeight / 11, mHeight * 11 / 12, mNumTextPaint);
        }
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
        invalidate();
    }

    public String getmNumText() {
        return mNumText;
    }

    public void setmNumText(String mNumText) {
        this.mNumText = mNumText;
        invalidate();
    }

    public int getmTextColor() {
        return mTextColor;
    }

    public void setmTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
        invalidate();
    }

    public int getMbackgroundColor() {
        return mbackgroundColor;
    }

    public void setMbackgroundColor(int mbackgroundColor) {
        this.mbackgroundColor = mbackgroundColor;
        invalidate();
    }
}
