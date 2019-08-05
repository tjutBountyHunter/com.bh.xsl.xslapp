package org.tjut.xsl.mvp.ui.widget;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import org.tjut.xsl.R;


public class SearchEditButton extends ConstraintLayout implements TextWatcher, View.OnClickListener {
    private EditText editText = null;
    private ImageView cancel = null;

    private ITextChang iTextChang = null;

    public SearchEditButton(Context context, ITextChang iTextChang) {
        super(context);
        this.iTextChang = iTextChang;
        setup(context, null);
    }

    public SearchEditButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context, attrs);
    }

    public SearchEditButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context, attrs);
    }

    private void setup(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.widget_search_edit_button, this);
        editText = findViewById(R.id.et_search);
        cancel = findViewById(R.id.iv_cancel);
        editText.addTextChangedListener(this);
        cancel.setOnClickListener(this);
    }

    public void setHint(String hint) {
        editText.setHint(hint);
    }


    private boolean isEmptyInput() {
        return editText.getText().toString().trim().isEmpty();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        setCancelIconStatus(isEmptyInput());
        iTextChang.onTextChanged(s);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    private void setCancelIconStatus(boolean cancelIconStatus) {
        cancel.setVisibility(cancelIconStatus ? GONE : VISIBLE);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_cancel) {
            editText.setText("");
        }
    }

    public interface ITextChang {
        void onTextChanged(CharSequence sequenc);
    }

    public String getText() {
        Editable editable = editText.getText();
        return (editable == null ? null : editable.toString());
    }
}
