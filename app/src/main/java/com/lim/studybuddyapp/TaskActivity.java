package com.lim.studybuddyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends AppCompatActivity {
    private TextView txtCourseCode, txtCoursName, txtCourseSchedule;
    private RecyclerView recyclerView;
    private FloatingActionButton btnAddTask;
    private List<Task> tasks;
    private TaskAdapter taskAdapter;
    private DatabaseHelper databaseHelper;
    private String courseCode, courseName, courseSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        initViews();
        setCourseDetailsFromIntent(); // Set course details from the intent
        loadTasks(); // Load tasks for the course from the database
        setupRecyclerView();

        // Set click listener for FloatingActionButton to open AddTaskActivity
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start AddTaskActivity and pass course details
                Intent intent = new Intent(TaskActivity.this, AddTaskActivity.class);
                intent.putExtra("courseCode", courseCode);
                intent.putExtra("courseName", courseName);
                intent.putExtra("courseSchedule", courseSchedule);

                startActivity(intent);
                finish();
            }
        });
    }

    // Method to initialize views
    private void initViews() {
        txtCourseCode = findViewById(R.id.task_txt_courseCode);
        txtCoursName = findViewById(R.id.task_txt_courseName);
        txtCourseSchedule = findViewById(R.id.task_txt_courseSchedule);

        recyclerView = findViewById(R.id.recyclerView_tasks);
        btnAddTask = findViewById(R.id.task_btn_addTask);
    }

    // Method to load tasks from the database for the current course
    private void loadTasks() {
        databaseHelper = new DatabaseHelper(TaskActivity.this);

        // Retrieve tasks from the database for the current course
        tasks = databaseHelper.getTasks(courseCode);
    }

    // Method to retrieve course details from the intent and set them to TextViews
    private void setCourseDetailsFromIntent() {
        // Retrieve data from the intent
        courseCode = getIntent().getStringExtra("code");
        courseName = getIntent().getStringExtra("name");
        courseSchedule = getIntent().getStringExtra("schedule");

        // Set the retrieved data to the TextViews
        txtCourseCode.setText(courseCode);
        txtCoursName.setText(courseName);
        txtCourseSchedule.setText(courseSchedule);
    }

    // Method to set up RecyclerView to display tasks
    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(TaskActivity.this));
        taskAdapter = new TaskAdapter(tasks, TaskActivity.this);
        recyclerView.setAdapter(taskAdapter);
    }

    public void onBackPressed() {
        ActivityHelper.openNewActivity(TaskActivity.this, MainActivity.class);
    }
}