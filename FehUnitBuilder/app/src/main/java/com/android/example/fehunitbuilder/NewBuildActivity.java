/*
 * Kevin Kochanek & Evan Cruzen
 * CIS 433 Project - Feh Unit Builder
 * NewBuildActivity.java
 */

package com.android.example.fehunitbuilder;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

/**
 * This class displays a screen where the user enters a new build.
 * The NewBuildActivity returns the entered build to the calling activity
 * (MainActivity), which then stores the new build and updates the list of
 * displayed builds.
 */
public class NewBuildActivity extends AppCompatActivity {

    //Definitions
    EditText et_build_name, et_unit, et_weapon, et_assist,
    et_special, et_a_skill, et_b_skill, et_c_skill;
    Button btn_save;

    DataBaseHelper dataBaseHelper = new DataBaseHelper(NewBuildActivity.this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_build);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //GUI assignments
        et_build_name = findViewById(R.id.edit_build_name);
        et_unit = findViewById(R.id.edit_unit);
        et_weapon = findViewById(R.id.edit_weapon);
        et_assist = findViewById(R.id.edit_assist);
        et_special = findViewById(R.id.edit_special);
        et_a_skill = findViewById(R.id.edit_a_skill);
        et_b_skill = findViewById(R.id.edit_b_skill);
        et_c_skill = findViewById(R.id.edit_c_skill);
        btn_save = findViewById(R.id.button_save);

        //Create build object when save is clicked
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save the build to the database
                Build build;
                try{
                    build = new Build(-1, et_build_name.getText().toString(), et_unit.getText().toString(),
                            et_weapon.getText().toString(), et_assist.getText().toString(),
                            et_special.getText().toString(), et_a_skill.getText().toString(),
                            et_b_skill.getText().toString(), et_c_skill.getText().toString());
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Error creating build.", Toast.LENGTH_SHORT).show();
                    build = new Build(-1, "Error", "", "", "", "", "", "", "");
                }

                //Update old build with new one
                if(MainActivity.isPro) {
                    boolean success = dataBaseHelper.addOne(build);
                    Toast.makeText(getApplicationContext(), "Success: " + success, Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Pro version is required to save builds", Toast.LENGTH_SHORT).show();
                }
            }
        });


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
                    NewBuildActivity.this.recreate();
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("start_night_mode_key", true);
                    editor.commit();
                    NewBuildActivity.this.recreate();
                }
            }
            else{
                Toast.makeText(this, "Pro version is required for dark mode", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
