package com.example.coop.schedulaui;

import android.os.AsyncTask;

import java.util.ArrayList;

import SchedulaAlgo.Commitment;
import SchedulaAlgo.Schedule;

/**
 * Created by coop on 2016-10-14.
 */

public class TaskScheduleGenerator extends AsyncTask<Object, Void, Void> {

    private Schedule schedule;

    @Override
    protected Void doInBackground(Object[] params) {

        States.SCHEDULE = new Schedule(new ArrayList<Commitment>(), States.SELECTED_COURSES);

        return null;
    }

    /*
     * use publishProgress() to call
     * https://developer.android.com/reference/android/os/AsyncTask.html#publishProgress(Progress...)
     */
    protected void onProgressUpdate(Integer... progress) {

    }

    protected void onPostExecute(Long result) {

    }
}
