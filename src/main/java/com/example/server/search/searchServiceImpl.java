package com.example.server.search;

import com.example.client.SearchService;
import com.example.shared.search.SearchRequest;
import com.example.shared.search.SearchResponse;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;


public class searchServiceImpl extends RemoteServiceServlet implements SearchService {
    public SearchResponse search(SearchRequest searchRequest) {
        SearchByName searchByName = new SearchByName();
        return searchByName.search(searchRequest);
    }
}
