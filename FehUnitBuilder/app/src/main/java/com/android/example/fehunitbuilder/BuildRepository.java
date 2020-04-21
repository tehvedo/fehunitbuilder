/*
 * Kevin Kochanek & Evan Cruzen
 * CIS 433 Project - Feh Unit Builder
 * BuildRepository.java
 */

package com.android.example.fehunitbuilder;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * This class holds the implementation code for the methods that interact with the database.
 */

public class BuildRepository {

    private BuildDao mBuildDao;
    private LiveData<List<Build>> mAllBuilds;

    BuildRepository(Application application) {
        BuildRoomDatabase db = BuildRoomDatabase.getDatabase(application);
        mBuildDao = db.buildDao();
        mAllBuilds = mBuildDao.getAllBuilds();
    }

    LiveData<List<Build>> getAllBuilds() {
        return mAllBuilds;
    }

    public void insert(Build build) {
        new insertAsyncTask(mBuildDao).execute(build);
    }

    public void update(Build build)  {
        new updateBuildAsyncTask(mBuildDao).execute(build);
    }

    public void deleteAll()  {
        new deleteAllBuildsAsyncTask(mBuildDao).execute();
    }

    // Must run off main thread
    public void deleteBuild(Build build) {
        new deleteBuildAsyncTask(mBuildDao).execute(build);
    }

    // Static inner classes below here to run database interactions in the background.

    /**
     * Inserts a build into the database.
     */
    private static class insertAsyncTask extends AsyncTask<Build, Void, Void> {

        private BuildDao mAsyncTaskDao;

        insertAsyncTask(BuildDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Build... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    /**
     * Deletes all builds from the database (does not delete the table).
     */
    private static class deleteAllBuildsAsyncTask extends AsyncTask<Void, Void, Void> {
        private BuildDao mAsyncTaskDao;

        deleteAllBuildsAsyncTask(BuildDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

    /**
     *  Deletes a single build from the database.
     */
    private static class deleteBuildAsyncTask extends AsyncTask<Build, Void, Void> {
        private BuildDao mAsyncTaskDao;

        deleteBuildAsyncTask(BuildDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Build... params) {
            mAsyncTaskDao.deleteBuild(params[0]);
            return null;
        }
    }

    /**
     *  Updates a build in the database.
     */
    private static class updateBuildAsyncTask extends AsyncTask<Build, Void, Void> {
        private BuildDao mAsyncTaskDao;

        updateBuildAsyncTask(BuildDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Build... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
