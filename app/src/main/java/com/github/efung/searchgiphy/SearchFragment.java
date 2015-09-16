package com.github.efung.searchgiphy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.efung.searchgiphy.adapter.GiphyResultAdapter;
import com.github.efung.searchgiphy.model.GiphyResponse;
import com.github.efung.searchgiphy.model.ImagesMetadata;
import com.github.efung.searchgiphy.model.SearchType;
import com.github.efung.searchgiphy.service.ApiKeyQueryParamInterceptor;
import com.github.efung.searchgiphy.service.GiphyService;
import com.squareup.okhttp.OkHttpClient;

import java.util.Collections;

import retrofit.Call;
import retrofit.Callback;
import retrofit.MoshiConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static java.util.Collections.EMPTY_LIST;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchFragment extends Fragment {
    public static final String ARG_SEARCH = "search";
    private RecyclerView resultsView;
    private String currentSearchTerm;
    private SearchType currentSearchType;
    private GiphyService service;

    public static SearchFragment newInstance() {
        SearchFragment f = new SearchFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_SEARCH, searchTerm);
//        f.setArguments(args);
        return f;
    }
    public SearchFragment() {
        currentSearchTerm = null;
        currentSearchType = null;
        service = createService();
    }

    private GiphyService createService() {
        OkHttpClient client = new OkHttpClient();
        client.networkInterceptors().add(new ApiKeyQueryParamInterceptor("dc6zaTOxFJmzC"));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.giphy.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .client(client)
                .build();
        return retrofit.create(GiphyService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search, container, false);
        resultsView = (RecyclerView) v.findViewById(R.id.results);
        resultsView.setAdapter(new GiphyResultAdapter(container.getContext(), Collections.<ImagesMetadata>emptyList()));
        resultsView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle args = getArguments();
        if (args != null && args.containsKey(ARG_SEARCH)) {
            setSearchTerm(args.getString(ARG_SEARCH));
        }
    }

    public void setSearchTerm(String searchTerm) {
        currentSearchTerm = searchTerm;
    }

    public void setSearchType(SearchType type) {
        currentSearchType = type;
    }

    public void executeSearch() {
        if (TextUtils.isEmpty(currentSearchTerm) || currentSearchType == null) {
            return;
        }
        Call<GiphyResponse> call = null;
        switch (currentSearchType) {
            case TYPE_GIFS:
                call = service.searchGifs(currentSearchTerm, null, null, null, null);
                break;
            case TYPE_TEXT:
                call = service.translateText(currentSearchTerm, null, null);
                break;
            case TYPE_STICKERS:
                call = service.searchStickers(currentSearchTerm, null, null, null, null);
                break;
        }

        call.enqueue(new ResponseCallback());
    }

    private class ResponseCallback implements Callback<GiphyResponse> {
        @Override
        public void onResponse(Response<GiphyResponse> retrofitResponse) {
            GiphyResponse response = retrofitResponse.body();
            if (response.meta.status == 200) {
                ((GiphyResultAdapter)resultsView.getAdapter()).setItems(response.data);
            }
        }

        @Override
        public void onFailure(Throwable t) {

        }

    }
}
