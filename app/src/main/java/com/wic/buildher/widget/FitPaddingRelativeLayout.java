package com.wic.buildher.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * RelativeLayout that makes fitsSystemWindows work with padding
 * Normally, enabling fitsSystemWindows ignores all padding values
 */
public class FitPaddingRelativeLayout extends RelativeLayout {
    public FitPaddingRelativeLayout(Context context) {
        super(context);
    }

    public FitPaddingRelativeLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FitPaddingRelativeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FitPaddingRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int
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
