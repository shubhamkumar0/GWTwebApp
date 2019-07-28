package com.example.client;

import com.example.server.search.SearchRequest;
import com.example.server.search.SearchResponse;
import com.google.gwt.user.client.rpc.AsyncCallback;


public interface SearchServiceAsync {
    void search(SearchRequest searchRequest, AsyncCallback<SearchResponse> async);
}
