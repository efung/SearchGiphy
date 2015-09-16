package com.github.efung.searchgiphy;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.RadioGroup;
import android.widget.SearchView;

import com.github.efung.searchgiphy.model.SearchType;

import static com.github.efung.searchgiphy.model.SearchType.fromId;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, RadioGroup.OnCheckedChangeListener {
    private SearchView searchView;
    private RadioGroup searchTypeRadio;
    public static final String FRAGMENT_TAG = "searchFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchView = (SearchView) toolbar.findViewById(R.id.search);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        searchTypeRadio = (RadioGroup) toolbar.findViewById(R.id.type);
        searchTypeRadio.setOnCheckedChangeListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, createSearchFragment(), FRAGMENT_TAG).commit();
    }

    private Fragment createSearchFragment() {
        return SearchFragment.newInstance();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Fragment f = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (f != null) {
            SearchFragment sf = (SearchFragment)f;
            sf.setSearchTerm(query);
            sf.setSearchType(getSearchType());
            sf.executeSearch();
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return true;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Fragment f = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
        if (f != null) {
            SearchFragment sf = (SearchFragment)f;
            sf.setSearchType(getSearchType());
            sf.executeSearch();
        }
    }

    private SearchType getSearchType() {
        return fromId(searchTypeRadio.getCheckedRadioButtonId());
    }
}
