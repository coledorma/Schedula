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
import java.util.HashMap;
import java.util.List;

import SchedulaAlgo.Commitment;
import SchedulaAlgo.ScheduleGenerator;


/**
 * Created by ColeDorma on 2016-10-20.
 */

public class ActivityPreferences extends AppCompatActivity{

    /*
     * UI Elements
     */
    private ExpandableListView expandableListView;
    private ExpandableListAdapter expandableListAdapter;
    private EditText preferredTime;
    private Button addTime;
    private Button generateButton;
    private TextView specificTimesList;

    /*
     * Data Elements
     */
    private String preferredTOD = "";
    private List<String> specificTimes = new ArrayList<String>();
    private List<String> expandableListTitle;
    private ArrayList<String> specificDays = new ArrayList<>();
    private HashMap<String, List<String>> expandableListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        if (States.PREF_SET){
            Log.i("myTag", "Prefs loaded");
            preferredTOD = States.preferredTOD;
            specificDays = States.specificDays;
            specificTimes = States.specificTimes;
        }

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
                Log.d("myTag", "This is in preferredDay: " + specificTimes);

                if (preferredTime.getText().toString().indexOf(":") == 4 && preferredTime.getText().toString().indexOf("-") == 7 &&
                        preferredTime.getText().toString().charAt(10) == ':' && preferredTime.getText().toString().length() == 13){
                    specificTimes.add(preferredTime.getText().toString());
                    specificTimesList.setText("Specific Time(s) Listed: " + specificTimes.toString());
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect format of time. Try again.", Toast.LENGTH_SHORT).show();
                    specificTimes.remove(preferredTime.getText().toString());
                }

                preferredTime.setText("");
            }
        });

        final CheckedTextView checkMon = (CheckedTextView) findViewById(R.id.checkMon);
        checkMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkMon.isChecked()) {
                    checkMon.setChecked(false);
                    specificDays.remove(specificDays.indexOf("M"));
                }else {
                    checkMon.setChecked(true);
                    specificDays.add("M");
                }

            }
        });

        final CheckedTextView checkTue = (CheckedTextView) findViewById(R.id.checkTue);
        checkTue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkTue.isChecked()) {
                    checkTue.setChecked(false);
                    specificDays.remove(specificDays.indexOf("T"));
                }else {
                    checkTue.setChecked(true);
                    specificDays.add("T");
                }

            }
        });

        final CheckedTextView checkWed = (CheckedTextView) findViewById(R.id.checkWed);
        checkWed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkWed.isChecked()) {
                    checkWed.setChecked(false);
                    specificDays.remove(specificDays.indexOf("W"));
                }else {
                    checkWed.setChecked(true);
                    specificDays.add("W");
                }

            }
        });

        final CheckedTextView checkThu = (CheckedTextView) findViewById(R.id.checkThu);
        checkThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkThu.isChecked()) {
                    checkThu.setChecked(false);
                    specificDays.remove(specificDays.indexOf("R"));
                }else {
                    checkThu.setChecked(true);
                    specificDays.add("R");
                }

            }
        });

        final CheckedTextView checkFri = (CheckedTextView) findViewById(R.id.checkFri);
        checkFri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkFri.isChecked()) {
                    checkFri.setChecked(false);
                    specificDays.remove(specificDays.indexOf("F"));
                }else {
                    checkFri.setChecked(true);
                    specificDays.add("F");
                }

            }
        });

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Commitment> commitments = new ArrayList<>();
                for (String time : specificTimes) {
                    System.out.println(time.replace(":", ""));
                    Commitment commitment = new Commitment("Commitment", Integer.parseInt(States.term), time.replace(":", ""));
                    commitments.add(commitment);
                }

                for (String dayOff : specificDays) {
                    String time = "";
                    switch (dayOff) {
                        case "M":
                            time = "M 0001-2359"; break;
                        case "T":
                            time = "T 0001-2359"; break;
                        case "W":
                            time = "W 0001-2359"; break;
                        case "R":
                            time = "R 0001-2359"; break;
                        case "F":
                            time = "F 0001-2359"; break;
                    }
                    Commitment commitment = new Commitment("Day Off", Integer.parseInt(States.term), time);
                    commitments.add(commitment);
                }

                System.out.println(commitments);

                if (States.term.equals("201710")) {
                    ScheduleGenerator schedules = new ScheduleGenerator(States.SELECTED_COURSES_WINT, commitments, new ArrayList<String>(), 10);
                    States.SCHEDULES = schedules.getSchedules();

                } else {
                    ScheduleGenerator schedules = new ScheduleGenerator(States.SELECTED_COURSES_FALL, commitments, new ArrayList<String>(), 10);
                    States.SCHEDULES = schedules.getSchedules();
                }

                States.preferredTOD = preferredTOD;
                States.specificTimes = specificTimes;
                States.specificDays = specificDays;

                Log.d("myTag", "This is in specificDay: " + specificDays);

                States.PREF_SET = true;

                startActivity(new Intent(ActivityPreferences.this, ActivityFinal.class));
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
