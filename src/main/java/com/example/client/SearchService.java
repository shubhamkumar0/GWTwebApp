package com.example.client;


import com.example.shared.search.SearchRequest;
import com.example.shared.search.SearchResponse;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("SearchService")
public interface SearchService extends RemoteService {

    SearchResponse search(SearchRequest searchRequest);

}

