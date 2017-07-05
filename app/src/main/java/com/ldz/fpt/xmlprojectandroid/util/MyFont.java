package com.ldz.fpt.xmlprojectandroid.util;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by linhdq on 7/1/17.
 */

public class MyFont {
    private Typeface extraLight;
    private Typeface light;
    private Typeface regular;
    private Typeface extraBold;
    private Typeface lightItalic;

    public MyFont(Context context) {
        extraLight = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-ExtraLight.ttf");
        light = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Light.ttf");
        regular = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Regular.ttf");
        extraBold = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-ExtraBold.ttf");
        lightItalic = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-LightItalic.ttf");
    }

    private static MyFont inst;

    public static MyFont getInst(Context context) {
        if (inst == null) {
            inst = new MyFont(context);
        }
        return inst;
    }

    public Typeface getExtraLight() {
        return extraLight;
    }

    public Typeface getLight() {
        return light;
    }

    public Typeface getRegular() {
        return regular;
    }

    public Typeface getExtraBold() {
        return extraBold;
    }

    public Typeface getLightItalic() {
        return lightItalic;
    }
}
