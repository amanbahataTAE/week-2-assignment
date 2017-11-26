package com.example.aman1.songlistapplication.views.norealm;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.aman1.songlistapplication.R;
import com.example.aman1.songlistapplication.model.TrackModel;
import com.example.aman1.songlistapplication.model.TrackWrapper;
import com.example.aman1.songlistapplication.realm.RealmController;
import com.example.aman1.songlistapplication.services.RequestInterface;
import com.example.aman1.songlistapplication.utils.constants.Api_List;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.aman1.songlistapplication.services.ServerConnection.getServerConnection;

/**
 * A simple {@link Fragment} subclass.
 * It sends an api request to receive tracks of type Classic and Pop
 * from the server.
 * It implements a swipe to refresh functionality.
 */
public class TracksFragment extends Fragment {


    private static final String TAG = "TracksFragment";


    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mTrackRecyclerView;
    private RequestInterface requestInterface;

    private static final String ARG_GENRE = "genre";


    public static TracksFragment newInstance(String genre){
        Bundle args = new Bundle();
        args.putSerializable(ARG_GENRE, genre);
        TracksFragment fragment = new TracksFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String genre = (String) getArguments().getSerializable(ARG_GENRE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh_layout);


        requestInterface = getServerConnection();

        requestInterface.getSongsList(genre, Api_List.API_SEARCH_CRITERIA)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<TrackWrapper>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TrackWrapper value) {

                        Log.i("INCOMING_DATA", value.getResultCount().toString());


                        List<TrackModel> trackModel = value.getResults();



                        initiliseRecyclerView(view, trackModel);
                        Toast.makeText(getActivity(), "Found " + value.getResultCount() + " results", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "Refreshing", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * It initialises the recyclerview
     * @param view current view
     * @param trackList a collection of the available tracks
     */

    public void initiliseRecyclerView(View view, List<TrackModel> trackList){
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh_layout);
        mTrackRecyclerView = (RecyclerView) view.findViewById(R.id.track_recycler_view);
        mTrackRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTrackRecyclerView.setAdapter(new TrackFragmentAdaptor(trackList, R.layout.list_item_tracks, getActivity()));
    }
}
