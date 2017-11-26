package com.example.aman1.songlistapplication.views.base;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aman1.songlistapplication.R;
import com.example.aman1.songlistapplication.data.database.realm.RealmController;
import com.example.aman1.songlistapplication.data.database.realm.RealmTrack;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.List;

/**
 * Created by aman1 on 26/11/2017.
 */


/**
 * TrackAdaptor helps by offering it's methods when RecyclerView needs to display
 * a new ViewHolder or connect a TrackModel
 */

public class TrackAdaptor extends RecyclerView.Adapter<TrackAdaptor.ViewHolder> {

    private List<RealmTrack> mTracksList;
    private int mRowTrak;
    private Context mApplicationContext;
    private MediaPlayer mediaplayer;
    private String mTrackUrl;
    private boolean mIsPlaying;

    RealmController realmController;

    public TrackAdaptor(List<RealmTrack> mTracksList, int mRowTrak, Context mApplicationContext) {
        this.mTracksList = mTracksList;
        this.mRowTrak = mRowTrak;
        this.mApplicationContext = mApplicationContext;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTrackCollectionName, mTrackArtistName, mTrackPrice;
        private SimpleDraweeView draweeView;

        public ViewHolder(View itemView) {
            super(itemView);

            mTrackCollectionName = (TextView) itemView.findViewById(R.id.track_collection_name);
            mTrackArtistName = (TextView) itemView.findViewById(R.id.track_artist_name);
            mTrackPrice = (TextView) itemView.findViewById(R.id.track_price);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.track_artwork);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.mApplicationContext).inflate(mRowTrak, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position) {
        holder.mTrackCollectionName.setText(mTracksList.get(position).getmTrackCollectionName());
        holder.mTrackArtistName.setText(mTracksList.get(position).getmTrackArtistName());
        holder.mTrackPrice.setText(String.format("%s", mTracksList.get(position).getmTrackPrice()));

        Uri uri = Uri.parse(mTracksList.get(position).getmTrackArtwork());
        holder.draweeView.setImageURI(uri);


        mediaplayer = new MediaPlayer();
        mTrackUrl = mTracksList.get(position).getmTrackPreviewUrl();
        mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mIsPlaying = false;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mIsPlaying) {
                    try {
                        mIsPlaying = true;
                        mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaplayer.setDataSource(mTrackUrl);
                        mediaplayer.prepare();
                        mediaplayer.start();

                    } catch (Exception e) {
                        // handle exception
                    }
                }else{
                    mediaplayer.reset();
                    mediaplayer.release();
                    mIsPlaying = false;
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return mTracksList.size();
    }

}
