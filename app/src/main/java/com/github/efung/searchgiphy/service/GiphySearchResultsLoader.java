package com.github.efung.searchgiphy.service;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;

import com.github.efung.searchgiphy.model.GiphyResponse;
import com.github.efung.searchgiphy.model.GiphyTranslateResponse;
import com.github.efung.searchgiphy.model.ImagesMetadata;
import com.github.efung.searchgiphy.model.Rating;
import com.github.efung.searchgiphy.model.SearchType;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import retrofit.Call;
import retrofit.Response;

/**
 * Follows Loader template of http://www.androiddesignpatterns.com/2012/08/implementing-loaders.html
 */
public class GiphySearchResultsLoader extends AsyncTaskLoader<List<ImagesMetadata>> {

    private GiphyService service;
    private SearchType searchType;
    private String searchTerm;
    private List<ImagesMetadata> mData;

    public GiphySearchResultsLoader(Context context, GiphyService service, SearchType searchType, String searchTerm) {
        super(context);
        this.service = service;
        this.searchType = searchType;
        this.searchTerm = searchTerm;
    }

    @Override
    public List<ImagesMetadata> loadInBackground() {
        if (searchType == null && TextUtils.isEmpty(searchTerm)) {
            return null;
        }
        try {
            switch (searchType) {
                case TYPE_GIFS:
                    Call<GiphyResponse> call = service.searchGifs(searchTerm, null, null, Rating.RATING_G, null);
                    Response<GiphyResponse> gifRetrofitResponse = call.execute();
                    GiphyResponse gifResponse = gifRetrofitResponse.body();
                    if (gifResponse.meta.status == 200) {
                        return gifResponse.data;
                    }
                    break;
                case TYPE_TEXT:
                    Call<GiphyTranslateResponse> translateCall = service.translateText(searchTerm, Rating.RATING_G, null);
                    Response<GiphyTranslateResponse> translateRetrofitResponse = translateCall.execute();
                    GiphyTranslateResponse translateResponse = translateRetrofitResponse.body();
                    if (translateResponse.meta.status == 200) {
                        return Collections.singletonList(translateResponse.data);
                    }
                    break;
                case TYPE_STICKERS:
                    Call<GiphyResponse> stickerCall = service.searchStickers(searchTerm, null, null, Rating.RATING_G, null);
                    Response<GiphyResponse> stickerRetrofitResponse = stickerCall.execute();
                    GiphyResponse stickerResponse = stickerRetrofitResponse.body();
                    if (stickerResponse.meta.status == 200) {
                        return stickerResponse.data;
                    }
                    break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deliverResult(List<ImagesMetadata> data) {
        if (isReset()) {
            // The Loader has been reset; ignore the result and invalidate the data.
            releaseResources(data);
            return;
        }

        // Hold a reference to the old data so it doesn't get garbage collected.
        // We must protect it until the new data has been delivered.
        List<ImagesMetadata> oldData = mData;
        mData = data;

        if (isStarted()) {
            // If the Loader is in a started state, deliver the results to the
            // client. The superclass method does this for us.
            super.deliverResult(data);
        }

        // Invalidate the old data as we don't need it any more.
        if (oldData != null && oldData != data) {
            releaseResources(oldData);
        }
    }


    @Override
    protected void onStartLoading() {
        if (mData != null) {
            // Deliver any previously loaded data immediately.
            deliverResult(mData);
        }

        if (takeContentChanged() || mData == null) {
            // When the observer detects a change, it should call onContentChanged()
            // on the Loader, which will cause the next call to takeContentChanged()
            // to return true. If this is ever the case (or if the current data is
            // null), we force a new load.
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        // The Loader is in a stopped state, so we should attempt to cancel the
        // current load (if there is one).
        cancelLoad();

        // Note that we leave the observer as is. Loaders in a stopped state
        // should still monitor the data source for changes so that the Loader
        // will know to force a new load if it is ever started again.
    }

    @Override
    protected void onReset() {
        // Ensure the loader has been stopped.
        onStopLoading();

        // At this point we can release the resources associated with 'mData'.
        if (mData != null) {
            releaseResources(mData);
            mData = null;
        }
    }


    @Override
    public void onCanceled(List<ImagesMetadata> data) {
        // Attempt to cancel the current asynchronous load.
        super.onCanceled(data);

        // The load has been canceled, so we should release the resources
        // associated with 'data'.
        releaseResources(data);
    }

    private void releaseResources(List<ImagesMetadata> data) {
        // nothing
    }

}

