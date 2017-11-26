package com.example.aman1.songlistapplication.data.network.services;

import com.example.aman1.songlistapplication.model.TrackWrapper;
import io.reactivex.Observable;


/**
 * Created by aman1 on 26/11/2017.
 */

public interface ApiHelper {

    Observable<TrackWrapper> getSongsList(String songGenre,String searchCriteria);

}
