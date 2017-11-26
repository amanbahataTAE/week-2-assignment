package com.example.aman1.songlistapplication.data.database.realm;

/**
 * Created by aman1 on 26/11/2017.
 */


import io.realm.RealmObject;

/**
 * RealmTrack models the metadata of a single track
 */
public class RealmTrack extends RealmObject{

    private String mTrackCollectionName;
    private String mTrackArtistName;
    private String mTrackPrice;

    private String mTrackArtwork;
    private String mTrackPreviewUrl;


    public RealmTrack() {
    }


    public RealmTrack(String mTrackCollectionName, String mTrackArtistName, String mTrackPrice, String mTrackArtwork, String mTrackPreviewUrl) {
        this.mTrackCollectionName = mTrackCollectionName;
        this.mTrackArtistName = mTrackArtistName;
        this.mTrackPrice = mTrackPrice;
        this.mTrackArtwork = mTrackArtwork;
        this.mTrackPreviewUrl = mTrackPreviewUrl;
    }


    public String getmTrackCollectionName() {
        return mTrackCollectionName;
    }

    public void setmTrackCollectionName(String mTrackCollectionName) {
        this.mTrackCollectionName = mTrackCollectionName;
    }

    public String getmTrackArtistName() {
        return mTrackArtistName;
    }

    public void setmTrackArtistName(String mTrackArtistName) {
        this.mTrackArtistName = mTrackArtistName;
    }

    public String getmTrackPrice() {
        return mTrackPrice;
    }

    public void setmTrackPrice(String mTrackPrice) {
        this.mTrackPrice = mTrackPrice;
    }

    public String getmTrackArtwork() {
        return this.mTrackArtwork;
    }

    public void setmTrackArtwork(String mTrackArtwork) {
        this.mTrackArtwork = mTrackArtwork;
    }

    public String getmTrackPreviewUrl() {
        return mTrackPreviewUrl;
    }

    public void setmTrackPreviewUrl(String mTrackPreviewUrl) {
        this.mTrackPreviewUrl = mTrackPreviewUrl;
    }
//
//    /**
//     * Method convertArtworkImage(byte[]) converts a blob to a bitmap
//     * @param array
//     * @return an encoded array blob to a decoded bitmap
//     */
//
//    public Bitmap convertArtworkImage(byte[] array){
//        Bitmap bitmap = BitmapFactory.decodeByteArray(array, 0, array.length);
//        return bitmap;
//    }
}
