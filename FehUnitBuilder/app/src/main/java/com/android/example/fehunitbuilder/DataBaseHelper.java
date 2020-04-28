package com.android.example.fehunitbuilder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String BUILD_TABLE = "BUILD_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_BUILD_NAME = "BUILD_NAME";
    public static final String COLUMN_BUILD_UNIT = "BUILD_UNIT";
    public static final String COLUMN_BUILD_WEAPON = "BUILD_WEAPON";
    public static final String COLUMN_BUILD_ASSIST = "BUILD_ASSIST";
    public static final String COLUMN_BUILD_SPECIAL = "BUILD_SPECIAL";
    public static final String COLUMN_BUILD_A_SKILL = "BUILD_A_SKILL";
    public static final String COLUMN_BUILD_B_SKILL = "BUILD_B_SKILL";
    public static final String COLUMN_BUILD_C_SKILL = "BUILD_C_SKILL";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "build.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + BUILD_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BUILD_NAME + " TEXT, " + COLUMN_BUILD_UNIT + " TEXT, " + COLUMN_BUILD_WEAPON + " TEXT, " + COLUMN_BUILD_ASSIST + " TEXT, " +
                COLUMN_BUILD_SPECIAL + " TEXT, " + COLUMN_BUILD_A_SKILL + " TEXT, " + COLUMN_BUILD_B_SKILL + " TEXT, " + COLUMN_BUILD_C_SKILL + " TEXT)";

        db.execSQL(createTableStatement);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {

    }

    public boolean addOne(Build build){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BUILD_NAME, build.getName());
        cv.put(COLUMN_BUILD_UNIT, build.getUnit());
        cv.put(COLUMN_BUILD_WEAPON, build.getWeapon());
        cv.put(COLUMN_BUILD_ASSIST, build.getAssist());
        cv.put(COLUMN_BUILD_SPECIAL, build.getSpecial());
        cv.put(COLUMN_BUILD_A_SKILL, build.getA_skill());
        cv.put(COLUMN_BUILD_B_SKILL, build.getB_skill());
        cv.put(COLUMN_BUILD_C_SKILL, build.getC_skill());

        long insert = db.insert(BUILD_TABLE, null, cv);
        if(insert == -1){
            return false;
        }
        else{
            return true;
        }

    }

    public boolean deleteOne(Build build){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + BUILD_TABLE + " WHERE " + COLUMN_ID + " = " + build.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }
    }

    public List<Build> getAllBuilds(){
        List<Build> returnList = new ArrayList<>();
        String queryString = "SELECT * FROM " + BUILD_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                int buildID = cursor.getInt(0);
                String buildName = cursor.getString(1);
                String buildUnit = cursor.getString(2);
                String buildWeapon = cursor.getString(3);
                String buildAssist = cursor.getString(4);
                String buildSpecial = cursor.getString(5);
                String buildASkill = cursor.getString(6);
                String buildBSkill = cursor.getString(7);
                String buildCSkill = cursor.getString(8);

                Build newBuild = new Build(buildID, buildName, buildUnit, buildWeapon
                , buildAssist, buildSpecial, buildASkill, buildBSkill, buildCSkill);

                returnList.add(newBuild);

            } while(cursor.moveToNext());

        }
        else{
            //Don't add anything
        }

        cursor.close();
        db.close();

        return returnList;
    }


}
