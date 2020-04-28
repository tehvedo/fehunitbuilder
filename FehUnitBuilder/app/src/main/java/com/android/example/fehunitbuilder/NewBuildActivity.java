/*
 * Kevin Kochanek & Evan Cruzen
 * CIS 433 Project - Feh Unit Builder
 * NewBuildActivity.java
 */

package com.android.example.fehunitbuilder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
import static com.android.example.fehunitbuilder.MainActivity.EXTRA_DATA_ID;
import static com.android.example.fehunitbuilder.MainActivity.EXTRA_DATA_UPDATE_BUILD;
import static com.android.example.fehunitbuilder.MainActivity.EXTRA_DATA_UPDATE_UNIT;
import static com.android.example.fehunitbuilder.MainActivity.EXTRA_DATA_UPDATE_WEAPON;
import static com.android.example.fehunitbuilder.MainActivity.EXTRA_DATA_UPDATE_ASSIST;
import static com.android.example.fehunitbuilder.MainActivity.EXTRA_DATA_UPDATE_SPECIAL;
import static com.android.example.fehunitbuilder.MainActivity.EXTRA_DATA_UPDATE_ASKILL;
import static com.android.example.fehunitbuilder.MainActivity.EXTRA_DATA_UPDATE_BSKILL;
import static com.android.example.fehunitbuilder.MainActivity.EXTRA_DATA_UPDATE_CSKILL;*/

/**
 * This class displays a screen where the user enters a new build.
 * The NewBuildActivity returns the entered build to the calling activity
 * (MainActivity), which then stores the new build and updates the list of
 * displayed builds.
 */

public class NewBuildActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.fehunitbuilder.REPLY";
    public static final String EXTRA_DATA_ID = "com.example.android.fehunitbuilder.DATA_ID";
    public static final String EXTRA_DATA_UPDATE_BUILD = "com.example.android.fehunitbuilder.DATA_UPDATE_BUILD";
    public static final String EXTRA_DATA_UPDATE_UNIT = "com.example.android.fehunitbuilder.DATA_UPDATE_UNIT";
    public static final String EXTRA_DATA_UPDATE_WEAPON = "com.example.android.fehunitbuilder.DATA_UPDATE_WEAPON";
    public static final String EXTRA_DATA_UPDATE_ASSIST = "com.example.android.fehunitbuilder.DATA_UPDATE_ASSIST";
    public static final String EXTRA_DATA_UPDATE_SPECIAL = "com.example.android.fehunitbuilder.DATA_UPDATE_SPECIAL";
    public static final String EXTRA_DATA_UPDATE_ASKILL = "com.example.android.fehunitbuilder.DATA_UPDATE_ASKILL";
    public static final String EXTRA_DATA_UPDATE_BSKILL = "com.example.android.fehunitbuilder.DATA_UPDATE_BSKILL";
    public static final String EXTRA_DATA_UPDATE_CSKILL = "com.example.android.fehunitbuilder.DATA_UPDATE_CSKILL";

    public static final String EXTRA_REPLY_ID = "com.android.example.fehunitbuilder.REPLY_ID";

    private EditText[] mEditBuildView = new EditText[8];


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_build);

        mEditBuildView[0] = findViewById(R.id.edit_build);
        mEditBuildView[1] = findViewById(R.id.edit_unit);
        mEditBuildView[2] = findViewById(R.id.edit_weapon);
        mEditBuildView[3] = findViewById(R.id.edit_assist);
        mEditBuildView[4] = findViewById(R.id.edit_special);
        mEditBuildView[5] = findViewById(R.id.edit_a_skill);
        mEditBuildView[6] = findViewById(R.id.edit_b_skill);
        mEditBuildView[7] = findViewById(R.id.edit_c_skill);

        //int id = -1;

        final Bundle extras = getIntent().getExtras();
        // If we are passed content, fill it in for the user to edit.
        if (extras != null) {
            String[] build = new String[8];
            build[0] = extras.getString(EXTRA_DATA_UPDATE_BUILD, "");
            build[1] = extras.getString(EXTRA_DATA_UPDATE_UNIT, "");
            build[2] = extras.getString(EXTRA_DATA_UPDATE_WEAPON, "");
            build[3] = extras.getString(EXTRA_DATA_UPDATE_ASSIST, "");
            build[4] = extras.getString(EXTRA_DATA_UPDATE_SPECIAL, "");
            build[5] = extras.getString(EXTRA_DATA_UPDATE_ASKILL, "");
            build[6] = extras.getString(EXTRA_DATA_UPDATE_BSKILL, "");
            build[7] = extras.getString(EXTRA_DATA_UPDATE_CSKILL, "");
            for (int i = 0; i < 8; i++) {
                if (!build[i].isEmpty()) {
                    mEditBuildView[i].setText(build[i]);
                    mEditBuildView[i].setSelection(build[i].length());
                    mEditBuildView[i].requestFocus();
                }
            } // Otherwise, start with empty fields.

            final Button button = findViewById(R.id.button_save);

            // When the user presses the Save button, create a new Intent for the reply.
            // The reply Intent will be sent back to the calling activity (in this case, MainActivity).
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    // Create a new Intent for the reply.
                    Intent replyIntent = new Intent();

                    //Build build = new Build();
                    if (TextUtils.isEmpty(mEditBuildView[0].getText())) {
                        // No build was entered, set the result accordingly.
                        setResult(RESULT_CANCELED, replyIntent);
                        Toast.makeText(NewBuildActivity.this, "No build entered.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(NewBuildActivity.this, "Build entered, saving.", Toast.LENGTH_LONG).show();

                        // Set build name for screen
                        replyIntent.putExtra(EXTRA_REPLY, mEditBuildView[0].getText().toString());

                        // Set build details
                        replyIntent.putExtra(EXTRA_DATA_UPDATE_BUILD, mEditBuildView[0].getText().toString());
                        replyIntent.putExtra(EXTRA_DATA_UPDATE_UNIT, mEditBuildView[1].getText().toString());
                        replyIntent.putExtra(EXTRA_DATA_UPDATE_WEAPON, mEditBuildView[2].getText().toString());
                        replyIntent.putExtra(EXTRA_DATA_UPDATE_ASSIST, mEditBuildView[3].getText().toString());
                        replyIntent.putExtra(EXTRA_DATA_UPDATE_SPECIAL, mEditBuildView[4].getText().toString());
                        replyIntent.putExtra(EXTRA_DATA_UPDATE_ASKILL, mEditBuildView[5].getText().toString());
                        replyIntent.putExtra(EXTRA_DATA_UPDATE_BSKILL, mEditBuildView[6].getText().toString());
                        replyIntent.putExtra(EXTRA_DATA_UPDATE_CSKILL, mEditBuildView[7].getText().toString());

                        // Put the new build in the extras for the reply Intent.
                        if (extras.containsKey(EXTRA_DATA_ID)) {
                            int id = extras.getInt(EXTRA_DATA_ID, -1);
                            if (id != -1) {
                                replyIntent.putExtra(EXTRA_REPLY_ID, id);
                            }
                        }
                        // Set the result status to indicate success.
                        setResult(RESULT_OK, replyIntent);
                    }
                    finish();
                }
            });
        }
    }
}
