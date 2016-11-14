package com.example.coop.schedulaui;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.util.ArrayList;

import SchedulaAlgo.Course;
import SchedulaAlgo.Section;
import SchedulaAlgo.SubSection;

/**
 * Created by coop on 2016-10-14.
 */

public class TaskXMLParser extends AsyncTask<Object, Void, Void> {

    private  final String TAG = this.getClass().getSimpleName() + " @" + System.identityHashCode(this);

    static final String rowTag = "<Row";
    static final String rowEnd = "</Row>";

    static final String cellTag = "<Cell>";
    static final String cellEnd = "</Cell>";
    static final String cellEmp = "<Cell  />";

    static final String dataTag = "<Data>";
    static final String dataEnd = "</Data>";

    @Override
    protected Void doInBackground(Object... params) {

        /*
         * Parse XML contained in buffer
         */
        BufferedReader reader;
        try {

            reader = (BufferedReader) params[0];
            String line;
            int lineCount = 0;
            int dead = 0;

            // sectionData - placeholder store of data for each section
            ArrayList<String> sectionData = new ArrayList<>();

            long startTime = System.nanoTime();

            try {
                while ((line = reader.readLine()) != null) {

                    lineCount++;

                    if (line.contains(rowTag)) {
                        /*
                         * Beginning of new section.
                         * Check if course exists.
                         */
                        sectionData.clear();
                        while (!line.contains(rowEnd)) {
                            /*
                             * Generate new section.
                             * Extract data from line, or act appropriately for null-value lines.
                             * End of this while loop will kick into creating a new Section/SubSection object.
                             */

                            if (line.contains(cellTag)) {
                                line = line.replace(cellTag, "");
                                line = line.replace(dataTag, "");
                                line = line.replace(dataEnd, "");
                                line = line.replace(cellEnd, "");
                                line = line.trim();
                                sectionData.add(line);
                            } else if (line.contains(cellEmp)) {
                                sectionData.add("");
                            }
                            line = reader.readLine();
                            lineCount++;
                        }
                        /*
                         * Make a new Section on SubSection based on sectionData.
                         */
                        if (sectionData.get(4).length() > 1) {
                            SubSection subSection = new SubSection(sectionData.get(2) + "\n" + sectionData.get(3) + sectionData.get(4), sectionData.get(5), Integer.parseInt(sectionData.get(1)), Integer.parseInt(sectionData.get(0)), sectionData.get(7));
                            /*
                             * Based on the structure of the DB (how elements are ordered),
                             * SubSections should already have their parent Section/Course created.
                             * Check anyway.
                             * Create the SubSection and add it to it's parent Section.
                             */
                            if (sectionData.get(0).equals("201710")) {
                                if (States.coursesWint.indexOf(new Course(sectionData.get(2) + sectionData.get(3), null)) != -1) {
                                    Course course = States.coursesWint.get(States.coursesWint.indexOf(new Course(sectionData.get(2) + sectionData.get(3), null)));
                                    for (Section section : course.sections) {
                                        if (section.getID().equals(sectionData.get(4).substring(0, 2))) {
                                            section.addSubSection(subSection);
                                            break;
                                        }
                                    }
                                } else {
                                    // deal with the parents not being created
                                    dead++;
                                }
                            } else {
                                if (States.coursesFall.indexOf(new Course(sectionData.get(2) + sectionData.get(3), null)) != -1) {
                                    Course course = States.coursesFall.get(States.coursesFall.indexOf(new Course(sectionData.get(2) + sectionData.get(3), null)));

                                    for (Section section : course.sections) {
                                        if (section.getID().substring(9).equals(sectionData.get(4).substring(0, 1))) {
                                            section.addSubSection(subSection);
                                            break;
                                        }
                                    }
                                } else {
                                    // deal with the parents not being created
                                    dead++;
                                }
                            }

                        } else {
                            Section section = new Section(sectionData.get(2) + "\n" + sectionData.get(3) + sectionData.get(4), sectionData.get(6), Integer.parseInt(sectionData.get(1)), Integer.parseInt(sectionData.get(0)), sectionData.get(7), new ArrayList<SubSection>());

                            /*
                             * Verify if a Course exists for this Section, do as necessary.
                             * Add the Section to it's parent Course.
                             */
                            if (sectionData.get(0).equals("201710")) {
                                if (States.coursesWint.contains(new Course(sectionData.get(2) + sectionData.get(3), null))) {
                                    Course course = States.coursesWint.get(States.coursesWint.indexOf(new Course(sectionData.get(2) + sectionData.get(3), null)));
                                    course.sections.add(section);
                                } else {
                                    Course course = new Course(sectionData.get(2) + sectionData.get(3), new ArrayList<Section>());
                                    course.sections.add(section);
                                    States.coursesWint.add(course);
                                }
                            } else {
                                if (States.coursesFall.contains(new Course(sectionData.get(2) + sectionData.get(3), null))) {
                                    Course course = States.coursesFall.get(States.coursesFall.indexOf(new Course(sectionData.get(2) + sectionData.get(3), null)));
                                    course.sections.add(section);
                                } else {
                                    Course course = new Course(sectionData.get(2) + sectionData.get(3), new ArrayList<Section>());
                                    course.sections.add(section);
                                    States.coursesFall.add(course);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, "Error from line: " + lineCount);
                e.printStackTrace();
            }
            long endTime = System.nanoTime();
            Log.i(TAG, "TaskXMLParser DONE" +
                    "\n    lines parsed:             " + lineCount +
                    "\n    Courses created:          " + (States.coursesFall.size() + States.coursesWint.size()) +
                    "\n    Courses discarded:        " + dead +
                    "\n    time taken, seconds:      " + ((endTime - startTime) / 1000000000.0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onProgressUpdate(Integer... progress) {

    }

    protected void onPostExecute(Long result) {

    }
}
