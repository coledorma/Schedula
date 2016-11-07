package com.example.coop.schedulaui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import SchedulaAlgo.Commitment;
import SchedulaAlgo.Schedule;


/**
 * Created by ColeDorma on 2016-10-20.
 */

public class ActivityPreferences extends AppCompatActivity{

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    Button generateButton;
    String preferredTOD = "";
    EditText preferredTime;
    Button addTime;
    List<String> specificTimes = new ArrayList<String>();
    List<Character> specificDays = new ArrayList<Character>();
    TextView specificTimesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomizableExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        generateButton = (Button) findViewById(R.id.generateButton);
        preferredTime = (EditText) findViewById(R.id.specificTime);
        addTime = (Button) findViewById(R.id.addTime);
        specificTimesList = (TextView) findViewById(R.id.specificTimesList);

        addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                specificTimes.add(preferredTime.getText().toString());

                Log.d("myTag", "This is in preferredDay: " + specificTimes);

                specificTimesList.setText("Specific Time(s) Listed: " + specificTimes.toString());

                preferredTime.setText("");
            }
        });

        final CheckedTextView checkMon = (CheckedTextView) findViewById(R.id.checkMon);
        checkMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkMon.isChecked()) {
                    checkMon.setChecked(false);
                    specificDays.remove('M');
                }else {
                    checkMon.setChecked(true);
                    specificDays.add('M');
                }

            }
        });

        final CheckedTextView checkTue = (CheckedTextView) findViewById(R.id.checkTue);
        checkTue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkTue.isChecked()) {
                    checkTue.setChecked(false);
                    specificDays.remove('T');
                }else {
                    checkTue.setChecked(true);
                    specificDays.add('T');
                }

            }
        });

        final CheckedTextView checkWed = (CheckedTextView) findViewById(R.id.checkWed);
        checkWed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkWed.isChecked()) {
                    checkWed.setChecked(false);
                    specificDays.remove('W');
                }else {
                    checkWed.setChecked(true);
                    specificDays.add('W');
                }

            }
        });

        final CheckedTextView checkThu = (CheckedTextView) findViewById(R.id.checkThu);
        checkThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkThu.isChecked()) {
                    checkThu.setChecked(false);
                    specificDays.remove('R');
                }else {
                    checkThu.setChecked(true);
                    specificDays.add('R');
                }

            }
        });

        final CheckedTextView checkFri = (CheckedTextView) findViewById(R.id.checkFri);
        checkFri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFri.isChecked()) {
                    checkFri.setChecked(false);
                    specificDays.remove('F');
                }else {
                    checkFri.setChecked(true);
                    specificDays.add('F');
                }

            }
        });

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long start = System.nanoTime();
                System.out.println(States.SELECTED_COURSES);
                States.SCHEDULE = new Schedule(new ArrayList<Commitment>(), States.SELECTED_COURSES);
                long stop = System.nanoTime();
                System.out.println("Finished generating: " + (start - stop) / 1000000000.0 + "s");

                Intent intent = new Intent(ActivityPreferences.this, ActivityFinal.class);
                Bundle bundle = new Bundle();

                ArrayList<ArrayList<Object>> eventSetA = new ArrayList<>();
                ArrayList<ArrayList<Object>> eventSetB = new ArrayList<>();
                ArrayList<ArrayList<Object>> eventSetC = new ArrayList<>();

                ArrayList<Object> startEndPairA = new ArrayList<>();
                ArrayList<Object> startEndPairB = new ArrayList<>();

                // Populate the week view with some events.
                Calendar startTime;
                Calendar endTime;

                startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR, 10);
                startTime.set(Calendar.MINUTE, 5);
                startTime.set(Calendar.AM_PM, Calendar.AM);
                startTime.set(Calendar.MONTH, Calendar.NOVEMBER);
                startTime.set(Calendar.DAY_OF_MONTH, 1);
                startTime.set(Calendar.YEAR, 2016);
                startEndPairA.add(startTime);

                endTime = Calendar.getInstance();
                endTime.set(Calendar.HOUR, 11);
                endTime.set(Calendar.MINUTE, 25);
                endTime.set(Calendar.AM_PM, Calendar.AM);
                endTime.set(Calendar.MONTH, Calendar.NOVEMBER);
                endTime.set(Calendar.DAY_OF_MONTH, 1);
                endTime.set(Calendar.YEAR, 2016);
                startEndPairA.add(endTime);
                startEndPairA.add("COMP\n3004");

                startTime = Calendar.getInstance();
                startTime.set(Calendar.HOUR, 10);
                startTime.set(Calendar.MINUTE, 5);
                startTime.set(Calendar.AM_PM, Calendar.AM);
                startTime.set(Calendar.MONTH, Calendar.NOVEMBER);
                startTime.set(Calendar.DAY_OF_MONTH, 3);
                startTime.set(Calendar.YEAR, 2016);
                startEndPairB.add(startTime);

                endTime = Calendar.getInstance();
                endTime.set(Calendar.HOUR, 11);
                endTime.set(Calendar.MINUTE, 25);
                endTime.set(Calendar.AM_PM, Calendar.AM);
                endTime.set(Calendar.MONTH, Calendar.NOVEMBER);
                endTime.set(Calendar.DAY_OF_MONTH, 3);
                endTime.set(Calendar.YEAR, 2016);
                startEndPairB.add(endTime);
                startEndPairB.add("COMP\n3000");

                eventSetA.add(startEndPairA);

                eventSetB.add(startEndPairB);

                eventSetC.add(startEndPairA);
                eventSetC.add(startEndPairB);

                ArrayList<ArrayList<ArrayList<Object>>> eventSets = new ArrayList<>();
                eventSets.add(eventSetA);
                eventSets.add(eventSetB);
                eventSets.add(eventSetC);

                bundle.putSerializable(States.eventSets, eventSets);
                intent.putExtras(bundle);

                Log.d("myTag", "This is in specificDay: " + specificDays);

                startActivity(intent);
            }
        });


        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                preferredTOD = expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition);

                Log.d("myTag", "This is in preferredDay: " + preferredTOD);

                Toast.makeText(getApplicationContext(),
                        expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition) + " chosen.",
                        Toast.LENGTH_SHORT).show();

                expandableListView.collapseGroup(0);

                return false;
            }
        });
    }
}
