package com.wic.buildher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.wic.buildher.widget.ContentLoadingProgressBar;

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

    @BindView(R.id.updates) ListView mUpdates;
    @BindView(R.id.loading) ContentLoadingProgressBar mLoading;
    @BindView(R.id.loading_failed) TextView mLoadingFailed;

    private ParseQuery<Update> mUpdateQuery;
    private UpdateAdapter mUpdateAdapter;

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

        if (mUpdateAdapter == null) {
            showUpdates(list);
        } else {
            if (mUpdateAdapter.getCount() != list.size()) {
                // Server has diff number of updates, show the server's updates
                showUpdates(list);
            } else {
                // Check if there are any updates that aren't the same
                for (int i = 0; i < list.size(); i++) {
                    if (!list.get(i).hasSameId(mUpdateAdapter.getItem(i))) {
                        showUpdates(list);
                        return;
                    }
                }
            }
        }
    }

    public void showUpdates(List<Update> list) {
        mLoading.hide();
        if (mUpdateAdapter == null) {
            mUpdateAdapter = new UpdateAdapter(getActivity(), list);
            mUpdates.setAdapter(mUpdateAdapter);
        } else {
            mUpdateAdapter.setNotifyOnChange(false);
            mUpdateAdapter.clear();
            mUpdateAdapter.addAll(list);
            mUpdateAdapter.notifyDataSetChanged();
        }
    }
}
