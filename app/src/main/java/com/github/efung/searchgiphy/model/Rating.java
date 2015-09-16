package com.github.efung.searchgiphy.model;

/**
 * @author efung on 2015 Sep 15
 */
public enum Rating {
    RATING_Y("y"),
    RATING_G("g"),
    RATING_PG("pg"),
    RATING_PG13("pg-13"),
    RATING_R("r");

    private String rating;
    Rating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return rating;
    }
}
