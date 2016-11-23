package com.example.coop.schedulaui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
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


        FloatingActionButton startFAB = (FloatingActionButton) findViewById(R.id.startFAB);
        startFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityMain.this, ActivitySelect.class));
            }
        });

        FloatingActionButton infoFAB = (FloatingActionButton) findViewById(R.id.infoFAB);
        infoFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(ActivityMain.this)
                        .setTitle("Information")
                        .setMessage("Authors:\nCole Dorma\nEvan Cooper\nJacob Perks\nDaniel Fitzhenry\n")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();

            }
        });
    }
}
