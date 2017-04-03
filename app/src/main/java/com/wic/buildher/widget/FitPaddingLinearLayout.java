package com.wic.buildher.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * LinearLayout that makes fitsSystemWindows work with padding
 * Normally, enabling fitsSystemWindows ignores all padding values
 */
public class FitPaddingLinearLayout extends LinearLayout {
    public FitPaddingLinearLayout(Context context) {
        super(context);
    }

    public FitPaddingLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FitPaddingLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FitPaddingLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected boolean fitSystemWindows(Rect insets) {
        insets.top += getPaddingTop();
        insets.left += getPaddingLeft();
        insets.right += getPaddingRight();
        insets.bottom += getPaddingBottom();
        return super.fitSystemWindows(insets);
    }
}
