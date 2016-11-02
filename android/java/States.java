package com.example.coop.schedula;

import com.alamkanak.weekview.WeekViewEvent;

import java.util.ArrayList;

/**
 * Created by coop on 2016-10-13.
 */

public class States {

    // CourseSelector
    static Boolean IS_SET = false;
    static ArrayList<String> SELECTED_COURSES = new ArrayList<>();
    static ArrayList<String> AVAILABLE_COURSES = new ArrayList<>();

    // Bundle : CourseSelector -> CoursesFinal
    static String eventSets;
}
