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

import static com.android.example.fehunitbuilder.MainActivity.EXTRA_DATA_ID;
import static com.android.example.fehunitbuilder.MainActivity.EXTRA_DATA_UPDATE_BUILD;

/**
 * This class displays a screen where the user enters a new build.
 * The NewBuildActivity returns the entered build to the calling activity
 * (MainActivity), which then stores the new build and updates the list of
 * displayed builds.
 */
public class NewBuildActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.fehunitbuilder.REPLY";
    public static final String EXTRA_REPLY_ID = "com.android.example.fehunitbuilder.REPLY_ID";

    private EditText mEditBuildView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_build);

        mEditBuildView = findViewById(R.id.edit_build);
        int id = -1 ;

        final Bundle extras = getIntent().getExtras();

        // If we are passed content, fill it in for the user to edit.
        if (extras != null) {
            String build = extras.getString(EXTRA_DATA_UPDATE_BUILD, "");
            if (!build.isEmpty()) {
                mEditBuildView.setText(build);
                mEditBuildView.setSelection(build.length());
                mEditBuildView.requestFocus();
            }
        } // Otherwise, start with empty fields.


        final Button button = findViewById(R.id.button_save);

        // When the user presses the Save button, create a new Intent for the reply.
        // The reply Intent will be sent back to the calling activity (in this case, MainActivity).
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Create a new Intent for the reply.
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditBuildView.getText())) {
                    // No build was entered, set the result accordingly.
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    // Get the new build that the user entered.
                    String build = mEditBuildView.getText().toString();
                    // Put the new build in the extras for the reply Intent.
                    replyIntent.putExtra(EXTRA_REPLY, build);
					if (extras != null && extras.containsKey(EXTRA_DATA_ID)) {
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
