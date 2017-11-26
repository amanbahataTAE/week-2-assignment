package com.example.aman1.songlistapplication;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Base64;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.security.SecureRandom;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by aman1 on 26/11/2017.
 */

/**
 * MyTracksApplication initialises globally the libraries of
 * Fresco and Realm together with data encryption to realm
 */

public class MyTracksApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);


        /**
         * Encryption of data stored in the realm database
         */

        SharedPreferences preferences = getApplicationContext().getSharedPreferences("HEY_STORED", MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        byte[] key;
        if (preferences.getString("DB_KEY", null) == null){
            key = new byte[64];
            new SecureRandom().nextBytes(key);
            edit.putString("DB_KEY", Base64.encodeToString(key,Base64.DEFAULT));

            edit.commit();
        }else{
            key = Base64.decode( preferences.getString("DB_KEY",null),Base64.DEFAULT);
        }



        /**
         * Initializes Realm
         */

        Realm.init(getApplicationContext());


        // Builder pattern used
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(1)
                .encryptionKey(key)
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(configuration);


    }
}
