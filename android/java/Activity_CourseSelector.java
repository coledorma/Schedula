package com.example.coop.schedula;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.alamkanak.weekview.WeekViewEvent;

/**
 * Created by coop on 2016-10-11.
 */

public class CourseSelector extends AppCompatActivity {

    private  final String TAG = this.getClass().getSimpleName() + " @" + System.identityHashCode(this);

    /*
     * UI elements
     */
    private ClearableAutoCompleteTextView course_selector;

    /*
     * Data elements
     */
    private ArrayList<String> selectedCourses;
    private ArrayList<String> availableCourses;
    private ArrayAdapter<String> selectorAdapter;
    private ArrayAdapter<String> listAdapter;

    /*
     * Unable to re-initialize the ArrayAdapter of an object within it's own OnItemClickListener
     * Therefore, do it outside the OnItemClickListener
     */
    private void updateSelector() {
        selectorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, availableCourses);
        course_selector.setAdapter(selectorAdapter);
    }

    /*
     * Return a calendar event title with course code, section and time
     */
    public String generateCourseName(String code, Calendar startTime, Calendar endTime) {
        String courseName = code;

        String startHour = (startTime.get(Calendar.HOUR_OF_DAY) > 9) ? Integer.toString(startTime.get(Calendar.HOUR_OF_DAY)) : ("0" + startTime.get(Calendar.HOUR_OF_DAY));
        String startMin = (startTime.get(Calendar.MINUTE) > 9) ? Integer.toString(startTime.get(Calendar.MINUTE)) : ("0" + startTime.get(Calendar.MINUTE));
        String endHour = (endTime.get(Calendar.HOUR_OF_DAY) > 9) ? Integer.toString(endTime.get(Calendar.HOUR_OF_DAY)) : ("0" + endTime.get(Calendar.HOUR_OF_DAY));
        String endMin = (endTime.get(Calendar.MINUTE) > 9) ? Integer.toString(endTime.get(Calendar.MINUTE)) : ("0" + endTime.get(Calendar.MINUTE));

        courseName += "\n\n" + startHour + ":" + startMin;
        courseName += "\n" + endHour + ":" + endMin;

        return courseName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);

        /*
         * Load resources required
         */
        Resources res = getResources();
        final String[] raw_courses = res.getStringArray(R.array.courses_array);

        /*
         * Setup additional resources needed
         */
        availableCourses = new ArrayList<>(Arrays.asList(raw_courses));
        selectedCourses = new ArrayList<>();

        /*
         * Load already-created resources if present
         */
        if (States.IS_SET) {
            availableCourses = States.AVAILABLE_COURSES;
            selectedCourses = States.SELECTED_COURSES;
            States.IS_SET = false;
        }

        /*
         * Setup adapters from resources
         */
        selectorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, availableCourses);
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selectedCourses);

        /*
         * Init the search object
         */
        course_selector = (ClearableAutoCompleteTextView) findViewById(R.id.course_selector);
        course_selector.setAdapter(selectorAdapter);
        course_selector.setHint(R.string.course_selector_hint);
        course_selector.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String selection = course_selector.getText().toString();

                if (selectedCourses.contains(selection)) {
                    selectedCourses.remove(selection);
                    availableCourses.add(selection);
                } else {
                    selectedCourses.add(selection);
                    availableCourses.remove(selection);
                }

                course_selector.setText("");
                listAdapter.notifyDataSetChanged();
                updateSelector();
            }
        });

        /*
         * Init the chosen courses object
         */
        ListView courseList = (ListView) findViewById(R.id.course_list);
        courseList.setAdapter(listAdapter);
        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                availableCourses.add(selectedCourses.get(position));
                selectedCourses.remove(position);
                listAdapter.notifyDataSetChanged();
                updateSelector();
            }
        });


        /*
         * Init the next button
         */
        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * When data needs to be passed to the next screen...
                 */
                States.AVAILABLE_COURSES = availableCourses;
                States.SELECTED_COURSES = selectedCourses;
                States.IS_SET = true;

                Intent intent = new Intent(CourseSelector.this, Preferences.class);

                startActivity(intent);
            }
        });
    }
}
