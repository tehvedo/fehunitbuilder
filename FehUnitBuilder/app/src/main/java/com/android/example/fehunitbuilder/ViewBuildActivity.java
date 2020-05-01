/*
 * Kevin Kochanek & Evan Cruzen
 * CIS 433 Project - Feh Unit Builder
 * ViewBuildActivity.java
 */

package com.android.example.fehunitbuilder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This class displays a screen where the user can view a build.
 */
public class ViewBuildActivity extends AppCompatActivity {

    //Definitions
    TextView[] et_build = new TextView[8];
    Button btn_edit;

    public static int last_viewed_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_build);
        Intent intent = getIntent();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Grab build to view
        final int id = intent.getIntExtra("build_id", -1);
        if(id != -1){
            last_viewed_id = id;
        }

        Toast.makeText(this, "id: " + last_viewed_id, Toast.LENGTH_LONG).show();

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
        Build build = dataBaseHelper.getBuild(last_viewed_id);

        //GUI assignments
        et_build[0] = findViewById(R.id.edit_build_name);
        et_build[1] = findViewById(R.id.edit_unit);
        et_build[2] = findViewById(R.id.edit_weapon);
        et_build[3] = findViewById(R.id.edit_assist);
        et_build[4] = findViewById(R.id.edit_special);
        et_build[5] = findViewById(R.id.edit_a_skill);
        et_build[6] = findViewById(R.id.edit_b_skill);
        et_build[7] = findViewById(R.id.edit_c_skill);
        btn_edit = findViewById(R.id.button_edit);

        //Save all data from build into string array
        String[] allData = build.getAll();

        //Display data
//        for(int i=0; i<8; i++)
//            et_build[i].setText(allData[i]);

        et_build[0].setText("Build Name: " + allData[0]);
        et_build[1].setText("Unit: " + allData[1]);
        et_build[2].setText("Weapon: " + allData[2]);
        et_build[3].setText("Assist: " + allData[3]);
        et_build[4].setText("Special: " + allData[4]);
        et_build[5].setText("A Skill: " + allData[5]);
        et_build[6].setText("B Skill: " + allData[6]);
        et_build[7].setText("C Skill: " + allData[7]);

        //When edit button is clicked, pass build id to EditBuildActivity
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit_build_intent = new Intent(getApplicationContext(), EditBuildActivity.class);
                edit_build_intent.putExtra("build_id", last_viewed_id);
                startActivity(edit_build_intent);

            }
        });
    }

    //On resume, update the build info
    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();

        final int id = intent.getIntExtra("build_id", -1);
        if(id != -1){
            last_viewed_id = id;
        }

        Toast.makeText(this, "id: " + last_viewed_id, Toast.LENGTH_LONG).show();

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
        Build build = dataBaseHelper.getBuild(last_viewed_id);

        et_build[0] = findViewById(R.id.edit_build_name);
        et_build[1] = findViewById(R.id.edit_unit);
        et_build[2] = findViewById(R.id.edit_weapon);
        et_build[3] = findViewById(R.id.edit_assist);
        et_build[4] = findViewById(R.id.edit_special);
        et_build[5] = findViewById(R.id.edit_a_skill);
        et_build[6] = findViewById(R.id.edit_b_skill);
        et_build[7] = findViewById(R.id.edit_c_skill);

        btn_edit = findViewById(R.id.button_edit);

        String[] allData = build.getAll();

//        for(int i=0; i<8; i++)
//            et_build[i].setText("yeeter " + allData[i]);

        et_build[0].setText("Build Name: " + allData[0]);
        et_build[1].setText("Unit: " + allData[1]);
        et_build[2].setText("Weapon: " + allData[2]);
        et_build[3].setText("Assist: " + allData[3]);
        et_build[4].setText("Special: " + allData[4]);
        et_build[5].setText("A Skill: " + allData[5]);
        et_build[6].setText("B Skill: " + allData[6]);
        et_build[7].setText("C Skill: " + allData[7]);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // The options menu has a single item "Clear all data now"
    // that deletes all the entries in the database.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here
        int id = item.getItemId();

        SharedPreferences sharedPref = getSharedPreferences("my_shared_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        //noinspection SimplifiableIfStatement
        if (id == R.id.clear_data) {
            // Add a toast just for confirmation
            Toast.makeText(this, "Deleting all builds", Toast.LENGTH_LONG).show();

            // Delete the existing data.
            DataBaseHelper dataBaseHelper = new DataBaseHelper(ViewBuildActivity.this);
            dataBaseHelper.deleteAllBuilds();

            return true;
        }
        else if (id == R.id.pro_upgrade){
            if(MainActivity.isPro){
                MainActivity.isPro = false;
                editor.putBoolean("isPro_key", false);
                editor.commit();
            }
            else{
                MainActivity.isPro = true;
                editor.putBoolean("isPro_key", true);
                editor.commit();
            }
            Toast.makeText(this, "Pro: " + MainActivity.isPro, Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.dark_mode_toggle){
            if(MainActivity.isPro){
                //toggle dark mode
                if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("start_night_mode_key", false);
                    editor.commit();
                    ViewBuildActivity.this.recreate();
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("start_night_mode_key", true);
                    editor.commit();
                    ViewBuildActivity.this.recreate();
                }
            }
            else{
                Toast.makeText(this, "Pro version is required for dark mode", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}