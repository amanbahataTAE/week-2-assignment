package com.example.aman1.songlistapplication.views;

import com.example.aman1.songlistapplication.model.TrackModel;
import com.example.aman1.songlistapplication.views.ui.base.base.MvpView;

import java.util.List;

/**
 * Created by aman1 on 26/11/2017.
 */

public interface ITrackListMvpView extends MvpView{

    void onFetchDataSuccess(List<TrackModel> trackModels);
    void onFetchDataError(String message);
}
