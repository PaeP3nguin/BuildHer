package com.wic.buildher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.wic.buildher.widget.ContentLoadingProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A Fragment for the Updates tab of the application
 */
public class UpdateFragment extends WatchableFragment implements FindCallback<Update> {
    public static UpdateFragment newInstance() {
        return new UpdateFragment();
    }

    @BindView(R.id.updates) RecyclerView mUpdates;
    @BindView(R.id.loading) ContentLoadingProgressBar mLoading;
    @BindView(R.id.loading_failed) TextView mLoadingFailed;

    private ParseQuery<Update> mUpdateQuery;
    private UpdateAdapter mUpdateAdapter;
    private List<Update> mUpdateList;

    public UpdateFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Call to super required for WatchableFragment to know
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_updates, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mUpdateList = new ArrayList<>();

        mUpdates.setLayoutManager(new LinearLayoutManager(getActivity()));
        mUpdateAdapter = new UpdateAdapter(getActivity(), mUpdateList);
        mUpdateAdapter.setHasStableIds(true);
        mUpdates.setAdapter(mUpdateAdapter);

        mUpdateQuery = ParseQuery.getQuery(Update.class)
                .orderByDescending("createdAt")
                .setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        mUpdateQuery.findInBackground(this);
        mLoading.show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mUpdateQuery.cancel();
    }

    @Override
    public void done(List<Update> list, ParseException e) {
        if (e != null) {
            if (e.getCode() == ParseException.CONNECTION_FAILED) {
                mLoading.hide();
                mLoadingFailed.setVisibility(View.VISIBLE);
            }
            return;
        }
        if (list == null) {
            return;
        }
        if (isDetached() || getActivity() == null) {
            return;
        }

        mLoading.hide();
        mUpdateAdapter.swap(list);
    }
}
