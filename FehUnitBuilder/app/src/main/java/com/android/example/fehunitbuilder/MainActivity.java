/*
 * Kevin Kochanek & Evan Cruzen
 * CIS 433 Project - Feh Unit Builder
 * MainActivity.java (Saved builds screen)
 */

package com.android.example.fehunitbuilder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * This class displays a list of builds in a ListView.
 * The builds are saved in a SQLite database.
 * The layout for this activity also displays a FAB that
 * allows users to start the NewBuildActivity to add new builds.
 * Users can delete a build by long pressing, or delete all builds
 * through the Options menu.
 * Users can view a build by clicking on one, from there they have
 * the option to update it.
 * Whenever a new build is added, deleted, or updated, the ListView
 * showing the list of builds automatically updates.
 */
public class MainActivity extends AppCompatActivity {

    //Definitions
    FloatingActionButton fab_new_build;
    ListView lv_build_list;
    ArrayAdapter buildArrayAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //GUI assignments
        fab_new_build = findViewById(R.id.fab);
        lv_build_list = findViewById(R.id.lv_buildList);

        //DataBaseHelper to interact with the DB
        dataBaseHelper = new DataBaseHelper(MainActivity.this);

        //Update the list of builds to reflect current DB
        buildArrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getAllBuilds());
        lv_build_list.setAdapter(buildArrayAdapter);

        //Create new build activity when + button is clicked
        fab_new_build.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent new_build_intent = new Intent(getApplicationContext(), NewBuildActivity.class);
                startActivity(new_build_intent);
            }
        });

        //View build when a build is clicked
        lv_build_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                //Find which build was clicked
                Build clickedBuild = (Build) parent.getItemAtPosition(pos);
                Intent view_build_intent = new Intent(getApplicationContext(), ViewBuildActivity.class);

                //Pass build id to build viewer
                view_build_intent.putExtra("build_id", clickedBuild.getId());
                startActivity(view_build_intent);
            }
        });

        //Delete build when a build is long clicked
        lv_build_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {
                //Find which build was long clicked
                Build clickedBuild = (Build) parent.getItemAtPosition(pos);
                //Delete it
                dataBaseHelper.deleteOne(clickedBuild);
                //Update displayed list
                buildArrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getAllBuilds());
                lv_build_list.setAdapter(buildArrayAdapter);
                Toast.makeText(getApplicationContext(), "Deleting build", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Every time the user returns to the MainActivity, update the build list
        buildArrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getAllBuilds());
        lv_build_list.setAdapter(buildArrayAdapter);
    }

}