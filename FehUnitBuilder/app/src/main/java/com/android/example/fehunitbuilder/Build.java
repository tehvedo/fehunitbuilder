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
    @ColumnInfo(name = "build")
    private String build = "build";

    @NonNull
    @ColumnInfo(name = "unit")
    private String unit = "unit";

    @NonNull
    @ColumnInfo(name = "weapon")
    private String weapon = "weapon";

    @NonNull
    @ColumnInfo(name = "assist")
    private String assist = "assist";

    @NonNull
    @ColumnInfo(name = "special")
    private String special = "special";

    @NonNull
    @ColumnInfo(name = "ASkill")
    private String ASkill = "aSkill";

    @NonNull
    @ColumnInfo(name = "BSkill")
    private String BSkill = "bSkill";

    @NonNull
    @ColumnInfo(name = "CSkill")
    private String CSkill = "cSkill";

    public Build(int id, @NonNull String build) {
        setId(id);
        setBuild(build);
    }


    public void setBuild(@NonNull String build) {
        this.build = build;
    }

    public void setUnit(@NonNull String unit) {
        this.unit = unit;
    }

    public void setWeapon(@NonNull String weapon) {
        this.weapon = weapon;
    }

    public void setAssist(@NonNull String assist) {
        this.assist = assist;
    }

    public void setSpecial(@NonNull String special) {
        this.special = special;
    }

    public void setASkill(@NonNull String ASkill) {
        this.ASkill = ASkill;
    }

    public void setBSkill(@NonNull String BSkill) {
        this.BSkill = BSkill;
    }

    public void setCSkill(@NonNull String CSkill) {
        this.CSkill = CSkill;
    }

    @NonNull
    public String getBuild() {
        return build;
    }

    @NonNull
    public String getUnit() {
        return unit;
    }

    @NonNull
    public String getWeapon() {
        return weapon;
    }

    @NonNull
    public String getAssist() {
        return assist;
    }

    @NonNull
    public String getSpecial() {
        return special;
    }

    @NonNull
    public String getASkill() {
        return ASkill;
    }

    @NonNull
    public String getBSkill() {
        return BSkill;
    }

    @NonNull
    public String getCSkill() {
        return CSkill;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;
    }
}
