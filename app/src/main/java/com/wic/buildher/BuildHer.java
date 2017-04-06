package com.wic.buildher;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class BuildHer extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AndroidThreeTen.init(this);

        Parse.initialize(this);
        ParseObject.registerSubclass(Update.class);
        ParseObject.registerSubclass(ScheduleItem.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Muli.ttf").build());
    }
}
