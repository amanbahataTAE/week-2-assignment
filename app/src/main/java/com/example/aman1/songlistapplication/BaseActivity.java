package com.example.aman1.songlistapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.aman1.songlistapplication.views.base.BaseFragment;
import com.example.aman1.songlistapplication.views.norealm.TracksFragment;

public class BaseActivity extends AppCompatActivity{

    private static final String TAG = "BaseActivity";

    private FragmentManager mFragmentManager;
    private String mPopGenre = "pop";
    private String mRockGenre = "rock";
    private String mClassicGenre = "classic";


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, createFragment(mRockGenre))
                            .commit();

                    return true;
                case R.id.navigation_dashboard:
                    mFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, createTracksFragment(mClassicGenre))
                            .commit();

                    return true;
                case R.id.navigation_notifications:
                    mFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, createTracksFragment(mPopGenre))
                            .commit();

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mFragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null){
            mFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, createFragment(mRockGenre))
                    .commit();
        }
    }

    /**
     * createFragment creates an new fragment by passing a genere as a parameter
     * @param genre genre of the track
     * @return new fragment that displays the api call return
     */

    protected Fragment createFragment(String genre){
       return BaseFragment.newInstance(genre);
    }

    protected Fragment createTracksFragment(String genre){
        return TracksFragment.newInstance(genre);
    }


}
