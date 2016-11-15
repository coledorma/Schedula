package com.example.coop.schedulaui;

import android.content.Intent;
import android.content.res.Configuration;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import SchedulaAlgo.Course;

public class ActivitySelect extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Course Selector");
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tabLayout.getSelectedTabPosition() == 0) {
                    States.term = "201630";
                } else if (tabLayout.getSelectedTabPosition() == 1) {
                    States.term = "201710";
                }

                Intent intent = new Intent(ActivitySelect.this, ActivityPreferences.class);

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_select, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /*
         * UI elements
         */
        private ClearableAutoCompleteTextView course_selector;

        /*
         * Data elements
         */
        private ArrayList<Course> selectedCourses;
        private ArrayList<Course> availableCourses;
        private ArrayAdapter<Course> selectorAdapter;
        private ArrayAdapter<Course> listAdapter;
        private String term;

        /*
         * Unable to re-initialize the ArrayAdapter of an object within it's own OnItemClickListener
         * Therefore, do it outside the OnItemClickListener
         */
        private void updateSelector() {
            selectorAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_dropdown_item_1line, availableCourses);
            course_selector.setAdapter(selectorAdapter);
        }

        /*
         * Called before onCreate of the new Configuration.
         */
        @Override
        public void onConfigurationChanged(Configuration newConfig) {
            super.onConfigurationChanged(newConfig);

            if (term.equals("201710")) {
                States.coursesWint = availableCourses;
                States.SELECTED_COURSES_WINT = selectedCourses;
            } else {
                States.coursesFall = availableCourses;
                States.SELECTED_COURSES_FALL = selectedCourses;
            }
            States.IS_SET = true;
        }

        public PlaceholderFragment() {

        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);

            if (sectionNumber == 1) {
                fragment.availableCourses = States.coursesFall;
                fragment.selectedCourses = States.SELECTED_COURSES_FALL;
                fragment.term = "201630";
            } else if (sectionNumber == 2) {
                fragment.availableCourses = States.coursesWint;
                fragment.selectedCourses = States.SELECTED_COURSES_WINT;
                fragment.term = "201710";
            }

            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_activity_select, container, false);

            selectedCourses = new ArrayList<>();

            /*
             * Load already-created resources if present
            */
            if (States.IS_SET) {
                if (term.equals("201710")) {
                    selectedCourses = States.SELECTED_COURSES_WINT;
                    availableCourses = States.coursesWint;
                } else {
                    selectedCourses = States.SELECTED_COURSES_FALL;
                    availableCourses = States.coursesFall;
                }
            }

            /*
             * Setup adapters from resources
             */
            selectorAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_dropdown_item_1line, availableCourses);
            listAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, selectedCourses);

            /*
             * Init the search object
             */
            course_selector = (ClearableAutoCompleteTextView) rootView.findViewById(R.id.course_selector);
            course_selector.setAdapter(selectorAdapter);
            course_selector.setHint(R.string.course_selector_hint);
            course_selector.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                    Course selection = (Course) parent.getItemAtPosition(pos);

                    if (selectedCourses.size() >= 6) {
                        Toast.makeText(getContext(), "Max number of courses chosen (6)\nClick to delete one", Toast.LENGTH_LONG).show();
                    } else {
                        if (selectedCourses.contains(selection)) {
                            selectedCourses.remove(selection);
                            availableCourses.add(selection);
                        } else {
                            selectedCourses.add(selection);
                            availableCourses.remove(selection);
                        }

                        if (term.equals("201710")) {
                            States.coursesWint = availableCourses;
                            States.SELECTED_COURSES_WINT = selectedCourses;
                        } else {
                            States.coursesFall = availableCourses;
                            States.SELECTED_COURSES_FALL = selectedCourses;
                        }

                        States.IS_SET = true;

                        course_selector.setText("");
                        listAdapter.notifyDataSetChanged();
                        updateSelector();
                    }
                }
            });

            /*
             * Init the chosen courses object
             */
            ListView courseList = (ListView) rootView.findViewById(R.id.course_list);
            courseList.setAdapter(listAdapter);
            courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    availableCourses.add(selectedCourses.get(position));
                    selectedCourses.remove(position);
                    listAdapter.notifyDataSetChanged();
                    updateSelector();

                    if (term.equals("201710")) {
                        States.coursesWint = availableCourses;
                        States.SELECTED_COURSES_WINT = selectedCourses;
                    } else {
                        States.coursesFall = availableCourses;
                        States.SELECTED_COURSES_FALL = selectedCourses;
                    }
                }
            });

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if (position == 0) {
                return "Fall Semester";
            } else if (position == 1) {
                return "Winter Semster";
            }

            return null;
        }
    }
}
