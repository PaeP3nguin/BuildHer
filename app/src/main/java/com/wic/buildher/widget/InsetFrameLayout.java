package com.wic.buildher.widget;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * FrameLayout that saves window insets and dispatches them to all new children
 * Suitable as a fragment container with fragments that use fitsSystemWindows
 */
public class InsetFrameLayout extends FrameLayout implements ViewGroup
        .OnHierarchyChangeListener, OnApplyWindowInsetsListener {
    private WindowInsetsCompat mInsets;

    public InsetFrameLayout(Context context) {
        super(context);
        init();
    }

    public InsetFrameLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InsetFrameLayout(Context context, @Nullable AttributeSet attrs, int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public InsetFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int
            defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        ViewCompat.setOnApplyWindowInsetsListener(this, this);
        setOnHierarchyChangeListener(this);
    }

    @Override
    public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
        mInsets = insets;

        for (int index = 0; index < getChildCount(); index++) {
            ViewCompat.dispatchApplyWindowInsets(getChildAt(index), insets);
        }

        return insets;
    }

    @Override
    public void onChildViewAdded(View parent, View child) {
        if (mInsets == null) {
            return;
        }

        ViewCompat.dispatchApplyWindowInsets(child, mInsets);
    }

    @Override
    public void onChildViewRemoved(View parent, View child) {
    }
}
