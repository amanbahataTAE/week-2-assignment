package com.example.aman1.songlistapplication.data.network.services.constants;

/**
 * Created by aman1 on 25/11/2017.
 */

/**
 * Class Api_List holds the base url for all the requests,
 * together with the paths for retrieving songs of type rock, pop and classic.
 */

public class Api_List {

    public static final String BASE_URL = "https://itunes.apple.com/";
    public static final String API_SEARCH_SONG = "search";
    public static final String API_SONG_TYPE = "{genre}";
    public static final String API_SEARCH_CRITERIA = "amp;media=music&amp;entity=song&amp;limit=50";
}
