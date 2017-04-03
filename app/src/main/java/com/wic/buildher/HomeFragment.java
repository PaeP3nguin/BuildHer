package com.wic.buildher;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.Month;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.ChronoUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fragment for the Home tab of the application
 */
public class HomeFragment extends WatchableFragment {
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    private static final long EIGHTEEN_HOURS = 18 * 60 * 60 * 1000;

    @BindView(R.id.countdown) TextView mCountdown;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Call to super required for WatchableFragment to know
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LocalDateTime hackathonStart = LocalDateTime.of(2017, Month.APRIL, 7, 8, 0);

        long msUntilHackathon = ChronoUnit.MILLIS.between(LocalDateTime.now(),
                hackathonStart);
        if (msUntilHackathon <= 0) {
            mCountdown.setText("00:00:00");
        } else {
            new CountDownTimer(msUntilHackathon, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    LocalTime displayTime;
                    if (millisUntilFinished <= EIGHTEEN_HOURS) {
                        displayTime = LocalTime.ofSecondOfDay(millisUntilFinished / 1000);
                    } else {
                        displayTime = LocalTime.of(18, 0);
                    }
                    mCountdown.setText(displayTime.format(DateTimeFormatter.ISO_LOCAL_TIME));
                }

                @Override
                public void onFinish() {
                    mCountdown.setText("00:00:00");
                }
            }.start();
        }
    }
}
