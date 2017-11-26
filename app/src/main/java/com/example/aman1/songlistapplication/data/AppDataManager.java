package com.example.aman1.songlistapplication.data;

import com.example.aman1.songlistapplication.data.network.services.ApiHelper;
import com.example.aman1.songlistapplication.data.network.services.AppApiHelper;
import com.example.aman1.songlistapplication.model.TrackWrapper;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by aman1 on 26/11/2017.
 */

public class AppDataManager implements IDataManager {

    private ApiHelper apiHelper;

    public AppDataManager() {
        apiHelper = new AppApiHelper();
    }

    @Override
    public Observable<TrackWrapper> getSongsList(String songGenre, String searchCriteria) {
        return apiHelper.getSongsList(songGenre, searchCriteria);
    }
}
