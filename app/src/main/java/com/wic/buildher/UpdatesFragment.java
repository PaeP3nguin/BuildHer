package com.wic.buildher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;


/**
 * A Fragment for the Updates view of the application
 */
public class UpdatesFragment extends WatchableFragment {
    public UpdatesFragment() {
        // Required empty public constructor
    }

    /**
     * Create a new UpdatesFragment
     */
    public static UpdatesFragment newInstance() {
        return new UpdatesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Call to super required for WatchableFragment to know
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_updates, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
