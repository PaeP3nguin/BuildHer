package com.wic.buildher.widget;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.wic.buildher.R;

/**
 * A borderless radio button that bolds and underlines its text when selected
 * Supports custom fonts when checked/unchecked
 */
public class BorderlessRadioButton extends android.support.v7.widget.AppCompatRadioButton {
    public BorderlessRadioButton(Context context) {
        this(context, null);
    }

    public BorderlessRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BorderlessRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, R.attr.BorderlessRadioButtonStyle);
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);

        if (checked) {
            setPaintFlags(getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        } else {
            setPaintFlags(getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
        }
    }
}
