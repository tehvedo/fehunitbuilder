/*
 * Kevin Kochanek & Evan Cruzen
 * CIS 433 Project - Feh Unit Builder
 * BuildDao.java
 */

package com.android.example.fehunitbuilder;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Data Access Object (DAO) for a Feh unit build
 * Each method performs a database operation, such as inserting or deleting a build,
 * running a DB query, or deleting all builds.
 */

@Dao
public interface BuildDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Build build);

    @Query("DELETE FROM build_table")
    void deleteAll();

    @Delete
    void deleteBuild(Build build);

    @Query("SELECT * from build_table LIMIT 1")
    Build[] getAnyBuild();

    @Query("SELECT * from build_table ORDER BY build ASC")
    LiveData<List<Build>> getAllBuilds();

    @Update
    void update(Build... build);
}
