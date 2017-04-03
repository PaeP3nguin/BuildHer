package com.wic.buildher;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.parse.Parse;
import com.parse.ParseObject;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class BuildHer extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
        ParseObject.registerSubclass(Update.class);
        Parse.initialize(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Muli.ttf")
                .build());
    }
}
