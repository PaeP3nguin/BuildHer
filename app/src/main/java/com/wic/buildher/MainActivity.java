package com.wic.buildher;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements WatchableFragment
        .OnLifecycleListener {

    @BindView(R.id.main_view) View mMainView;
    @BindView(R.id.bottom_nav) BottomBar mBottomNav;
    @BindView(R.id.background_overlay) SurfaceView mBackgroundOverlay;
    @BindView(R.id.color_overlay) View mColorOverlay;
    @BindView(R.id.fragment_container) View mFragmentContainer;
    @IdRes int mCurrTab;
    SurfaceHolder mBackgroundSurface;

    @ColorInt int backgroundColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBackgroundSurface = mBackgroundOverlay.getHolder();
            mBackgroundOverlay.setVisibility(View.VISIBLE);
        }

        mBottomNav.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                mCurrTab = tabId;
                WatchableFragment fragment = null;
                int oldColor = backgroundColor;
                if (tabId == R.id.home) {
                    fragment = HomeFragment.newInstance();
                    backgroundColor = getResources().getColor(R.color.backgroundBlue);
                } else if (tabId == R.id.schedule) {
                    fragment = ScheduleFragment.newInstance();
                    backgroundColor = getResources().getColor(R.color.backgroundPurple);
                } else if (tabId == R.id.map) {
                    fragment = MapFragment.newInstance();
                    backgroundColor = getResources().getColor(R.color.backgroundGreen);
                } else if (tabId == R.id.updates) {
                    fragment = UpdatesFragment.newInstance();
                    backgroundColor = getResources().getColor(R.color.backgroundBlue);
                } else if (tabId == R.id.sponsors) {
                    fragment = SponsorFragment.newInstance();
                    backgroundColor = getResources().getColor(R.color.backgroundPurple);
                }
                assert fragment != null;

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // Prepare for circular reveal by getting an image of the current Fragment and
                    // showing it in the background so the new Fragment can be revealed on top of it

                    if (mFragmentContainer.getWidth() > 0) {
                        // Only run if not the initial call of setOnTabSelectListener in onCreate
                        Canvas fragmentCanvas = mBackgroundSurface.lockCanvas();
                        fragmentCanvas.drawColor(oldColor);
                        mFragmentContainer.draw(fragmentCanvas);
                        mBackgroundSurface.unlockCanvasAndPost(fragmentCanvas);
                        fragment.addOnLifecycleListener(MainActivity.this);
                    } else {
                        // Set background color in initial callback
                        mColorOverlay.setBackgroundColor(backgroundColor);
                    }
                } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    // Nice fade when circular reveal not possible
                    transaction.setCustomAnimations(android.R.animator.fade_in,
                            android.R.animator.fade_out);
                    // TODO: Animate background color change
                }
                transaction.replace(R.id.fragment_container, fragment).commit();
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onPause() {
        Fresco.getImagePipeline().clearCaches();

        super.onPause();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onLifecycleEvent(int event) {
        if (event != WatchableFragment.ON_CREATE_VIEW) return;

        View tab = findViewById(mCurrTab);
        int[] loc = new int[2];
        tab.getLocationOnScreen(loc);
        int centerX = loc[0] + tab.getMeasuredWidth() / 2;
        int centerY = loc[1] + tab.getMeasuredHeight() / 2;
        int startRadius = 0;
        int finalRadius = (int) Math.hypot(mBackgroundOverlay.getWidth(),
                mBackgroundOverlay.getHeight());

        Animator fragmentReveal = ViewAnimationUtils.createCircularReveal(
                mFragmentContainer, centerX, centerY, startRadius, finalRadius);
        //        fragmentReveal.setInterpolator(new DecelerateInterpolator(2f));
        fragmentReveal.setDuration(400);

        mColorOverlay.setBackgroundColor(backgroundColor);

        Animator colorReveal = ViewAnimationUtils.createCircularReveal(
                mColorOverlay, centerX, centerY, startRadius, finalRadius);
        colorReveal.setDuration(400);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(fragmentReveal, colorReveal);
        animatorSet.start();
    }
}
