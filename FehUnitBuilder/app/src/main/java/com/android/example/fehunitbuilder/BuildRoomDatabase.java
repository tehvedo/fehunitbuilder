/*
 * Kevin Kochanek & Evan Cruzen
 * CIS 433 Project - Feh Unit Builder
 * BuildRoomDatabase.java
 */

package com.android.example.fehunitbuilder;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

/**
 * BuildRoomDatabase. Includes code to create the database.
 * After the app creates the database, all further interactions
 * with it happen through the BuildViewModel.
 */

@Database(entities = {Build.class}, version = 2, exportSchema = false)
public abstract class BuildRoomDatabase extends RoomDatabase {

    public abstract BuildDao buildDao();

    private static BuildRoomDatabase INSTANCE;

    public static BuildRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BuildRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here.
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BuildRoomDatabase.class, "build_database")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // This callback is called when the database has opened.
    // In this case, use PopulateDbAsync to populate the database
    // with the initial data set if the database has no entries.
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    // Populate the database with the initial data set
    // only if the database has no entries.
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final BuildDao mDao;

        // Initial data set
        private static String [] builds = {"Sample Build #1", "Sample Build #2", "Sample Build #3", "Sample Build #4", "Sample Build #5",
                "Sample Build #6", "Sample Build #7"};

        PopulateDbAsync(BuildRoomDatabase db) {
            mDao = db.buildDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // If we have no builds, then create the initial list of builds.
            Build test = new Build(13, "Test");
            test.setUnit("Dimwit");
            mDao.insert(test);
            if (mDao.getAnyBuild().length < 1) {
                for (int i = 0; i <= builds.length - 1; i++) {
                    Build build = new Build(i, builds[i]);
                    mDao.insert(build);
                }
            }
            return null;
        }
    }
}

