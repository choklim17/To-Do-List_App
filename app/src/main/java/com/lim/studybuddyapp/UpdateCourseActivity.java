package com.lim.studybuddyapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateCourseActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtCourseCode, edtCourseName, edtCourseSchedule;
    private Button btnUpdate, btnCancel, btnDelete;
    private String courseCode, courseName, courseSchedule;
    private DatabaseHelper databaseHelper;
    private Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_course);

        initViews();
        getAndSetIntentData();

        btnUpdate.setOnClickListener(UpdateCourseActivity.this);
        btnCancel.setOnClickListener(UpdateCourseActivity.this);
        btnDelete.setOnClickListener(UpdateCourseActivity.this);
    }

    // Method to initialize views
    private void initViews() {
        edtCourseCode = findViewById(R.id.updateCourse_edt_code);
        edtCourseName = findViewById(R.id.updateCourse_edt_name);
        edtCourseSchedule = findViewById(R.id.updateCourse_edt_schedule);

        btnUpdate = findViewById(R.id.updateCourse_btn_update);
        btnCancel = findViewById(R.id.updateCourse_btn_cancel);
        btnDelete = findViewById(R.id.updateCourse_btn_delete);
    }

    // Method to get course details from the intent and set them to EditText fields
    private void getAndSetIntentData() {
        courseCode = getIntent().getStringExtra("courseCode");
        courseName = getIntent().getStringExtra("courseName");
        courseSchedule = getIntent().getStringExtra("courseSchedule");

        edtCourseCode.setText(courseCode);
        edtCourseName.setText(courseName);
        edtCourseSchedule.setText(courseSchedule);
    }

    // Method to handle button clicks
    @Override
    public void onClick(View v) {
        // If update button is clicked
        if (v.getId() == R.id.updateCourse_btn_update) {
            // Update the course details
            updateCourse();
            ActivityHelper.openNewActivity(UpdateCourseActivity.this, MainActivity.class);
        }

        if (v.getId() == R.id.updateCourse_btn_cancel) {
            ActivityHelper.openNewActivity(UpdateCourseActivity.this, MainActivity.class);
        }

        // If delete button is clicked
        if (v.getId() == R.id.updateCourse_btn_delete) {
            // Show confirmation dialog for course deletion
            showConfirmDialog();
        }
    }

    // Method to update course details
    private void updateCourse() {
        String updatedCourseSchedule = edtCourseSchedule.getText().toString().trim();

        if (courseCode.isEmpty() || courseName.isEmpty() || updatedCourseSchedule.isEmpty()) {
            Toast.makeText(UpdateCourseActivity.this, "Please fill empty fields", Toast.LENGTH_SHORT).show();
        }
        else {
            course = new Course(courseCode, courseName, updatedCourseSchedule);

            databaseHelper = new DatabaseHelper(UpdateCourseActivity.this);
            databaseHelper.updateCourse(course); // Update the course in the database
        }
    }

    // Method to delete the course
    private void deleteCourse(Course course) {
        databaseHelper = new DatabaseHelper(UpdateCourseActivity.this);
        databaseHelper.deleteCourse(course); // Delete the course from the database
    }

    // Method to show confirmation dialog for course deletion
    private void showConfirmDialog() {
        course = new Course(courseCode, courseName, courseSchedule);

        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateCourseActivity.this);
        builder.setTitle("Delete Course");
        builder.setMessage("Are you sure you want to delete " + course.getCourseCode() + "?");

        // Set positive button to confirm deletion
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteCourse(course); // Delete the course
                ActivityHelper.openNewActivity(UpdateCourseActivity.this, MainActivity.class);
            }
        });

        // Set negative button to cancel deletion
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(UpdateCourseActivity.this, "Deletion Cancelled", Toast.LENGTH_SHORT).show();
            }
        });

        builder.create().show();
    }

    public void onBackPressed() {
        ActivityHelper.openNewActivity(UpdateCourseActivity.this, MainActivity.class);
    }
}