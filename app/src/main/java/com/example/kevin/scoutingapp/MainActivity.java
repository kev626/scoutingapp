package com.example.kevin.scoutingapp;

import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            if (getArguments().getInt(ARG_SECTION_NUMBER) == 1) {
                final View rootView = inflater.inflate(R.layout.fragment_add, container, false);
                Button button = (Button) rootView.findViewById(R.id.submit);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String params = "";
                        params += "?number=" + ((EditText) rootView.findViewById(R.id.number)).getText();
                        params += "&teamName=" + ((EditText) rootView.findViewById(R.id.name)).getText();
                        params += "&autobeacons=" + ((EditText) rootView.findViewById(R.id.autobeacons)).getText();
                        params += "&autoparticlefloor=" + ((EditText) rootView.findViewById(R.id.autoparticlesfloor)).getText();
                        params += "&autoparticlegoal=" + ((EditText) rootView.findViewById(R.id.autoparticlesgoal)).getText();
                        params += "&teleparticlesgoal=" + ((EditText) rootView.findViewById(R.id.teleparticlegoal)).getText();
                        params += "&teleparticlesfloor=" + ((EditText) rootView.findViewById(R.id.teleparticlefloor)).getText();
                        params += "&telebeacons=" + ((EditText) rootView.findViewById(R.id.telebeacons)).getText();

                        boolean autopark1 = ((RadioButton) rootView.findViewById(R.id.autopark1)).isChecked();
                        boolean autopark2 = ((RadioButton) rootView.findViewById(R.id.autopark2)).isChecked();
                        boolean autopark3 = ((RadioButton) rootView.findViewById(R.id.autopark3)).isChecked();
                        boolean autopark4 = ((RadioButton) rootView.findViewById(R.id.autopark4)).isChecked();
                        boolean autopark5 = ((RadioButton) rootView.findViewById(R.id.autopark5)).isChecked();

                        int autopark = 0;
                        if (autopark1) {
                            autopark = 0;
                        } else if (autopark2) {
                            autopark = 5;
                        } else if (autopark3) {
                            autopark = 10;
                        } else if (autopark4) {
                            autopark = 5;
                        } else if (autopark5) {
                            autopark = 10;
                        }

                        params += "&autopark=" + autopark;


                        params += "&autocap=" + (((CheckBox) rootView.findViewById(R.id.autocap)).isChecked() ? 5 : 0);

                        boolean teleball1 = ((RadioButton) rootView.findViewById(R.id.teleball1)).isChecked();
                        boolean teleball2 = ((RadioButton) rootView.findViewById(R.id.teleball2)).isChecked();
                        boolean teleball3 = ((RadioButton) rootView.findViewById(R.id.teleball3)).isChecked();
                        boolean teleball4 = ((RadioButton) rootView.findViewById(R.id.teleball4)).isChecked();

                        int teleball = 0;
                        if (teleball1) {
                            teleball = 0;
                        }
                        if (teleball2) {
                            teleball = 10;
                        }
                        if (teleball3) {
                            teleball = 20;
                        }
                        if (teleball4) {
                            teleball = 40;
                        }

                        params += "&teleball=" + teleball;


                        Thread t = new Thread(new SubmitThread(params));
                        t.start();
                    }
                });
                return rootView;
            }
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
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
            // Show 3 total pages.
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Add";
                case 1:
                    return "View";
                case 2:
                    return "Matches";
                case 3:
                    return "+ Match";
                case 4:
                    return "Predict";
            }
            return null;
        }
    }
}
