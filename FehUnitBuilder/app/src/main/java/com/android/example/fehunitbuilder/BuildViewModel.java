/*
 * Kevin Kochanek & Evan Cruzen
 * CIS 433 Project - Feh Unit Builder
 * BuildViewModel.java
 */

package com.android.example.fehunitbuilder;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

/**
 * The BuildViewModel provides the interface between the UI and the data layer of the app,
 * represented by the Repository
 */

public class BuildViewModel extends AndroidViewModel {

    private BuildRepository mRepository;

    private LiveData<List<Build>> mAllBuilds;

    public BuildViewModel(Application application) {
        super(application);
        mRepository = new BuildRepository(application);
        mAllBuilds = mRepository.getAllBuilds();
    }

    LiveData<List<Build>> getAllBuilds() {
        return mAllBuilds;
    }

    public void insert(Build build) {
        mRepository.insert(build);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void deleteBuild(Build build) {
        mRepository.deleteBuild(build);
    }

    public void update(Build build) {
        mRepository.update(build);
    }
}
