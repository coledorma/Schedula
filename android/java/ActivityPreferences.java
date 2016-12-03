package com.example.coop.schedulaui;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListAdapter;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import SchedulaAlgo.Commitment;
import SchedulaAlgo.Schedule;
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
    private Button specificTimeStart;
    private Button specificTimeEnd;
    private Button addTime;
    private Button generateButton;
    private String finalSpecificTime = "";
    private Spinner daySpinner;
    private String dayString;
    private Spinner specificTimeSpinner;
    private boolean isSpinnerTouched = false;
    private boolean onlinePref = false;



    /*
     * Data Elements
     */
    private String preferredTOD = "";
    private ArrayList<String> specificTimes = new ArrayList<String>();
    private List<String> expandableListTitle;
    private ArrayList<String> specificDays = new ArrayList<>();
    private HashMap<String, List<String>> expandableListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        Resources res = getResources();
        final String[] daysArray = res.getStringArray(R.array.days_array);

        if (States.PREF_SET){
            Log.i("myTag", "Prefs loaded");
            preferredTOD = States.preferredTOD;
            specificDays = States.specificDays;
            specificTimes = States.specificTimes;
        }

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        expandableListAdapter = new CustomizableExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        generateButton = (Button) findViewById(R.id.generateButton);
        specificTimeStart = (Button) findViewById(R.id.specificTimeStart);
        specificTimeEnd = (Button) findViewById(R.id.specificTimeEnd);
        addTime = (Button) findViewById(R.id.addTime);

        daySpinner = (Spinner) findViewById(R.id.daySpinner);
        ArrayAdapter<CharSequence> dayAdapter = ArrayAdapter.createFromResource(this, R.array.days_array, android.R.layout.simple_spinner_item);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        daySpinner.setAdapter(dayAdapter);

        specificTimeSpinner = (Spinner) findViewById(R.id.specificTimeSpinner);
        final ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeAdapter.add("SPECIFIC TIME(S)");
        timeAdapter.notifyDataSetChanged();
        specificTimeSpinner.setAdapter(timeAdapter);


        specificTimeSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isSpinnerTouched = true;
                return false;
            }
        });

        specificTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                if (!isSpinnerTouched) return;

                Log.d("myTag", "REMOVE: " + specificTimeSpinner.getSelectedItem().toString());
                timeAdapter.remove(specificTimeSpinner.getSelectedItem().toString());
                timeAdapter.notifyDataSetChanged();
                specificTimeSpinner.setAdapter(timeAdapter);
                int index = specificTimeSpinner.getSelectedItemPosition();
                specificTimes.remove(index);
                Log.d("myTag", "REMOVE specificTimes: " + specificTimes);

                isSpinnerTouched = false;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {

                if (daySpinner.getSelectedItem().toString().equals("Monday")){
                   dayString = "M";
                } else if (daySpinner.getSelectedItem().toString().equals("Tuesday")){
                    dayString = "T";
                } else if (daySpinner.getSelectedItem().toString().equals("Wednesday")){
                    dayString = "W";
                } else if (daySpinner.getSelectedItem().toString().equals("Thursday")){
                    dayString = "R";
                } else if (daySpinner.getSelectedItem().toString().equals("Friday")){
                    dayString = "F";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });


        specificTimeStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ActivityPreferences.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        specificTimeStart.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select A Start Time:");
                mTimePicker.show();

            }
        });

        specificTimeEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(ActivityPreferences.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        specificTimeEnd.setText(String.format("%02d:%02d", selectedHour, selectedMinute));
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select An End Time:");
                mTimePicker.show();

            }
        });

        addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finalSpecificTime = dayString + " " + specificTimeStart.getText().toString() + "-" + specificTimeEnd.getText().toString();

                Log.d("myTag", "FINAL SPECIFIC TIME " + finalSpecificTime);

                if (finalSpecificTime.indexOf(":") == 4 && finalSpecificTime.indexOf("-") == 7 &&
                        finalSpecificTime.charAt(10) == ':' && finalSpecificTime.length() == 13){

                    specificTimes.add(finalSpecificTime);
                    timeAdapter.add(finalSpecificTime);

                    Log.d("myTag", "This is in specificTimes: " + specificTimes);

                } else {

                    Toast.makeText(getApplicationContext(), "Incorrect format of time. Try again.", Toast.LENGTH_SHORT).show();
                    specificTimes.remove(specificTimeStart.getText().toString());
                }

                specificTimeStart.setText("START\nTIME");
                specificTimeEnd.setText("END\nTIME");

            }
        });

        final CheckBox checkMon = (CheckBox) findViewById(R.id.checkMon);
        checkMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkMon.isChecked()) {
                    checkMon.setChecked(false);
                    specificDays.remove(specificDays.indexOf("M"));
                }else {
                    checkMon.setChecked(true);
                    specificDays.add("M");
                }

            }

        });

        final CheckBox checkTue = (CheckBox) findViewById(R.id.checkTue);
        checkTue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTue.isChecked()) {
                    checkTue.setChecked(false);
                    specificDays.remove(specificDays.indexOf("T"));
                }else {
                    checkTue.setChecked(true);
                    specificDays.add("T");
                }

            }
        });

        final CheckBox checkWed = (CheckBox) findViewById(R.id.checkWed);
        checkWed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkWed.isChecked()) {
                    checkWed.setChecked(false);
                    specificDays.remove(specificDays.indexOf("W"));
                }else {
                    checkWed.setChecked(true);
                    specificDays.add("W");
                }

            }
        });

        final CheckBox checkThu = (CheckBox) findViewById(R.id.checkThu);
        checkThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkThu.isChecked()) {
                    checkThu.setChecked(false);
                    specificDays.remove(specificDays.indexOf("R"));
                }else {
                    checkThu.setChecked(true);
                    specificDays.add("R");
                }

            }
        });

        final CheckBox checkFri = (CheckBox) findViewById(R.id.checkFri);
        checkFri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkFri.isChecked()) {
                    checkFri.setChecked(false);
                    specificDays.remove(specificDays.indexOf("F"));
                }else {
                    checkFri.setChecked(true);
                    specificDays.add("F");
                }

            }
        });
        
        final CheckBox onlineYesCheck = (CheckBox) findViewById(R.id.onlineYesCheck);
        checkFri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!onlineYesCheck.isChecked()) {
                    onlineYesCheck.setChecked(false);
                    onlinePref = false;
                }else {
                    onlineYesCheck.setChecked(true);
                    onlinePref = true;
                }

                Log.d("myTag", "Online Classes Preference: " + onlinePref);

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

                if (States.term.equals("201710")) {
                    ScheduleGenerator schedules = new ScheduleGenerator(States.SELECTED_COURSES_WINT, commitments, new ArrayList<String>(), 10);
                    States.SCHEDULES = schedules.getSchedules();

                } else {
                    ScheduleGenerator schedules = new ScheduleGenerator(States.SELECTED_COURSES_FALL, commitments, new ArrayList<String>(), 10);
                    States.SCHEDULES = schedules.getSchedules();
                }

                for (Schedule schedule : States.SCHEDULES) {

                }
                Collections.sort(States.SCHEDULES);

                States.preferredTOD = preferredTOD;
                States.specificTimes = specificTimes;
                States.specificDays = specificDays;

                Log.d("myTag", "This is in specificDay: " + specificDays);

                States.PREF_SET = true;

                startActivity(new Intent(ActivityPreferences.this, ActivityFin.class));
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
