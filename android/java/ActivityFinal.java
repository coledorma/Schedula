package com.example.coop.schedulaui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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

import SchedulaAlgo.Schedule;
import SchedulaAlgo.Section;
import SchedulaAlgo.TimeSlot;

/**
 * Created by coop on 2016-10-20.
 */

/*
 * TODO
 * Implement selectButton
 */

public class ActivityFinal extends AppCompatActivity implements MonthLoader.MonthChangeListener, WeekView.ScrollListener {

    /*
     * UI Elements
     */
    private WeekView mWeekView;
    private Button selectButton;

    /*
     * Data Elements
     */
    private List<WeekViewEvent> activeCourseSet;
    private ArrayList<List<WeekViewEvent>> availableCourseSets;

    private Boolean mWeekViewLoaded;
    private Boolean mWeekViewSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        mWeekViewLoaded = false;
        mWeekViewSet = false;

        final LinearLayout scheduleSelector = (LinearLayout) findViewById(R.id.schedule_selector);
        scheduleSelector.removeAllViews();

        availableCourseSets = new ArrayList<>();
        activeCourseSet = null;

        for (int i = 0; i < States.SCHEDULES.size(); i++) {
            /*
             * Create a button for each schedule generated
             * Each button is paired with a schedule data set
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

            if (i == 0) {
                aButton.setAlpha(0.3f);
                aButton.setClickable(false);
            }

            scheduleSelector.addView(aButton);
        }

        selectButton = (Button) findViewById(R.id.select_button);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mWeekView = (WeekView) findViewById(R.id.weekView);
        mWeekView.setMonthChangeListener(this);
        mWeekView.setScrollListener(this);

        setupDateTimeInterpreter(true);
    }

    /*
     * For the WeekView...
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

    /*
     * Main method for WeekView
     * Populate the WeekView with events for current month, previous month, and next month
     */
    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        for (Schedule schedule : States.SCHEDULES) {
            List<WeekViewEvent> events = new ArrayList<>();

            for (Section section : schedule.getSections()) {
                for (TimeSlot timeSlot : section.getTimes()) {
                    if (timeSlot == null) continue;

                    Calendar start = Calendar.getInstance();
                    start.set(Calendar.HOUR_OF_DAY, timeSlot.getStart().get(Calendar.HOUR_OF_DAY));
                    start.set(Calendar.MINUTE, timeSlot.getStart().get(Calendar.MINUTE));
                    start.set(Calendar.DAY_OF_WEEK, timeSlot.getStart().get(Calendar.DAY_OF_WEEK));

                    Calendar end = Calendar.getInstance();
                    end.set(Calendar.HOUR_OF_DAY, timeSlot.getEnd().get(Calendar.HOUR_OF_DAY));
                    end.set(Calendar.MINUTE, timeSlot.getEnd().get(Calendar.MINUTE));
                    end.set(Calendar.DAY_OF_WEEK, timeSlot.getEnd().get(Calendar.DAY_OF_WEEK));

                    WeekViewEvent event = new WeekViewEvent(1, generateCourseName(section.getID(), start, end), start, end);

                    if (eventMatches(event, newYear, newMonth)) {
                        events.add(event);
                    }
                }
            }

            if (!events.isEmpty()) {
                try {
                    availableCourseSets.set(States.SCHEDULES.indexOf(schedule), events);
                } catch (IndexOutOfBoundsException e) {
                    availableCourseSets.add(States.SCHEDULES.indexOf(schedule), events);
                }
            }
        }

        if (activeCourseSet == null && availableCourseSets.size() != 0) {
            // This will only occur the first time this Activity loads
            activeCourseSet = availableCourseSets.get(0);
        }

        if (availableCourseSets.size() == 0) {
            return new ArrayList<>();
        }

        if (eventMatches(activeCourseSet.get(0), newYear, newMonth)) {
            scrollWeekViewToHour();
            if (!mWeekViewLoaded) {
                scrollWeekViewToDay();
                mWeekViewLoaded = true;
            }
            return activeCourseSet;
        }

        return new ArrayList<>();
    }

    /*
     * Horizontal scroll listener for WeekView
     */
    @Override
    public void onFirstVisibleDayChanged(Calendar newFirstVisibleDay, Calendar oldFirstVisibleDay) {
        // Essentially disable horizontal scrolling
        if (mWeekViewLoaded) {
            scrollWeekViewToDay();
        }
    }


    /*
     * WeekView helper method
     * Check if an event is part of the current month that is being loaded in onMonthChange
     */
    private boolean eventMatches(WeekViewEvent event, int year, int month) {
        return (event.getStartTime().get(Calendar.YEAR) == year && event.getStartTime().get(Calendar.MONTH) == month - 1) ||
                (event.getEndTime().get(Calendar.YEAR) == year && event.getEndTime().get(Calendar.MONTH) == month - 1);
    }

    /*
     * Helper method
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
     * Helper method
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
     * Helper method
     * Return a calendar event title with course code, section and time
     */
    public String generateCourseName(String code, Calendar startTime, Calendar endTime) {
        String startHour = (startTime.get(Calendar.HOUR_OF_DAY) > 9) ? Integer.toString(startTime.get(Calendar.HOUR_OF_DAY)) : ("0" + startTime.get(Calendar.HOUR_OF_DAY));
        String startMin = (startTime.get(Calendar.MINUTE) > 9) ? Integer.toString(startTime.get(Calendar.MINUTE)) : ("0" + startTime.get(Calendar.MINUTE));
        String endHour = (endTime.get(Calendar.HOUR_OF_DAY) > 9) ? Integer.toString(endTime.get(Calendar.HOUR_OF_DAY)) : ("0" + endTime.get(Calendar.HOUR_OF_DAY));
        String endMin = (endTime.get(Calendar.MINUTE) > 9) ? Integer.toString(endTime.get(Calendar.MINUTE)) : ("0" + endTime.get(Calendar.MINUTE));

        return code + "\n\n" + startHour + ":" + startMin + "\n" + endHour + ":" + endMin;
    }
}
