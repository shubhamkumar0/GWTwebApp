package com.example.client;

import com.example.shared.search.SearchRequest;
import com.example.shared.search.SearchResponse;


public interface SearchServiceAsync {
    void search(SearchRequest searchRequest, com.google.gwt.user.client.rpc.AsyncCallback<SearchResponse> arg4);
}
