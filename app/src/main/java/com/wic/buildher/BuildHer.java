package com.wic.buildher;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.parse.Parse;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class BuildHer extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this);
        Fresco.initialize(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Muli.ttf")
                .build());
    }
}
