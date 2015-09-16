package com.github.efung.searchgiphy.model;

import android.support.annotation.IdRes;

import com.github.efung.searchgiphy.R;

/**
 * @author efung on 2015 Sep 16
 */
public enum SearchType {
    TYPE_GIFS(R.id.type_gifs),
    TYPE_TEXT(R.id.type_text),
    TYPE_STICKERS(R.id.type_stickers);

    private int id;

    SearchType(@IdRes int id) {
        this.id = id;
    }

    public static SearchType fromId(@IdRes int fromId) {
        for (SearchType t : values()) {
            if (t.id == fromId) {
                return t;
            }
        }
        return null;
    }
}
