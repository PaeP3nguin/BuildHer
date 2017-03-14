package com.wic.buildher;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    static final private SparseIntArray mTabColors = new SparseIntArray();
    static {
        mTabColors.append(R.id.home, R.color.homeTab);
        mTabColors.append(R.id.schedule, R.color.scheduleTab);
        mTabColors.append(R.id.map, R.color.mapTab);
        mTabColors.append(R.id.updates, R.color.updatesTab);
        mTabColors.append(R.id.sponsors, R.color.sponsorsTab);
    }

    @BindView(R.id.bottom_nav) BottomBar mBottomNav;
    @BindView(R.id.main_view) ViewGroup mMainView;
    @BindView(R.id.background_overlay) View mBackgroundOverlay;
    int mCurrBackgroundColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mCurrBackgroundColor = getResources().getColor(R.color.homeTab);
        mMainView.setBackgroundColor(mCurrBackgroundColor);

        mBottomNav.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                animateBgColorChange(tabId);
            }
        }, false);
    }

    private void animateBgColorChange(@IdRes int tabId) {
        final int targetColor = getResources().getColor(mTabColors.get(tabId));
        mMainView.clearAnimation();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            interpolateBgColorChange(targetColor);
        } else {
            circularBgColorChange(tabId, targetColor);
        }
    }

    private void interpolateBgColorChange(@ColorInt final int targetColor) {
        final ObjectAnimator backgroundColorAnimator = ObjectAnimator.ofObject(mMainView,
                "backgroundColor", new ArgbEvaluator(), mCurrBackgroundColor, targetColor);
        backgroundColorAnimator.setDuration(300);
        backgroundColorAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                onEnd();
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                onEnd();
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }

            private void onEnd() {
                mCurrBackgroundColor = targetColor;
                mMainView.setBackgroundColor(mCurrBackgroundColor);
            }
        });
        backgroundColorAnimator.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void circularBgColorChange(@IdRes int tabId, final int newColor) {
        View tab = findViewById(tabId);

        mBackgroundOverlay.clearAnimation();
        mBackgroundOverlay.setBackgroundColor(newColor);
        mBackgroundOverlay.setVisibility(View.VISIBLE);

        int[] loc = new int[2];
        tab.getLocationOnScreen(loc);
        int centerX = loc[0] + tab.getMeasuredWidth() / 2;
        int centerY = loc[1] + tab.getMeasuredHeight() / 2;
        int startRadius = 0;
        int finalRadius = Math.max(mMainView.getWidth(), mMainView.getHeight());

        Animator animator = ViewAnimationUtils.createCircularReveal(
                mBackgroundOverlay,
                centerX,
                centerY,
                startRadius,
                finalRadius
        );

        animator.setDuration(300);

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                onEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                onEnd();
            }

            private void onEnd() {
                mMainView.setBackgroundColor(newColor);
                mBackgroundOverlay.setVisibility(View.INVISIBLE);
            }
        });

        animator.start();
    }
}
