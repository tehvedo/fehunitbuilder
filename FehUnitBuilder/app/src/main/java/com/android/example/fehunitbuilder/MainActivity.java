/*
 * Kevin Kochanek & Evan Cruzen
 * CIS 433 Project - Feh Unit Builder
 * MainActivity.java (Saved builds screen)
 */

package com.android.example.fehunitbuilder;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.List;

/**
 * This class displays a list of builds in a RecyclerView.
 * The builds are saved in a Room database.
 * The layout for this activity also displays a FAB that
 * allows users to start the NewBuildActivity to add new builds.
 * Users can delete a build by swiping it away, or delete all builds
 * through the Options menu.
 * Whenever a new build is added, deleted, or updated, the RecyclerView
 * showing the list of builds automatically updates.
 */
public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab_new_build;
    Button btn_refresh;
    ListView lv_build_list;
    ArrayAdapter buildArrayAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab_new_build = findViewById(R.id.fab);
        btn_refresh = findViewById(R.id.button_refresh);
        lv_build_list = findViewById(R.id.lv_buildList);

        dataBaseHelper = new DataBaseHelper(MainActivity.this);

        buildArrayAdapter = new ArrayAdapter<Build>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getAllBuilds());
        lv_build_list.setAdapter(buildArrayAdapter);

        fab_new_build.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent new_build_intent = new Intent(getApplicationContext(), NewBuildActivity.class);
                startActivity(new_build_intent);
            }
        });

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);
                List<Build> build_list = dataBaseHelper.getAllBuilds();

                buildArrayAdapter = new ArrayAdapter<Build>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getAllBuilds());
                lv_build_list.setAdapter(buildArrayAdapter);

                //Toast.makeText(getApplicationContext(), build_list.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        lv_build_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                //Need to make view build activity, from there, update build option
//                Intent view_build_intent = new Intent(getApplicationContext(), ViewBuildActivity.class);
//                Build clickedBuild = (Build) parent.getItemAtPosition(pos);
//                view_build_intent.putExtra("build_id", clickedBuild.getId());
//                startActivity(view_build_intent);
            }
        });

        lv_build_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {
                Build clickedBuild = (Build) parent.getItemAtPosition(pos);
                dataBaseHelper.deleteOne(clickedBuild);
                buildArrayAdapter = new ArrayAdapter<Build>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getAllBuilds());
                lv_build_list.setAdapter(buildArrayAdapter);
                Toast.makeText(getApplicationContext(), "Deleting build", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        buildArrayAdapter = new ArrayAdapter<Build>(MainActivity.this, android.R.layout.simple_list_item_1, dataBaseHelper.getAllBuilds());
        lv_build_list.setAdapter(buildArrayAdapter);
    }


}