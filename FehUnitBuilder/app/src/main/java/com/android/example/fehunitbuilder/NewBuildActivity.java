/*
 * Kevin Kochanek & Evan Cruzen
 * CIS 433 Project - Feh Unit Builder
 * NewBuildActivity.java
 */

package com.android.example.fehunitbuilder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This class displays a screen where the user enters a new build.
 * The NewBuildActivity returns the entered build to the calling activity
 * (MainActivity), which then stores the new build and updates the list of
 * displayed builds.
 */
public class NewBuildActivity extends AppCompatActivity {

    EditText et_build_name, et_unit, et_weapon, et_assist,
    et_special, et_a_skill, et_b_skill, et_c_skill;

    Button btn_save;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_build);

        et_build_name = findViewById(R.id.edit_build_name);
        et_unit = findViewById(R.id.edit_unit);
        et_weapon = findViewById(R.id.edit_weapon);
        et_assist = findViewById(R.id.edit_assist);
        et_special = findViewById(R.id.edit_special);
        et_a_skill = findViewById(R.id.edit_a_skill);
        et_b_skill = findViewById(R.id.edit_b_skill);
        et_c_skill = findViewById(R.id.edit_c_skill);
        btn_save = findViewById(R.id.button_save);

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

                    Toast.makeText(getApplicationContext(), build.toString(), Toast.LENGTH_SHORT).show();
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Error creating build.", Toast.LENGTH_SHORT).show();
                    build = new Build(-1, "Error", "", "", "", "", "", "", "");
                }


                DataBaseHelper dataBaseHelper = new DataBaseHelper(NewBuildActivity.this);

                boolean success = dataBaseHelper.addOne(build);

                Toast.makeText(getApplicationContext(), "Success: " + success, Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }
}
