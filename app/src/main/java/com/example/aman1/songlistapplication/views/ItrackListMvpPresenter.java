package com.example.aman1.songlistapplication.views;

import com.example.aman1.songlistapplication.views.ui.base.base.MvpPresenter;

/**
 * Created by aman1 on 26/11/2017.
 */

public interface ItrackListMvpPresenter<V extends ITrackListMvpView> extends MvpPresenter<V> {

    void onCallTrackList(String genre);
}