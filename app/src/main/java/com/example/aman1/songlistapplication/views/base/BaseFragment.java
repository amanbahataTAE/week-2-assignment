package com.example.aman1.songlistapplication.views.base;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.aman1.songlistapplication.data.database.realm.RealmController;
import com.example.aman1.songlistapplication.data.database.realm.RealmTrack;
import com.example.aman1.songlistapplication.data.network.services.RequestInterface;
import com.example.aman1.songlistapplication.data.network.services.constants.Api_List;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;

import static com.example.aman1.songlistapplication.data.network.services.ServerConnection.getServerConnection;

/**
 * A simple {@link Fragment} subclass.
 * Provides a base fragment functionality where other fragments with similar functionalities
 * are built on.
 */
public class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";


    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mTrackRecyclerView;
    private RequestInterface requestInterface;
    private RealmController realmController;

    private static final String ARG_GENRE = "genre";


    public static BaseFragment newInstance(String genre){
        Bundle args = new Bundle();
        args.putSerializable(ARG_GENRE, genre);
        BaseFragment fragment = new BaseFragment();
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

        initializeRealm();

        String genre = (String) getArguments().getSerializable(ARG_GENRE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh_layout);


        ArrayList<RealmTrack> listOfTrack = realmController.getTracksList();
        List<RealmTrack> realmTrack = new ArrayList<>();

        for (RealmTrack track : listOfTrack ) {
            realmTrack.add(track);
        }

        initiliseRecyclerView(view, realmTrack);

        requestInterface = getServerConnection();

        requestInterface.getSongsList(genre, Api_List.API_SEARCH_CRITERIA)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<TrackWrapper>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        // clear realm

                    }

                    @Override
                    public void onNext(TrackWrapper value) {

                        Log.i("INCOMING_DATA", value.getResultCount().toString());


                        List<TrackModel> trackModel = value.getResults();

                        for (int i = 0; i < value.getResults().size(); i++){
                            TrackModel singleTrack = value.getResults().get(i);
                            storeToRealm(singleTrack);
                        }
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


    public void initiliseRecyclerView(View view, List<RealmTrack> trackList){
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh_layout);
        mTrackRecyclerView = (RecyclerView) view.findViewById(R.id.track_recycler_view);
        mTrackRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTrackRecyclerView.setAdapter(new TrackAdaptor(trackList, R.layout.list_item_tracks, getActivity()));
    }


    private void storeToRealm(TrackModel track) {
        RealmTrack realmTrack = new RealmTrack(track.getCollectionName(),
                track.getArtistName(), "" + track.getTrackPrice(),
                track.getArtworkUrl100(), track.getPreviewUrl());
        realmController.saveTrack(realmTrack);

    }


    public byte[] getBitmapFromURL(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            return stream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public void initializeRealm(){
        Realm.init(getContext());
        realmController = new RealmController(Realm.getDefaultInstance());
    }
}
