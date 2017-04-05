package com.wic.buildher.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.wic.buildher.R;

import uk.co.chrisjenx.calligraphy.CalligraphyUtils;

/**
 * A borderless radio button that bolds and underlines its text when selected
 * Supports custom fonts when checked/unchecked
 */
public class BorderlessRadioButton extends android.support.v7.widget.AppCompatRadioButton {
    private String mCheckedFontPath;
    private String mUncheckedFontPath;

    public BorderlessRadioButton(Context context) {
        this(context, null);
    }

    public BorderlessRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BorderlessRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, R.attr.BorderlessRadioButtonStyle);

        TypedArray array = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.BorderlessRadioButton, 0, 0);
        mCheckedFontPath = array.getString(R.styleable.BorderlessRadioButton_checkedFontPath);
        mUncheckedFontPath = array.getString(R.styleable.BorderlessRadioButton_uncheckedFontPath);
    }

    @Override
    public void setChecked(boolean checked) {
        super.setChecked(checked);

        if (checked) {
            setPaintFlags(getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            if (mCheckedFontPath != null) {
                CalligraphyUtils.applyFontToTextView(getContext(), this, mCheckedFontPath);
            } else {
                setTypeface(null, Typeface.BOLD);
            }
        } else {
            setPaintFlags(getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
            if (mUncheckedFontPath != null) {
                CalligraphyUtils.applyFontToTextView(getContext(), this, mUncheckedFontPath);
            } else {
                setTypeface(null, Typeface.NORMAL);
            }
        }
    }
}
