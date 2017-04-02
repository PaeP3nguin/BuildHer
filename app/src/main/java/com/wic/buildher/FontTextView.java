package com.wic.buildher;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import java.util.HashMap;


public class FontTextView extends android.support.v7.widget.AppCompatTextView {

    private static final HashMap<String, Typeface> cache = new HashMap<String, Typeface>();

    public FontTextView(Context context) {
        super(context);
        init();
    }

    public FontTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FontTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }
}
