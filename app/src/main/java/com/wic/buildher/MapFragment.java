package com.wic.buildher;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.wic.buildher.widget.ContentLoadingProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A Fragment for the Map tab of the application
 */
public class MapFragment extends WatchableFragment implements
        RequestListener<Object, GlideDrawable>, GetCallback<ParseObject> {
    public static MapFragment newInstance() {
        return new MapFragment();
    }

    private static final String TAG = "MapFragment";
    private static final String MAP_URL_KEY = "Map URL";

    @BindView(R.id.loading_indicator) ContentLoadingProgressBar mLoadingIndicator;
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

        loadMapImage();
    }

    private void loadMapImage() {
        // Load image from Fresco cache if we have a URL cached
        final SharedPreferences sharedPref = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        final String localMapUrl = sharedPref.getString(MAP_URL_KEY, "");
        if (!localMapUrl.isEmpty()) {
            Log.i(TAG, "Map URL found in SharedPreferences");
            glideMapIntoPhotoView(localMapUrl);
        } else {
            Log.i(TAG, "Map URL not found in SharedPreferences");
            mLoadingIndicator.show();
        }

        ParseQuery.getQuery("Map")
                .orderByDescending("updatedAt")
                .getFirstInBackground(this);
    }

    @Override
    public void done(ParseObject result, ParseException e) {
        final SharedPreferences sharedPref = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        final String localMapUrl = sharedPref.getString(MAP_URL_KEY, "");
        if (e != null) {
            // Couldn't find map URL at all :(
            Log.i(TAG, "Unable to fetch map URL from server");
            glideMapIntoPhotoView(R.drawable.map_placeholder);
        } else {
            String serverMapUrl = result.getParseFile("picture").getUrl();
            Log.i(TAG, "Map URL from server: " + serverMapUrl);

            if (serverMapUrl.equals(localMapUrl)) {
                Log.i(TAG, "Server map URL same as local");
            } else {
                glideMapIntoPhotoView(serverMapUrl);
                sharedPref.edit().putString(MAP_URL_KEY, serverMapUrl).apply();
                Log.i(TAG, "Loaded map from server");
            }
        }
    }

    private void glideMapIntoPhotoView(final Object resource) {
        Glide.with(this)
                .load(resource)
                .listener(this)
                .into(mMap);
    }

    @Override
    public boolean onException(Exception e, Object model, Target<GlideDrawable> target, boolean
            isFirstResource) {
        e.printStackTrace();
        mLoadingIndicator.hide();
        return false;
    }

    @Override
    public boolean onResourceReady(GlideDrawable resource, Object model, Target<GlideDrawable>
            target, boolean isFromMemoryCache, boolean isFirstResource) {
        mLoadingIndicator.hide();
        return false;
    }
}
