package com.example.aman1.songlistapplication.views.norealm;


import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.example.aman1.songlistapplication.data.AppDataManager;
import com.example.aman1.songlistapplication.model.TrackModel;
import com.example.aman1.songlistapplication.model.TrackWrapper;
import com.example.aman1.songlistapplication.data.network.services.RequestInterface;
import com.example.aman1.songlistapplication.data.network.services.constants.Api_List;
import com.example.aman1.songlistapplication.views.ITrackListMvpView;
import com.example.aman1.songlistapplication.views.TrackListPresenter;
import com.example.aman1.songlistapplication.views.ui.base.utils.rx.AppSchedulerProvider;

import java.net.URLConnection;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.aman1.songlistapplication.data.network.services.ServerConnection.getServerConnection;

/**
 * A simple {@link Fragment} subclass.
 * It sends an api request to receive tracks of type Classic and Pop
 * from the server.
 * It implements a swipe to refresh functionality.
 */
public class TracksFragment extends Fragment implements ITrackListMvpView{


    private static final String TAG = "TracksFragment";


    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mTrackRecyclerView;
    private TrackListPresenter<TracksFragment> trackListPresenter;

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

        initiliseRecyclerView(view);
        initialisePresenter(genre);

        if (isDataConnectionAvailable(getContext())) {
            getData(genre);
        }else {
            Toast.makeText(getContext(), "Network connection not found", Toast.LENGTH_LONG).show();
        }


        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "Refreshing", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initialisePresenter(String genre){
        trackListPresenter = new TrackListPresenter<>(new AppDataManager(), new AppSchedulerProvider(),
                new CompositeDisposable(), genre);

        trackListPresenter.onAttach(this);

    }

    /**
     * Method checks for the internet connection when fetching the data
     * @param context activity context
     * @return true if there is internet connection available, false otherwise
     */

    public static boolean isDataConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    /**
     * It initialises the recyclerview
     * @param view current view
     */

    public void initiliseRecyclerView(View view){
  //      mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh_layout);
        mTrackRecyclerView = (RecyclerView) view.findViewById(R.id.track_recycler_view);
        mTrackRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void getData(String genre){
        trackListPresenter.onCallTrackList(genre);
    }

    @Override
    public void onFetchDataSuccess(List<TrackModel> trackModels) {
        mTrackRecyclerView.setAdapter(new TrackFragmentAdaptor(trackModels, R.layout.list_item_tracks, getActivity()));
    }

    @Override
    public void onFetchDataError(String message) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void openActivityOnTokenExpire() {

    }

    @Override
    public void onError(int resId) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showMessage(int resId) {

    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void hideKeyboard() {

    }
}
