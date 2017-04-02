package com.wic.buildher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;


/**
 * A Fragment for the Sponsors view of the application
 */
public class SponsorsFragment extends WatchableFragment {
    public SponsorsFragment() {
        // Required empty public constructor
    }

    /**
     * Create a new Sponsors
     */
    public static SponsorsFragment newInstance() {
        return new SponsorsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Call to super required for WatchableFragment to know
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
