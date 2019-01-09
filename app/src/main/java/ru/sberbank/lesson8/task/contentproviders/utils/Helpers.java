package ru.sberbank.lesson8.task.contentproviders.utils;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

public class Helpers {
    public static final int TEXT_SIZE_STEP = 1;
    public static final int TEXT_SIZE_MAX = 50;
    public static final int TEXT_SIZE_MIN = 18;

    public static String getRealProgress(int value) {
        return String.valueOf(TEXT_SIZE_MIN + (value * TEXT_SIZE_STEP));
    }

    public static int getBackgroundColor(View v) {
        return ((ColorDrawable)v.getBackground()).getColor();
    }

    public static String getSettingName(Resources resources, int id) {
        return resources.getString(id);
    }
}
