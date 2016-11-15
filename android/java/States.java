package com.example.coop.schedulaui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import SchedulaAlgo.Course;
import SchedulaAlgo.Schedule;

/**
 * Created by coop on 2016-10-13.
 */

public class States {

    // DB
    static Boolean dbQueried = false;

    // Course objects
    static ArrayList<Course> coursesFall = new ArrayList<>();
    static ArrayList<Course> coursesWint = new ArrayList<>();

    // ActivitySelect
    static Boolean IS_SET = false;
    static ArrayList<Course> SELECTED_COURSES_FALL = new ArrayList<>();
    static ArrayList<Course> SELECTED_COURSES_WINT = new ArrayList<>();
    static String term = "201630";

    // ActivityPreferences
    static List<String> SPECIFIC_TIMES_FALL = new ArrayList<>();
    static List<String> SPECIFIC_TIMES_WINT = new ArrayList<>();
    static String PREFERRED_TOD_FALL = "";
    static String PREFERRED_TOD_WINT = "";
    static ArrayList<String> SPECIFIC_DAYS_FALL = new ArrayList<>();
    static ArrayList<String> SPECIFIC_DAYS_WINT = new ArrayList<>();


    static List<String> specificTimes = new ArrayList<>();
    static String preferredTOD = "";
    static ArrayList<String> specificDays = new ArrayList<>();
    static Boolean PREF_SET = false;

    // ActivityFinal
    static LinkedList<Schedule> SCHEDULES;
}
