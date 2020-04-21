/*
 * Kevin Kochanek & Evan Cruzen
 * CIS 433 Project - Feh Unit Builder
 * Build.java
 */

package com.android.example.fehunitbuilder;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Entity class that represents a unit build in the database
 */

@Entity(tableName = "build_table")
public class Build {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String mName;

    public Build(@NonNull String name) {
        this.mName = name;
    }

    /*
    * This constructor is annotated using @Ignore, because Room expects only
    * one constructor by default in an entity class.
    */

    @Ignore
    public Build(int id, @NonNull String name) {
        this.id = id;
        this.mName = name;
    }

    public String getName() {
        return this.mName;
    }

    public int getId() {return id;}

    public void setId(int id) {
        this.id = id;
    }
}
