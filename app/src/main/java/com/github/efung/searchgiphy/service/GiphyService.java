package com.github.efung.searchgiphy.service;

import com.github.efung.searchgiphy.model.GiphyTranslateResponse;
import com.github.efung.searchgiphy.model.Rating;
import com.github.efung.searchgiphy.model.GiphyResponse;

import retrofit.Call;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * @author efung on 2015 Sep 15
 */
public interface GiphyService {
    @GET("/v1/gifs/search")
    Call<GiphyResponse> searchGifs(
            @Query("q") String searchTerm,
            @Query("limit") Integer limit,
            @Query("offset") Integer offset,
            @Query("rating") Rating rating,
            @Query("fmt") String format);

    @GET("/v1/gifs/translate")
    Call<GiphyTranslateResponse> translateText(
            @Query("s") String searchTerm,
            @Query("rating") Rating rating,
            @Query("fmt") String format);

    @GET("/v1/stickers/search")
    Call<GiphyResponse> searchStickers(
            @Query("q") String searchTerm,
            @Query("limit") Integer limit,
            @Query("offset") Integer offset,
            @Query("rating") Rating rating,
            @Query("fmt") String format);
}
