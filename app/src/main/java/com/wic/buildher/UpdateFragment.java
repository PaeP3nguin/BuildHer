package com.wic.buildher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

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

    @BindView(R.id.update_list) ListView mUpdatesList;

    private ParseQuery<Update> mUpdateQuery;

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
        mUpdateQuery
                .findInBackground(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mUpdateQuery.cancel();
    }

    @Override
    public void done(List<Update> list, ParseException e) {
        if (list == null || e != null) {
            return;
        }
        if (isDetached() || getActivity() == null) {
            return;
        }

        mUpdatesList.setAdapter(new UpdateListAdapter(getActivity(), list));
    }
}
