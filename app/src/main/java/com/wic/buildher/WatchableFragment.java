package com.wic.buildher;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A Fragment that allows listeners to listen to its lifecycle
 * Only supports {@link #onCreateView} right now
 * Definitely an ENFP
 */
public class WatchableFragment extends Fragment {

    public static final int ON_CREATE_VIEW = 0;

    public interface OnLifecycleListener {
        void onLifecycleEvent(int event);
    }

    private List<OnLifecycleListener> mListenerList;

    public WatchableFragment() {
        mListenerList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle
            savedInstanceState) {
        // Notify all of our listeners at home
        for (OnLifecycleListener listener : mListenerList) {
            listener.onLifecycleEvent(ON_CREATE_VIEW);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * Add a listener to listen for Fragment lifecycle events
     */
    public void addOnLifecycleListener(OnLifecycleListener listener) {
        mListenerList.add(listener);
    }
}
