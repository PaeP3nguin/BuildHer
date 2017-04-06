package com.wic.buildher;

import android.animation.Animator;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity
        implements WatchableFragment.OnLifecycleListener, OnTabSelectListener {
    @BindView(R.id.bottom_nav) BottomBar mBottomNav;
    @BindView(R.id.fragment_container) View mFragmentContainer;
    @BindView(R.id.background_overlay) SurfaceView mBackgroundOverlay;
    private SurfaceHolder mBackgroundSurface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBackgroundSurface = mBackgroundOverlay.getHolder();
            mBackgroundOverlay.setVisibility(View.VISIBLE);
        }

        mBottomNav.setOnTabSelectListener(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onTabSelected(int tabId) {
        WatchableFragment fragment = null;
        if (tabId == R.id.home) {
            fragment = HomeFragment.newInstance();
        } else if (tabId == R.id.schedule) {
            fragment = ScheduleFragment.newInstance();
        } else if (tabId == R.id.map) {
            fragment = MapFragment.newInstance();
        } else if (tabId == R.id.updates) {
            fragment = UpdateFragment.newInstance();
        } else if (tabId == R.id.sponsors) {
            fragment = SponsorFragment.newInstance();
        }
        assert fragment != null;

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Prepare for circular reveal by getting an image of the current Fragment and
            // showing it in the background so the new Fragment can be revealed on top of it

            if (mFragmentContainer.getWidth() > 0) {
                // Only run if not the initial call of setOnTabSelectListener in onCreate
                Canvas fragmentCanvas = mBackgroundSurface.lockCanvas();
                mFragmentContainer.draw(fragmentCanvas);
                mBackgroundSurface.unlockCanvasAndPost(fragmentCanvas);
                fragment.addOnLifecycleListener(MainActivity.this);
            }
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Nice fade when circular reveal not possible
            transaction.setCustomAnimations(android.R.animator.fade_in,
                    android.R.animator.fade_out);
        }
        transaction.replace(R.id.fragment_container, fragment).commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onLifecycleEvent(int event) {
        if (event != WatchableFragment.ON_CREATE_VIEW) return;

        BottomBarTab tab = mBottomNav.getCurrentTab();
        int[] loc = new int[2];
        tab.getLocationOnScreen(loc);
        int centerX = loc[0] + tab.getMeasuredWidth() / 2;
        int centerY = loc[1] + tab.getMeasuredHeight() / 2;
        int startRadius = 0;
        int finalRadius = (int) Math.hypot(mFragmentContainer.getWidth(),
                mFragmentContainer.getHeight());

        Animator fragmentReveal = ViewAnimationUtils.createCircularReveal(
                mFragmentContainer, centerX, centerY, startRadius, finalRadius);
        fragmentReveal.setDuration(400).start();
    }
}
