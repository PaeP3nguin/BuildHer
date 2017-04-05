package com.wic.buildher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A Fragment for the Map tab of the application
 */
public class MapFragment extends WatchableFragment {
    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @BindView(R.id.map) PhotoView mMap;

    public MapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Call to super required for WatchableFragment to know
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMap.setMaximumScale(10);
    }

    @OnClick({R.id.show_campus_map, R.id.show_tech_1_map, R.id.show_tech_3_map})
    public void switchMap(View view) {
        int id = view.getId();
        if (id == R.id.show_campus_map) {
            mMap.setImageResource(R.drawable.map_campus);
        } else if (id == R.id.show_tech_1_map) {
            mMap.setImageResource(R.drawable.map_tech_1);
        } else if (id == R.id.show_tech_3_map) {
            mMap.setImageResource(R.drawable.map_tech_3);
        }
    }
}
