package com.example.coop.schedulaui;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import SchedulaAlgo.Course;

/**
 * Created by coop on 2016-10-11.
 */

public class ActivitySelector extends AppCompatActivity {

    private  final String TAG = this.getClass().getSimpleName() + " @" + System.identityHashCode(this);

    /*
     * UI elements
     */
    private ClearableAutoCompleteTextView course_selector;
    private Button fallButton;
    private Button winterButton;

    /*
     * Data elements
     */
    private ArrayList<Course> selectedCourses;
    private ArrayList<Course> availableCourses;
    private ArrayAdapter<Course> selectorAdapter;
    private ArrayAdapter<Course> listAdapter;

    /*
     * Unable to re-initialize the ArrayAdapter of an object within it's own OnItemClickListener
     * Therefore, do it outside the OnItemClickListener
     */
    private void updateSelector() {
        selectorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, availableCourses);
        course_selector.setAdapter(selectorAdapter);
    }

    /*
     * Called before onCreate of the new Configuration.
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        System.out.println(States.SELECTED_COURSES);

        States.AVAILABLE_COURSES = availableCourses;
        States.SELECTED_COURSES = selectedCourses;
        States.IS_SET = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);

        /*
         * Init the fall term button
         */
        fallButton = (Button) findViewById(R.id.button_fall);
        fallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                States.term = "201630";
                fallButton.setClickable(false);
                fallButton.setAlpha(0.3f);
                winterButton.setClickable(true);
                winterButton.setAlpha(1.0f);
                availableCourses.addAll(selectedCourses);
                selectedCourses.clear();
                availableCourses = States.coursesFall;

                course_selector.setText("");
                listAdapter.notifyDataSetChanged();
                updateSelector();
            }
        });

        /*
         * Init the winter term button
         */
        winterButton = (Button) findViewById(R.id.button_winter);
        winterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                States.term = "201710";
                winterButton.setClickable(false);
                winterButton.setAlpha(0.3f);
                fallButton.setClickable(true);
                fallButton.setAlpha(1.0f);
                availableCourses.addAll(selectedCourses);
                selectedCourses.clear();
                availableCourses = States.coursesWint;

                course_selector.setText("");
                listAdapter.notifyDataSetChanged();
                updateSelector();
            }
        });

        /*
         * Setup additional resources needed
         */
        if (States.term.equals("201710")) {
            availableCourses = States.coursesWint;
            winterButton.setClickable(false);
            winterButton.setAlpha(0.3f);
        } else {
            availableCourses = States.coursesFall;
            fallButton.setClickable(false);
            fallButton.setAlpha(0.3f);
        }
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
                Course selection = (Course) parent.getItemAtPosition(pos);

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
                States.AVAILABLE_COURSES = availableCourses;
                States.SELECTED_COURSES = selectedCourses;
                States.IS_SET = true;

                Intent intent = new Intent(ActivitySelector.this, ActivityPreferences.class);

                startActivity(intent);
            }
        });
    }
}
