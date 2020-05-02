/*
 * Kevin Kochanek & Evan Cruzen
 * CIS 433 Project - Feh Unit Builder
 * EditBuildActivity.java
 */

package com.android.example.fehunitbuilder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;

import java.util.Objects;

/**
 * This class displays a screen where the user updates an existing build.
 * The EditBuildActivity returns the entered build to the calling activity
 * (ViewBuildActivity), which then stores the new build and updates the list of
 * displayed builds.
 */
public class EditBuildActivity extends AppCompatActivity {

    AutoCompleteTextView[] editText = new AutoCompleteTextView[7];
    EditText buildText;

    Button btn_save;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_build);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        //Fill fields with build info to be edited
        buildText = findViewById(R.id.edit_build_name);

        editText[0] = findViewById(R.id.edit_unit);
        editText[1] = findViewById(R.id.edit_weapon);
        editText[2] = findViewById(R.id.edit_assist);
        editText[3] = findViewById(R.id.edit_special);
        editText[4] = findViewById(R.id.edit_a_skill);
        editText[5] = findViewById(R.id.edit_b_skill);
        editText[6] = findViewById(R.id.edit_c_skill);

        btn_save = findViewById(R.id.button_save);

        DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());
        Intent intent = getIntent();
        final int id = intent.getIntExtra("build_id", -1);

        //Get build from database given an id
        Build rcvdBuild = dataBaseHelper.getBuild(id);

        //Save all data from build into string array
        String[] allData = rcvdBuild.getAll();

        //Variable used to track whether an AutoCompleteTextView has given valid data
        final String[] var = new String[7];

        //Set starting text to string array build text
        buildText.setText(allData[0]);

        for(int i=0; i<editText.length; i++) {
            editText[i].setText(allData[i+1]);
            var[i] = allData[i+1];
        }

        buildText = findViewById(R.id.edit_build_name);
        editText[0] = findViewById(R.id.edit_unit);
        editText[1] = findViewById(R.id.edit_weapon);
        editText[2] = findViewById(R.id.edit_assist);
        editText[3] = findViewById(R.id.edit_special);
        editText[4] = findViewById(R.id.edit_a_skill);
        editText[5] = findViewById(R.id.edit_b_skill);
        editText[6] = findViewById(R.id.edit_c_skill);

        //Get array of skills for user to pick from
        String[] units = new String[0];
        String[] weapons = new String[0];
        String[] assists = new String[0];
        String[] specials = new String[0];
        String[] skillsA = new String[0];
        String[] skillsB = new String[0];
        String[] skillsC = new String[0];

        try {
            units = JSONGetter.getData(this, "heroes", "name");
            weapons = JSONGetter.getData(this, "weapons", "name");
            assists = JSONGetter.getData(this, "assists", "name");
            specials = JSONGetter.getData(this, "specials", "name");
            skillsA = JSONGetter.getData(this, "passivea", "name");
            skillsB = JSONGetter.getData(this, "passiveb", "name");
            skillsC = JSONGetter.getData(this, "passivec", "name");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Set autocomplete suggestions to stuff from array
        final ArrayAdapter<String> adapterArray0 = new ArrayAdapter<>(this,
                R.layout.custom_list_item, R.id.text_view_list_item, units);
        editText[0].setAdapter(adapterArray0);
        final ArrayAdapter<String> adapterArray1  = new ArrayAdapter<>(this,
                R.layout.custom_list_item, R.id.text_view_list_item, weapons);
        editText[1].setAdapter(adapterArray1);
        final ArrayAdapter<String> adapterArray2  = new ArrayAdapter<>(this,
                R.layout.custom_list_item, R.id.text_view_list_item, assists);
        editText[2].setAdapter(adapterArray2);
        final ArrayAdapter<String> adapterArray3  = new ArrayAdapter<>(this,
                R.layout.custom_list_item, R.id.text_view_list_item, specials);
        editText[3].setAdapter(adapterArray3);
        final ArrayAdapter<String> adapterArray4  = new ArrayAdapter<>(this,
                R.layout.custom_list_item, R.id.text_view_list_item, skillsA);
        editText[4].setAdapter(adapterArray4);
        final ArrayAdapter<String> adapterArray5  = new ArrayAdapter<>(this,
                R.layout.custom_list_item, R.id.text_view_list_item, skillsB);
        editText[5].setAdapter(adapterArray5);
        final ArrayAdapter<String> adapterArray6  = new ArrayAdapter<>(this,
                R.layout.custom_list_item, R.id.text_view_list_item, skillsC);
        editText[6].setAdapter(adapterArray6);

        // **** Start of AutoCompleteTextView code ****

        editText[0].setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                var[0] = adapterArray0.getItem(position);
            }
        });
        editText[0].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                var[0] = "";
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editText[1].setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                var[1] = adapterArray1.getItem(position);
            }
        });
        editText[1].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                var[1] = "";
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editText[2].setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                var[2] = adapterArray2.getItem(position);
            }
        });
        editText[2].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                var[2] = "";
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editText[3].setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                var[3] = adapterArray3.getItem(position);
            }
        });
        editText[3].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                var[3] = "";
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editText[4].setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                var[4] = adapterArray4.getItem(position);
            }
        });
        editText[4].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                var[4] = "";
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editText[5].setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                var[5] = adapterArray5.getItem(position);
            }
        });
        editText[5].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                var[5] = "";
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editText[6].setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                var[6] = adapterArray6.getItem(position);
            }
        });
        editText[6].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                var[6] = "";
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // **** End of AutoCompleteTextView code ****

        //User makes changes...

        //When user is done, they can click the save button to update the build info
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save the build to the database
                Build build;
                try{
                    build = new Build(id, buildText.getText().toString(), var[0], var[1],
                            var[2], var[3], var[4], var[5], var[6]);
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Error creating build.", Toast.LENGTH_SHORT).show();
                    build = new Build(-1, "Error", "", "", "", "", "", "", "");
                }


                DataBaseHelper dataBaseHelper = new DataBaseHelper(getApplicationContext());

                //Update build
                if(MainActivity.isPro) {
                    boolean success = dataBaseHelper.updateBuild(build);
                    Toast.makeText(getApplicationContext(), "Success: " + success, Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Pro version is required to edit builds", Toast.LENGTH_SHORT).show();
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
            DataBaseHelper dataBaseHelper = new DataBaseHelper(EditBuildActivity.this);
            dataBaseHelper.deleteAllBuilds();

            return true;
        }
        else if (id == R.id.pro_upgrade){
            if(MainActivity.isPro){
                MainActivity.isPro = false;
                editor.putBoolean("isPro_key", false);
                editor.apply();
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
                    EditBuildActivity.this.recreate();
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("start_night_mode_key", true);
                    editor.commit();
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
