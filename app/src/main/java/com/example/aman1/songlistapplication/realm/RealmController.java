package com.example.aman1.songlistapplication.realm;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by aman1 on 26/11/2017.
 */

public class RealmController {

    Realm realm;


    public RealmController(Realm realm) {
        this.realm = realm;
    }

    /**
     * Stores the rack information to the database
     * @param realmTrack
     */
    public void saveTrack(final RealmTrack realmTrack){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(realmTrack);
            }
        });
    }


    /**
     * Returns the users list from the database
     */

    public ArrayList<RealmTrack> getTracksList(){
        ArrayList<RealmTrack> tracksList = new ArrayList<>();

        RealmResults<RealmTrack> realmTrackResults = realm.where(RealmTrack.class).findAll();

        for (RealmTrack track : realmTrackResults) {
            tracksList.add(track);
        }
        return tracksList;
    }
}

