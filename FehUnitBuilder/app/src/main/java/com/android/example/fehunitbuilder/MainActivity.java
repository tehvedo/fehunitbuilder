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

    public static final int NEW_BUILD_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_BUILD_ACTIVITY_REQUEST_CODE = 2;

    public static final String EXTRA_DATA_UPDATE_BUILD = "extra_build_to_be_updated";
    public static final String EXTRA_DATA_ID = "extra_data_id";

    private BuildViewModel mBuildViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set up the RecyclerView.
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final BuildListAdapter adapter = new BuildListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up the BuildViewModel.
        mBuildViewModel = ViewModelProviders.of(this).get(BuildViewModel.class);
        // Get all the builds from the database
        // and associate them to the adapter.
        mBuildViewModel.getAllBuilds().observe(this, new Observer<List<Build>>() {
            @Override
            public void onChanged(@Nullable final List<Build> builds) {
                // Update the cached copy of the builds in the adapter.
                adapter.setBuilds(builds);
            }
        });

        // Floating action button setup
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewBuildActivity.class);
                startActivityForResult(intent, NEW_BUILD_ACTIVITY_REQUEST_CODE);
            }
        });

        // Add the functionality to swipe items in the
        // RecyclerView to delete the swiped item.
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    // When the user swipes a build,
                    // delete that build from the database.
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Build myBuild = adapter.getBuildAtPosition(position);
                        Toast.makeText(MainActivity.this,
                                getString(R.string.delete_word_preamble) + " " +
                                myBuild.getName(), Toast.LENGTH_LONG).show();

                        // Delete the build.
                        mBuildViewModel.deleteBuild(myBuild);
                    }
                });
        // Attach the item touch helper to the recycler view.
        helper.attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new BuildListAdapter.ClickListener()  {

            @Override
            public void onItemClick(View v, int position) {
                Build build = adapter.getBuildAtPosition(position);
                launchUpdateBuildActivity(build);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
            mBuildViewModel.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * When the user enters a new build in the NewBuildActivity,
     * that activity returns the result to this activity.
     * If the user entered a new build, save it in the database.

     * @param requestCode ID for the request
     * @param resultCode indicates success or failure
     * @param data The Intent sent back from the NewBuildActivity,
     *             which includes the build that the user entered
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_BUILD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Build build = new Build(data.getStringExtra(NewBuildActivity.EXTRA_REPLY));
            // Save the data.
            mBuildViewModel.insert(build);
        } else if (requestCode == UPDATE_BUILD_ACTIVITY_REQUEST_CODE
                && resultCode == RESULT_OK) {
            String build_data = data.getStringExtra(NewBuildActivity.EXTRA_REPLY);
            int id = data.getIntExtra(NewBuildActivity.EXTRA_REPLY_ID, -1);

            if (id != -1) {
                mBuildViewModel.update(new Build(id, build_data));
            } else {
                Toast.makeText(this, R.string.unable_to_update,
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(
                    this, R.string.empty_not_saved, Toast.LENGTH_LONG).show();
        }
    }

    public void launchUpdateBuildActivity(Build build) {
        Intent intent = new Intent(this, NewBuildActivity.class);
        intent.putExtra(EXTRA_DATA_UPDATE_BUILD, build.getName());
        intent.putExtra(EXTRA_DATA_ID, build.getId());
        startActivityForResult(intent, UPDATE_BUILD_ACTIVITY_REQUEST_CODE);
    }
}