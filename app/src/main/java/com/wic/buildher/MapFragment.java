package com.wic.buildher;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import bolts.Task;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.relex.photodraweeview.PhotoDraweeView;


public class MapFragment extends WatchableFragment {
    /**
     * Create a new instance of MapFragment
     */
    public static MapFragment newInstance() {
        return new MapFragment();
    }

    private static final String TAG = "MapFragment";
    private static final String MAP_URL_KEY = "Map URL";

    @BindView(R.id.map) PhotoDraweeView mMapView;
    @BindView(R.id.map2) PhotoView mPhotoView;
    Task<ParseObject> mMapUrlTask;

    public MapFragment() {
        mMapUrlTask = ParseQuery.getQuery("Map").getFirstInBackground();
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

        mPhotoView.setMaximumScale(10);

        // TODO: determine
        //loadImageFresco();
        loadImageGlide();
    }

    private void loadImageGlide() {
        // Load image from Fresco cache if we have a URL cached
        final SharedPreferences sharedPref = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        final String localMapUrl = sharedPref.getString(MAP_URL_KEY, "");
        if (!localMapUrl.isEmpty()) {
            glideLoadMap(localMapUrl);
            Log.i(TAG, "Loaded map URL from SharedPreferences");
        } else {
            // TODO: Load local default map
        }

        ParseQuery.getQuery("Map")
                .orderByDescending("updatedAt")
                .getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject parseObject, ParseException e) {
                        if (e != null) {
                            Log.i(TAG, "Unable to fetch map URL from server");
                            // Couldn't find map URL at all :(
                            return;
                        }

                        String serverMapUrl = parseObject.getParseFile("picture").getUrl();
                        if (serverMapUrl.equals(localMapUrl)) {
                            Log.i(TAG, "Server map URL same as local");
                        } else {
                            glideLoadMap(serverMapUrl);
                            sharedPref.edit().putString(MAP_URL_KEY, serverMapUrl).apply();
                            Log.i(TAG, "Got map URL from server!");
                        }
                    }
                });
    }

    private void glideLoadMap(String url) {
        Glide
                .with(MapFragment.this)
                .load(url)
                .placeholder(R.drawable.ic_person_white_24dp)
                .dontTransform()
                .into(mPhotoView);
    }

    private void loadImageFresco() {
        // Load image from Fresco cache if we have a URL cached
        final SharedPreferences sharedPref = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        final String localMapUrl = sharedPref.getString(MAP_URL_KEY, "");
        if (!localMapUrl.isEmpty()) {
            mMapView.setPhotoUri(Uri.parse(localMapUrl));
            Log.i(TAG, "Loaded map URL from SharedPreferences");
        }

        ParseQuery.getQuery("Map")
                .orderByDescending("updatedAt")
                .getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject parseObject, ParseException e) {
                        if (e != null) {
                            Log.i(TAG, "Unable to fetch map URL from server");
                            // Couldn't find map URL at all :(
                            return;
                        }

                        String serverMapUrl = parseObject.getParseFile("picture").getUrl();
                        if (serverMapUrl.equals(localMapUrl)) {
                            Log.i(TAG, "Server map URL same as local");
                        } else {
                            mMapView.setPhotoUri(Uri.parse(serverMapUrl));
                            sharedPref.edit().putString(MAP_URL_KEY, serverMapUrl).apply();
                            Log.i(TAG, "Got map URL from server!");
                        }
                    }
                });
    }

}
