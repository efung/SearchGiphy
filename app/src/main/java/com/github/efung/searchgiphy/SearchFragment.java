package com.github.efung.searchgiphy;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.efung.searchgiphy.adapter.GiphyResultAdapter;
import com.github.efung.searchgiphy.adapter.ImagesMetadataViewHolder;
import com.github.efung.searchgiphy.adapter.OnItemClickListener;
import com.github.efung.searchgiphy.model.ImagesMetadata;
import com.github.efung.searchgiphy.model.SearchType;
import com.github.efung.searchgiphy.service.ApiKeyQueryParamInterceptor;
import com.github.efung.searchgiphy.service.GiphySearchResultsLoader;
import com.github.efung.searchgiphy.service.GiphyService;
import com.squareup.okhttp.OkHttpClient;

import java.util.Collections;
import java.util.List;

import retrofit.MoshiConverterFactory;
import retrofit.Retrofit;

/**
 * A placeholder fragment containing a simple view.
 */
public class SearchFragment extends Fragment implements OnItemClickListener<ImagesMetadataViewHolder>, LoaderManager.LoaderCallbacks<List<ImagesMetadata>> {
    public static final String ARG_SEARCH = "search";
    private RecyclerView resultsView;
    private GiphyResultAdapter adapter;
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
        adapter = getAdapter(container);
        adapter.setOnItemClickListener(this);
        resultsView.setAdapter(adapter);
        resultsView.setLayoutManager(getLayoutManager());
        return v;
    }

    protected RecyclerView.LayoutManager getLayoutManager() {
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

    protected GiphyResultAdapter getAdapter(ViewGroup container) {
        return new GiphyResultAdapter(container.getContext(), Collections.<ImagesMetadata>emptyList());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(0, null, this);
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
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public void onItemClick(View itemView, ImagesMetadataViewHolder viewHolder, int adapterPosition) {
    }

    @Override
    public Loader<List<ImagesMetadata>> onCreateLoader(int id, Bundle args) {
        return new GiphySearchResultsLoader(getActivity(), service, currentSearchType, currentSearchTerm);
    }

    @Override
    public void onLoadFinished(Loader<List<ImagesMetadata>> loader, List<ImagesMetadata> data) {
        ((GiphyResultAdapter)resultsView.getAdapter()).setItems(data);
    }

    @Override
    public void onLoaderReset(Loader<List<ImagesMetadata>> loader) {

    }

}
