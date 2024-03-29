package com.example.notekeeper;

import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CourseEventsMainActivity extends AppCompatActivity
        implements CourseEventsDisplayCallbacks {

    private static ArrayList<String> mCourseEvents;
    private static ArrayAdapter<String> mCourseEventsAdapter;
    private static CourseEventsReceiver mCourseEventsReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_course_events_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        mCourseEvents = new ArrayList<String>();
        mCourseEventsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mCourseEvents);
        final ListView listView = (ListView) findViewById(R.id.list_course_events);
        listView.setAdapter(mCourseEventsAdapter);

        setupCourseEventReceiver();
    }


    private void setupCourseEventReceiver(){
        mCourseEventsReceiver = new CourseEventsReceiver();
        mCourseEventsReceiver.setCourseEventsDisplayCallbacks(this);

        IntentFilter intentFilter = new IntentFilter(CourseEventsReceiver.ACTION_COURSE_EVENT);

        registerReceiver(mCourseEventsReceiver, intentFilter);
    }

    @Override
    protected void onDestroy(){
        unregisterReceiver(mCourseEventsReceiver);
        super.onDestroy();
    }
    @Override
    public void onEventReceived(String courseId, String courseMessage) {
        String displayText = courseId + ": " + courseMessage;
        mCourseEvents.add(displayText);
        mCourseEventsAdapter.notifyDataSetChanged();

    }
}
