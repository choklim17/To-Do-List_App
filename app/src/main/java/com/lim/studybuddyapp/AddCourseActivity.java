package com.lim.studybuddyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCourseActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtCourseCode, edtCourseName, edtCourseSchedule;
    private Button btnAddCourse, btnCancel;
    private Course course;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        // Initialize views and set click listeners
        initViews();

        // Set click listeners for buttons
        btnAddCourse.setOnClickListener(AddCourseActivity.this);
        btnCancel.setOnClickListener(AddCourseActivity.this);
    }

    // Method to initialize views
    private void initViews() {
        edtCourseCode = findViewById(R.id.addCourse_edt_code);
        edtCourseName = findViewById(R.id.addCourse_edt_name);
        edtCourseSchedule = findViewById(R.id.addCourse_edt_schedule);

        btnAddCourse = findViewById(R.id.addCourse_btn_add);
        btnCancel = findViewById(R.id.addCourse_btn_cancel);
    }

    // Method to handle click events
    @Override
    public void onClick(View v) {
        // Add course button clicked
        if (v.getId() == R.id.addCourse_btn_add) {
            // Call method to add the course to the database
            addCourse();

            // Clear input fields after adding the course
            edtCourseCode.setText("");
            edtCourseName.setText("");
            edtCourseSchedule.setText("");
        }

        // Cancel button clicked
        if (v.getId() == R.id.addCourse_btn_cancel) {
            // Open MainActivity and finish this activity
            ActivityHelper.openNewActivity(AddCourseActivity.this, MainActivity.class);
        }
    }

    // Method to add a new course to the database
    private void addCourse() {
        // Get course details from EditText fields
        String courseCode = edtCourseCode.getText().toString().trim();
        String courseName = edtCourseName.getText().toString().trim();
        String courseSchedule = edtCourseSchedule.getText().toString().trim();

        // Check if any field is empty
        if (courseCode.isEmpty() || courseName.isEmpty() || courseSchedule.isEmpty()) {
            Toast.makeText(AddCourseActivity.this, "Please fill empty fields", Toast.LENGTH_SHORT).show();
        }
        else {
            // Create a new Course object with the entered details
            course = new Course(courseCode, courseName, courseSchedule);

            // Initialize DatabaseHelper and add the course
            databaseHelper = new DatabaseHelper(AddCourseActivity.this);
            databaseHelper.addCourse(course);
        }
    }

    // Method called when the back button is pressed
    public void onBackPressed() {
        // Open MainActivity and finish this activity
        ActivityHelper.openNewActivity(AddCourseActivity.this, MainActivity.class);
    }
}