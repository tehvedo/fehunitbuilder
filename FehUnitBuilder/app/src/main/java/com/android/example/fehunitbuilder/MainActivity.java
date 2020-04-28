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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab_new_build = findViewById(R.id.fab);

        fab_new_build.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent new_build_intent = new Intent(getApplicationContext(), NewBuildActivity.class);
                startActivity(new_build_intent);
            }
        });

    }

}