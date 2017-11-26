package com.example.aman1.songlistapplication.data.network.services;

import com.example.aman1.songlistapplication.model.TrackWrapper;
import com.example.aman1.songlistapplication.data.network.services.constants.Api_List;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by aman1 on 25/11/2017.
 */

public interface RequestInterface {

    @GET(Api_List.API_SEARCH_SONG)
    Observable<TrackWrapper> getSongsList(@Query("term") String songGenre, @Query("amp") String searchCriteria);

}
