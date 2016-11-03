package com.example.coop.schedula;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;

/**
 * Created by coop on 2016-10-20.
 */

/*
 * TODO
 * Implement selectButton
 * setMaxDate for mWeekView
 */

public class CoursesFinal extends AppCompatActivity implements MonthLoader.MonthChangeListener, WeekView.ScrollListener, WeekView.EventClickListener {

    private WeekView mWeekView;
    private Button selectButton;

    private Boolean mWeekViewLoaded;
    private Boolean mWeekViewSet;

    private List<WeekViewEvent> activeCourseSet;
    public ArrayList<List<WeekViewEvent>> availableCourseSets;

    /*
     * Scrolls the WeekView to the previous Monday
     */
    private void scrollWeekViewToDay() {
        Calendar prevMon = Calendar.getInstance();
        prevMon.set(Calendar.DAY_OF_YEAR, activeCourseSet.get(0).getStartTime().get(Calendar.DAY_OF_YEAR));

        while (prevMon.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            prevMon.set(Calendar.DAY_OF_YEAR, prevMon.get(Calendar.DAY_OF_YEAR) - 1);
        }

        if (!mWeekViewSet) {
            mWeekView.setMinDate(prevMon);
            mWeekViewSet = true;
        }

        mWeekView.goToDate(prevMon);
    }

    /*
     * Scrolls the WeekView to the hour of day for the earliest course in the schedule
     */
    private void scrollWeekViewToHour() {
        double minHour = 24L;
        for (int i = 0; i < activeCourseSet.size(); i++) {
            if (activeCourseSet.get(i).getStartTime().get(Calendar.HOUR_OF_DAY) < minHour) {
                minHour = activeCourseSet.get(i).getStartTime().get(Calendar.HOUR_OF_DAY);
            }
        }

        mWeekView.goToHour(minHour);
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
        setContentView(R.layout.activity_final);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        ArrayList<ArrayList<ArrayList<Calendar>>> eventSets = (ArrayList<ArrayList<ArrayList<Calendar>>>) bundle.getSerializable(States.eventSets);

        mWeekViewLoaded = false;
        mWeekViewSet = false;

        final LinearLayout scheduleSelector = (LinearLayout) findViewById(R.id.schedule_selector);
        scheduleSelector.removeAllViews();

        availableCourseSets = new ArrayList<>();
        activeCourseSet = null;

        for (int i = 0; i < eventSets.size(); i++) {
            /*
             * Create a button for each schedule generated
             * Each button is paired with a data type
             * The data set of the WeekView will change with the button click
             */

            final int j = i;

            Button aButton = new Button(this);
            aButton.setText(Integer.toString(i + 1));
            aButton.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
            aButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Change data set for the WeekView
                    activeCourseSet = availableCourseSets.get(j);
                    mWeekView.notifyDatasetChanged();

                    // Avoid reloading data that is already displayed
                    for (int k = 0; k < scheduleSelector.getChildCount(); k++) {
                        if (k == j) {
                            scheduleSelector.getChildAt(k).setAlpha(0.3f);
                            scheduleSelector.getChildAt(k).setClickable(false);
                        } else {
                            scheduleSelector.getChildAt(k).setAlpha(1.0f);
                            scheduleSelector.getChildAt(k).setClickable(true);
                        }
                    }
                }
            });

            scheduleSelector.addView(aButton);

            if (i == 0) {
                aButton.setAlpha(0.3f);
                aButton.setClickable(false);
            }
        }

        selectButton = (Button) findViewById(R.id.select_button);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) findViewById(R.id.weekView);
        mWeekView.setMonthChangeListener(this);
        mWeekView.setScrollListener(this);
        mWeekView.setOnEventClickListener(this);

        setupDateTimeInterpreter(true);
    }

    /**
     * Set up a date time interpreter which will show short date values when in week view and long
     * date values otherwise.
     * @param shortDate True if the date values should be short.
     */
    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase(); // + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour, int minutes) {
                String strMinutes = String.format("%02d", minutes);
                if (hour > 11) {
                    if (hour == 12) {
                        return "12:00PM";
                    }
                    return (hour - 12) + ":" + strMinutes + " PM";
                } else {
                    if (hour == 0) {
                        return "12:" + strMinutes + " AM";
                    } else {
                        return hour + ":" + strMinutes + " AM";
                    }
                }
            }
        });
    }

    @Override
    public void onFirstVisibleDayChanged(Calendar newFirstVisibleDay, Calendar oldFirstVisibleDay) {
        // Essentially disable horizontal scrolling
        if (mWeekViewLoaded) {
            scrollWeekViewToDay();
        }
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        System.out.println(event.getStartTime().get(Calendar.HOUR));
    }

    private boolean eventMatches(WeekViewEvent event, int year, int month) {
        return (event.getStartTime().get(Calendar.YEAR) == year && event.getStartTime().get(Calendar.MONTH) == month - 1) || (event.getEndTime().get(Calendar.YEAR) == year && event.getEndTime().get(Calendar.MONTH) == month - 1);
    }

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        // Provide the events for the WeekView

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        ArrayList<ArrayList<ArrayList<Calendar>>> eventSets = (ArrayList<ArrayList<ArrayList<Calendar>>>) bundle.getSerializable(States.eventSets);

        for (ArrayList<ArrayList<Calendar>> eventSet : eventSets) {
            List<WeekViewEvent> events = new ArrayList<>();

            for (ArrayList<Calendar> rawEvent : eventSet) {
                WeekViewEvent event = new WeekViewEvent(1, generateCourseName("COMP\n0000A", rawEvent.get(0), rawEvent.get(1)), rawEvent.get(0), rawEvent.get(1));
                if (eventMatches(event, newYear, newMonth)) {
                    events.add(event);
                }
            }

            if (!events.isEmpty()) {
                try {
                    availableCourseSets.set(eventSets.indexOf(eventSet), events);
                } catch (IndexOutOfBoundsException e) {
                    availableCourseSets.add(eventSets.indexOf(eventSet), events);
                }
            }
        }

        if (activeCourseSet == null) {
            // This will only occur the first time this Activity loads
            activeCourseSet = availableCourseSets.get(0);
        }

        if (eventMatches(activeCourseSet.get(0), newYear, newMonth)) {
            scrollWeekViewToHour();
            if (!mWeekViewLoaded) {
                System.out.println(true);
                scrollWeekViewToDay();
                mWeekViewLoaded = true;
            }
            return activeCourseSet;
        }
        return new ArrayList<>();
    }
}
