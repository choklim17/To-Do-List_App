package com.lim.studybuddyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton btnAdd;
    private CourseAdapter courseAdapter;
    private List<Course> courses;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews(); // Initialize views
        loadCourses(); // Load courses from the database
        setupRecyclerView(); // Set up RecyclerView to display courses

        // Set click listener for FloatingActionButton to open AddCourseActivity
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityHelper.openNewActivity(MainActivity.this, AddCourseActivity.class);
            }
        });
    }

    // Method to initialize views
    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView_courses);
        btnAdd = findViewById(R.id.button_addCourse);
    }

    // Method to load courses from the database
    private void loadCourses() {
        databaseHelper = new DatabaseHelper(MainActivity.this);
        courses = databaseHelper.getCourses();
    }

    // Method to set up RecyclerView to display courses
    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        courseAdapter = new CourseAdapter(courses, MainActivity.this);
        recyclerView.setAdapter(courseAdapter);
    }

    // Method called when the back button is pressed
    public void onBackPressed() {
        finish();
    }
}