package com.example.aman1.songlistapplication.views;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.example.aman1.songlistapplication.data.IDataManager;
import com.example.aman1.songlistapplication.data.network.services.constants.Api_List;
import com.example.aman1.songlistapplication.model.TrackModel;
import com.example.aman1.songlistapplication.model.TrackWrapper;
import com.example.aman1.songlistapplication.views.ui.base.base.BasePresenter;
import com.example.aman1.songlistapplication.views.ui.base.utils.rx.SchedulerProvider;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by aman1 on 26/11/2017.
 */

public class TrackListPresenter<V extends ITrackListMvpView> extends
        BasePresenter<V> implements ItrackListMvpPresenter<V>  {

    private String genre;
    private SwipeRefreshLayout mSwipeRefreshLayout;

        public TrackListPresenter(IDataManager dataManager, SchedulerProvider schedulerProvider,
                                  CompositeDisposable compositeDisposable, String genre) {
        super(dataManager, schedulerProvider, compositeDisposable);

        this.genre = genre;
    }


        @Override
        public void onCallTrackList(String genre) {
            getCompositeDisposable()
                    .add(getDataManager().getSongsList(genre, Api_List.API_SEARCH_CRITERIA)
                            .observeOn(getSchedulerProvider().ui())
                            .subscribeOn(getSchedulerProvider().io())
                            .subscribe(new Consumer<TrackWrapper>() {
                                           @Override
                                           public void accept(TrackWrapper trackWrapper) throws Exception {
                                               getMvpView().onFetchDataSuccess(trackWrapper.getResults());
                                           }
                                       },
                                    new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) throws Exception {
                                            getMvpView().onError(throwable.getMessage());
                                        }
                                    })
                    );

//            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                @Override
//                public void onRefresh() {
//                    mSwipeRefreshLayout.setRefreshing(false);
//                    Toast.makeText(getActivity(), "Refreshing", Toast.LENGTH_SHORT).show();
//                }
//            });

        }
}

