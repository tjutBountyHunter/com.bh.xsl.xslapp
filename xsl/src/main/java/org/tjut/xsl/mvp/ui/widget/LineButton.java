package org.tjut.xsl.mvp.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.tjut.xsl.R;


public class LineButton extends ConstraintLayout {
    private TextView titleText;
    private TextView leftText;
    private View lineButton;

    public LineButton(Context context) {
        super(context);
    }

    public LineButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_line_button, this);

        titleText = findViewById(R.id.tv_title);
        leftText = findViewById(R.id.tv_leftText);
        lineButton = findViewById(R.id.line_button);
    }

    public LineButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTitleText(String titleText) {
        this.titleText.setText(titleText);
    }

    public void setLeftText(String leftText) {
        if (leftText != null)
            this.leftText.setText(leftText);
    }

    public void setOnclick(OnClickListener listener) {
        lineButton.setOnClickListener(listener);
    }

    public void setTitleStyle(Typeface typeface) {
        titleText.setTypeface(typeface);
    }

    public void isRightText(boolean has) {
        if (!has) {
            leftText.setText("");
            leftText.setCompoundDrawables(null, null, null, null);
        }

    }
}
