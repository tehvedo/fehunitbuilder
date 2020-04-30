/*
 * Kevin Kochanek & Evan Cruzen
 * CIS 433 Project - Feh Unit Builder
 * EditBuildActivity.java
 */

package com.android.example.fehunitbuilder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This class displays a screen where the user updates an existing build.
 * The EditBuildActivity returns the entered build to the calling activity
 * (ViewBuildActivity), which then stores the new build and updates the list of
 * displayed builds.
 */
public class EditBuildActivity extends AppCompatActivity {

    EditText[] et_build = new EditText[8];

    Button btn_save;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_build);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Fill fields with build info to be edited
        et_build[0] = findViewById(R.id.edit_build_name);
        et_build[1] = findViewById(R.id.edit_unit);
        et_build[2] = findViewById(R.id.edit_weapon);
        et_build[3] = findViewById(R.id.edit_assist);
        et_build[4] = findViewById(R.id.edit_special);
        et_build[5] = findViewById(R.id.edit_a_skill);
        et_build[6] = findViewById(R.id.edit_b_skill);
        et_build[7] = findViewById(R.id.edit_c_skill);
        btn_save = findViewById(R.id.button_save);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
        Intent intent = getIntent();
        final int id = intent.getIntExtra("build_id", -1);

        //Get build from database given an id
        Build rcvdBuild = dataBaseHelper.getBuild(id);

        //Save all data from build into string array
        String[] allData = rcvdBuild.getAll();

        //Set starting text to string array build text
        for(int i=0; i<8; i++)
            et_build[i].setText(allData[i]);

        //User makes changes...

        //When user is done, they can click the save button to update the build info
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save the build to the database
                Build build;
                try{
                    build = new Build(id, et_build[0].getText().toString(), et_build[1].getText().toString(),
                            et_build[2].getText().toString(), et_build[3].getText().toString(),
                            et_build[4].getText().toString(), et_build[5].getText().toString(),
                            et_build[6].getText().toString(), et_build[7].getText().toString());

                    Toast.makeText(getApplicationContext(), build.toString(), Toast.LENGTH_SHORT).show();
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Error creating build.", Toast.LENGTH_SHORT).show();
                    build = new Build(-1, "Error", "", "", "", "", "", "", "");
                }


                DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());

                //Update build
                boolean success = dataBaseHelper.updateBuild(build);

                Toast.makeText(getApplicationContext(), "Success: " + success, Toast.LENGTH_SHORT).show();
                finish();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.clear_data) {
            // Add a toast just for confirmation
            Toast.makeText(this, R.string.clear_data_toast_text, Toast.LENGTH_LONG).show();

            // Delete the existing data.
            DataBaseHelper dataBaseHelper = new DataBaseHelper(EditBuildActivity.this);
            dataBaseHelper.deleteAllBuilds();

            return true;
        }
        else if (id == R.id.pro_upgrade){
            if(MainActivity.isPro){
                MainActivity.isPro = false;
            }
            else{
                MainActivity.isPro = true;
            }
            Toast.makeText(this, "Pro: " + MainActivity.isPro, Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.dark_mode_toggle){
            if(MainActivity.isPro){
                //toggle dark mode
                if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    EditBuildActivity.this.recreate();
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    EditBuildActivity.this.recreate();
                }
            }
            else{
                Toast.makeText(this, "Pro version is required for dark mode", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
