package com.example.aman1.songlistapplication.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aman1 on 25/11/2017.
 */

public class TrackWrapper {

    @SerializedName("resultCount")
    @Expose
    private Integer resultCount;
    @SerializedName("results")
    @Expose
    private List<TrackModel> results = null;

    public Integer getResultCount() {
        return resultCount;
    }

    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }

    public List<TrackModel> getResults() {
        return results;
    }

    public void setResults(List<TrackModel> results) {
        this.results = results;
    }
}
