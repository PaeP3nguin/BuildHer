package com.wic.buildher;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.Locale;

/**
 * Class representing an item in a schedule
 */
@ParseClassName("Schedule")
public class ScheduleItem extends ParseObject {
    private static DateTimeFormatter TIME_FORMATTER;

    public ScheduleItem() {
        super();

        if (TIME_FORMATTER == null) {
            TIME_FORMATTER = DateTimeFormatter.ofPattern("h:mm a", Locale.getDefault());
        }
    }

    public String getDay() {
        return getString("day");
    }

    public LocalTime getTime() {
        return LocalTime.parse(getString("time"), TIME_FORMATTER);
    }

    public String getTitle() {
        return getString("title");
    }

    public String getDescription() {
        return getString("description");
    }
}
