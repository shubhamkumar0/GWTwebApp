package com.example.client;


import com.example.server.search.SearchRequest;
import com.example.server.search.SearchResponse;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("SearchService")
public interface SearchService extends RemoteService {

    SearchResponse search(SearchRequest searchRequest);

}

