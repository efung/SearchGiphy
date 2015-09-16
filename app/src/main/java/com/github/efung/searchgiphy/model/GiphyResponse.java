package com.github.efung.searchgiphy.model;

import java.util.List;

/**
 * @author efung on 2015 Sep 15
 */
public class GiphyResponse {
    public List<ImagesMetadata> data;
    public Status meta;
    public Pagination pagination;
}
