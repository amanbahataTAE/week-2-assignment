package com.example.aman1.songlistapplication.views.norealm;

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
import com.example.aman1.songlistapplication.model.TrackModel;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by aman1 on 26/11/2017.
 */


/**
 * TrackFragmentAdaptor serves as an recyclerview adaptor for TracksFragment
 * - Realm database connection is not implemented in this class
 */

public class TrackFragmentAdaptor extends RecyclerView.Adapter<TrackFragmentAdaptor.ViewHolder>  {

        private List<TrackModel> mTracksList;
        private int mRowTrak;
        private Context mApplicationContext;
        private MediaPlayer mediaplayer;
        private String mTrackUrl;
        private boolean mIsPlaying;


        public TrackFragmentAdaptor(List<TrackModel> mTracksList, int mRowTrak, Context mApplicationContext) {
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
        public TrackFragmentAdaptor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(this.mApplicationContext).inflate(mRowTrak, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.mTrackCollectionName.setText(mTracksList.get(position).getCollectionName());
            holder.mTrackArtistName.setText(mTracksList.get(position).getArtistName());
            holder.mTrackPrice.setText(String.format("%s", mTracksList.get(position).getTrackPrice()));

            Uri uri = Uri.parse(mTracksList.get(position).getArtworkUrl100());
            holder.draweeView.setImageURI(uri);


            mediaplayer = new MediaPlayer();
            mTrackUrl = mTracksList.get(position).getPreviewUrl();
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
