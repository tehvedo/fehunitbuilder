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

public class Build {

    private int id;
    private String name;
    private String unit;
    private String weapon;
    private String assist;
    private String special;
    private String a_skill;
    private String b_skill;
    private String c_skill;

    public Build(int id, String name, String unit, String weapon, String assist, String special, String a_skill, String b_skill, String c_skill) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.weapon = weapon;
        this.assist = assist;
        this.special = special;
        this.a_skill = a_skill;
        this.b_skill = b_skill;
        this.c_skill = c_skill;
    }

    public Build(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Build() {
    }

    @Override
    public String toString() {
        return "Build{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", weapon='" + weapon + '\'' +
                ", assist='" + assist + '\'' +
                ", special='" + special + '\'' +
                ", a_skill='" + a_skill + '\'' +
                ", b_skill='" + b_skill + '\'' +
                ", c_skill='" + c_skill + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public String getAssist() {
        return assist;
    }

    public void setAssist(String assist) {
        this.assist = assist;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getA_skill() {
        return a_skill;
    }

    public void setA_skill(String a_skill) {
        this.a_skill = a_skill;
    }

    public String getB_skill() {
        return b_skill;
    }

    public void setB_skill(String b_skill) {
        this.b_skill = b_skill;
    }

    public String getC_skill() {
        return c_skill;
    }

    public void setC_skill(String c_skill) {
        this.c_skill = c_skill;
    }
}
