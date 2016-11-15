package com.example.coop.schedulaui;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by coop on 2016-10-11.
 */

public class ActivityMain extends AppCompatActivity {

    /*
     * Called before onCreate of the new Configuration.
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
         * Start the parsing of the courses immediately when the app loads
         */
        if (!States.dbQueried) {
            InputStream stream = getResources().openRawResource(R.raw.courses);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            new TaskXMLParser().execute(reader);
            States.dbQueried = true;
        }

        findViewById(R.id.start_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityMain.this, ActivitySelect.class));
            }
        });
    }
}
