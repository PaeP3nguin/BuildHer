package com.wic.buildher.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.wic.buildher.R;

/**
 * FrameLayout that saves window insets and dispatches them to all new children
 * Suitable as a fragment container with fragments that use fitsSystemWindows
 */
public class InsetFrameLayout extends FrameLayout implements ViewGroup
        .OnHierarchyChangeListener, OnApplyWindowInsetsListener {
    private WindowInsetsCompat mInsets;

    public InsetFrameLayout(Context context) {
        this(context, null);
    }

    public InsetFrameLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InsetFrameLayout(Context context, @Nullable AttributeSet attrs, int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.InsetFrameLayout, 0, 0);
        if (array.getBoolean(R.styleable.InsetFrameLayout_dispatchInsetsOnChildAdded, false)) {
            setOnHierarchyChangeListener(this);
        }
        ViewCompat.setOnApplyWindowInsetsListener(this, this);
    }

    @Override
    public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
        if (insetEquals(insets, mInsets)) {
            return insets.consumeSystemWindowInsets();
        }

        mInsets = insets;

        for (int index = 0; index < getChildCount(); index++) {
            ViewCompat.dispatchApplyWindowInsets(getChildAt(index), insets);
        }

        return insets;
    }

    private boolean insetEquals(WindowInsetsCompat insets1, WindowInsetsCompat insets2) {
        if (insets1 == null || insets2 == null) {
            return false;
        }

        if (!insets1.hasInsets() && !insets2.hasInsets()) {
            return true;
        }

        return insets1.getSystemWindowInsetTop() == insets2.getSystemWindowInsetTop()
                && insets1.getStableInsetBottom() == insets2.getStableInsetBottom()
                && insets1.getSystemWindowInsetLeft() == insets2.getSystemWindowInsetLeft()
                && insets1.getSystemWindowInsetRight() == insets2.getSystemWindowInsetRight();
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
