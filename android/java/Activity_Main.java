package com.example.coop.schedula;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by coop on 2016-10-11.
 */

public class MainActivity extends AppCompatActivity {

    private Button goButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Start the parsing of the courses immediately when the app loads
         */
        InputStream stream = getResources().openRawResource(R.raw.courses);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        new XMLParserTask().execute(reader);

        goButton = (Button) findViewById(R.id.start_button);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, CourseSelector.class);

                /*
                When data needs to be passed...

                Bundle bundle = new Bundle();
                */

                startActivity(intent);
            }
        });
    }
}
