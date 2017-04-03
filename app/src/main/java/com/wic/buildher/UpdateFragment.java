package com.wic.buildher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A Fragment for the Updates tab of the application
 */
public class UpdateFragment extends WatchableFragment {
    /**
     * Class representing an update
     */
    public static class Update {
        public String subject;
        public String message;
        public Date time;
    }

    public static UpdateFragment newInstance() {
        return new UpdateFragment();
    }

    @BindView(R.id.update_list) ListView mUpdatesList;

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

    }
}
