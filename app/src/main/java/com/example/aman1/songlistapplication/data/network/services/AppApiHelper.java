package com.example.aman1.songlistapplication.data.network.services;

import com.example.aman1.songlistapplication.model.TrackModel;
import com.example.aman1.songlistapplication.model.TrackWrapper;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by aman1 on 26/11/2017.
 */

public class AppApiHelper implements ApiHelper {

    RequestInterface requestInterface;

    public AppApiHelper() {
        requestInterface = ServerConnection.getServerConnection();
    }

    @Override
    public Observable<TrackWrapper> getSongsList(String songGenre, String searchCriteria) {
        return requestInterface.getSongsList(songGenre, searchCriteria);
    }
}