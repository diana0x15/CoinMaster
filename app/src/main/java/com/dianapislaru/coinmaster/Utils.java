package com.dianapislaru.coinmaster;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

/**
 * Created by Diana on 08/10/2016.
 */

public class Utils {

    public static int getDisplayHeight(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    public static int getDisplayWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }
}
