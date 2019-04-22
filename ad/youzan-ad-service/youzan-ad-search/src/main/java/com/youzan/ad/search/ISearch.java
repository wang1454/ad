package com.youzan.ad.search;

import com.youzan.ad.search.vo.SearchRequest;
import com.youzan.ad.search.vo.SearchResponse;

public interface ISearch {

    SearchResponse fatchAds(SearchRequest request);

}
