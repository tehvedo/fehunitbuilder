package com.android.example.fehunitbuilder;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

class JSONGetter
{
    //Get JSON object from file
    private static String loadSkills(Context context) {
        String json;
        try {
            InputStream is = context.getAssets().open("stats.json");
            int size = is.available();
            byte[] buffer = new byte[size];

            //Read from buffer
            final int read = is.read(buffer);
            Log.d("Details:", String.valueOf(read));

            is.close();

            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    static String[] getData(Context context, String table, String stat) throws JSONException {
        JSONObject obj = new JSONObject(loadSkills(context));
        //Get data from table: skills
        JSONArray objJSONArray = obj.getJSONArray(table);

        //Get names from skills table
        List<String> list = new ArrayList<>();
        for(int i = 0; i < objJSONArray.length(); i++){
            list.add(objJSONArray.getJSONObject(i).getString(stat));
        }

        //Make string array size equal to size of the list
        String[] stringArray = new String[list.size()];

        //ArrayList to array conversion
        for (int i =0; i < list.size(); i++)
            stringArray[i] = list.get(i);

        return stringArray;
    }
}