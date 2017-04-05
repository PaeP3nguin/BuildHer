package com.wic.buildher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;


/**
 * A Fragment for the Schedule tab of the application
 */
public class ScheduleFragment extends WatchableFragment implements FindCallback<ScheduleItem> {
    public static ScheduleFragment newInstance() {
        return new ScheduleFragment();
    }

    @BindView(R.id.schedule) StickyListHeadersListView mSchedule;

    private ParseQuery<ScheduleItem> mScheduleQuery;
    private ScheduleItemAdapter mScheduleItemAdapter;

    public ScheduleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Call to super required for WatchableFragment to know
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_schedule, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mScheduleQuery = ParseQuery.getQuery(ScheduleItem.class)
                .orderByAscending("order")
                .setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        mScheduleQuery.findInBackground(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mScheduleQuery.cancel();
    }

    @Override
    public void done(List<ScheduleItem> list, ParseException e) {
        if (list == null || e != null) {
            return;
        }
        if (isDetached() || getActivity() == null) {
            return;
        }

        if (mScheduleItemAdapter == null) {
            showSchedule(list);
        } else {
            if (mScheduleItemAdapter.getCount() != list.size()) {
                // Server has diff number of updates, show the server's updates
                showSchedule(list);
            } else {
                // Check if there are any updates that aren't the same
                for (int i = 0; i < list.size(); i++) {
                    if (!list.get(i).hasSameId(mScheduleItemAdapter.getItem(i))) {
                        showSchedule(list);
                        return;
                    }
                }
            }
        }
    }

    public void showSchedule(List<ScheduleItem> list) {
        if (mScheduleItemAdapter == null) {
            mScheduleItemAdapter = new ScheduleItemAdapter(getActivity(), list);
            mSchedule.setAdapter(mScheduleItemAdapter);
        } else {
            mScheduleItemAdapter.setNotifyOnChange(false);
            mScheduleItemAdapter.clear();
            mScheduleItemAdapter.addAll(list);
            mScheduleItemAdapter.notifyDataSetChanged();
        }
    }
}
