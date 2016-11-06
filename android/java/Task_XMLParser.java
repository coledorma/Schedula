package com.example.coop.schedula;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import SchedulaAlgo.Course;
import SchedulaAlgo.Section;
import SchedulaAlgo.SubSection;

/**
 * Created by coop on 2016-10-14.
 */

public class XMLParserTask extends AsyncTask<BufferedReader, Void, Void> {

    static final String workbookTag = "<Workbook>";
    static final String workbookEnd = "</Workbook>";

    static final String worksheetTag = "<Worksheet>";
    static final String worksheetEnd = "</Worksheet>";

    static final String ssTag = "<ss>";
    static final String ssEnd = "</ss>";

    static final String rowTag = "<Row";
    static final String rowEnd = "</Row>";

    static final String cellTag = "<Cell>";
    static final String cellEnd = "</Cell>";
    static final String cellEmp = "<Cell  />";

    static final String dataTag = "<Data>";
    static final String dataEnd = "</Data>";

    @Override
    protected Void doInBackground(BufferedReader... bufferedReaders) {

        /*
         * Parse XML contained in buffer
         */
        BufferedReader reader;
        try {
            reader = bufferedReaders[0];

            String line;

            // Array of Courses
            // Placeholder Section

            // courseData - store the data for each section
            ArrayList<String> sectionData = new ArrayList<>();

            int lineCount = 0;

            try {
                while ((line = reader.readLine()) != null) {

                    lineCount++;

                    if (line.contains(rowTag)) {
                        /*
                         * New section
                         * Check if course exists
                         *
                         */
                        sectionData.clear();
                        while (!line.contains(rowEnd)) {
                            // generate new section

                            if (line.contains(cellTag)) {
                                // extract data, add to courseData
                                line = line.replace(cellTag, "");
                                line = line.replace(dataTag, "");
                                line = line.replace(dataEnd, "");
                                line = line.replace(cellEnd, "");
                                line = line.trim();
                                sectionData.add(line);
                            } else if (line.contains(cellEmp)) {
                                // this data section doesn't have a value
                                // add blank value to courseData to keep consistency
                                sectionData.add("");
                            }
                            line = reader.readLine();
                            lineCount++;
                            // end of this loop will kick into the next section
                        }
                        // make new section based on courseData
                        // add it to an existing course or make a new one
                        if (sectionData.get(4).length() > 1) {
                            // subsection
                            SubSection subSection = new SubSection(sectionData.get(4), Integer.parseInt(sectionData.get(1)), Integer.parseInt(sectionData.get(0)), sectionData.get(7));

                            // A course and section should have already been made for this subsection
                            Course course = States.courses.get(States.courses.indexOf(new Course(sectionData.get(2) + sectionData.get(3), null)));
                            for (Section section : course.sections) {
                                // check if section exists
                                if (section.getID().equals(sectionData.get(4).substring(0, 2))) {
                                    section.addSubSection(subSection);
                                    break;
                                }
                            }
                        } else {
                            Section section = new Section(sectionData.get(4), sectionData.get(6), Integer.parseInt(sectionData.get(1)), Integer.parseInt(sectionData.get(0)), sectionData.get(7), null);
                            // create a new course
                            // or add to an existing

                            if (States.courses.contains(new Course(sectionData.get(2) + sectionData.get(3), null))) {
                                Course course = States.courses.get(States.courses.indexOf(new Course(sectionData.get(2) + sectionData.get(3), null)));
                                course.sections.add(section);
                            } else {
                                Course course = new Course(sectionData.get(2) + sectionData.get(3), new ArrayList<Section>());
                                course.sections.add(section);
                                States.courses.add(course);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                System.out.println(lineCount);
                e.printStackTrace();
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onProgressUpdate(Integer... progress) {
        
    }

    protected void onPostExecute(Long result) {

    }
}
