package com.github.efung.searchgiphy.util;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.ColorInt;

import com.github.efung.searchgiphy.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by efung on 15-09-30.
 */
public class DrawableHelper {
    private static Map<Integer, ColorDrawable> CACHE = new HashMap<>(16);
    private static final Random rng = new Random();

    public static ColorDrawable getRandomColorDrawable(Context context) {
        int randomColor = getRandomColor(context);
        if (!CACHE.containsKey(randomColor)) {
            CACHE.put(randomColor, new ColorDrawable(randomColor));
        }
        return CACHE.get(randomColor);
    }

    public static @ColorInt int getRandomColor(Context context) {
        int[] randomColors = context.getResources().getIntArray(R.array.placeholderColors);
        int i = rng.nextInt(randomColors.length);
        return randomColors[i];
    }
}
