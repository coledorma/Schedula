package com.example.coop.schedula;

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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


/**
 * Created by ColeDorma on 2016-10-20.
 */

public class Preferences extends AppCompatActivity{

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    Button generateButton;
    String preferredTOD = "";
    EditText preferredTime;
    Button addTime;
    List<String> specificTimes = new ArrayList<String>();
    List<String> specificDays = new ArrayList<String>();
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
                    specificDays.remove("Monday");
                }else {
                    checkMon.setChecked(true);
                    specificDays.add("Monday");
                }

            }
        });

        final CheckedTextView checkTue = (CheckedTextView) findViewById(R.id.checkTue);
        checkTue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkTue.isChecked()) {
                    checkTue.setChecked(false);
                    specificDays.remove("Tuesday");
                }else {
                    checkTue.setChecked(true);
                    specificDays.add("Tuesday");
                }

            }
        });

        final CheckedTextView checkWed = (CheckedTextView) findViewById(R.id.checkWed);
        checkWed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkWed.isChecked()) {
                    checkWed.setChecked(false);
                    specificDays.remove("Wednesday");
                }else {
                    checkWed.setChecked(true);
                    specificDays.add("Wednesday");
                }

            }
        });

        final CheckedTextView checkThu = (CheckedTextView) findViewById(R.id.checkThu);
        checkThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkThu.isChecked()) {
                    checkThu.setChecked(false);
                    specificDays.remove("Thursday");
                }else {
                    checkThu.setChecked(true);
                    specificDays.add("Thursday");
                }

            }
        });

        final CheckedTextView checkFri = (CheckedTextView) findViewById(R.id.checkFri);
        checkFri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFri.isChecked()) {
                    checkFri.setChecked(false);
                    specificDays.remove("Friday");
                }else {
                    checkFri.setChecked(true);
                    specificDays.add("Friday");
                }

            }
        });

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Preferences.this, CoursesFinal.class);
                Bundle bundle = new Bundle();

                ArrayList<ArrayList<Calendar>> eventSetA = new ArrayList<>();
                ArrayList<ArrayList<Calendar>> eventSetB = new ArrayList<>();
                ArrayList<ArrayList<Calendar>> eventSetC = new ArrayList<>();

                ArrayList<Calendar> startEndPairA = new ArrayList<>();
                ArrayList<Calendar> startEndPairB = new ArrayList<>();

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

                eventSetA.add(startEndPairA);

                eventSetB.add(startEndPairB);

                eventSetC.add(startEndPairA);
                eventSetC.add(startEndPairB);

                ArrayList<ArrayList<ArrayList<Calendar>>> eventSets = new ArrayList<>();
                eventSets.add(eventSetA);
                eventSets.add(eventSetB);
                eventSets.add(eventSetC);

                bundle.putSerializable(States.eventSets, eventSets);
                intent.putExtras(bundle);

                /*
                When data needs to be passed...

                Bundle bundle = new Bundle();
                intent.putExtra(States.STATE_BUNDLE, bundle);
                */
                Log.d("myTag", "This is in specificDay: " + specificDays);

                startActivity(intent);
            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                /*Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                /*Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();*/

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                /*Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();*/

                preferredTOD = expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition);
                Log.d("myTag", "This is in preferredDay: " + preferredTOD);

                expandableListView.collapseGroup(0);

                return false;
            }
        });
    }


}

