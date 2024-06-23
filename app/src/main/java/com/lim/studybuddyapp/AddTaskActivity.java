package com.lim.studybuddyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTaskActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtTaskTitle, edtTaskDescription, edtTaskDeadline;
    private Button btnAdd, btnBack;
    private Task task;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        initViews();

        btnAdd.setOnClickListener(AddTaskActivity.this);
        btnBack.setOnClickListener(AddTaskActivity.this);
    }

    private void initViews() {
        edtTaskTitle = findViewById(R.id.addTask_edt_title);
        edtTaskDescription = findViewById(R.id.addTask_edt_description);
        edtTaskDeadline = findViewById(R.id.addTask_edt_deadline);

        btnAdd = findViewById(R.id.addTask_btn_add);
        btnBack = findViewById(R.id.addTask_btn_back);
    }

    // Method to add a task to the course
    private void addTaskToCourse() {
        // Get the course code from the intent
        String courseCode = getIntent().getStringExtra("courseCode");

        // Get task details from EditText fields
        String taskTitle = edtTaskTitle.getText().toString().trim();
        String taskDescription = edtTaskDescription.getText().toString().trim();
        String taskDeadline = edtTaskDeadline.getText().toString().trim();

        if (taskTitle.isEmpty() || taskDescription.isEmpty() || taskDeadline.isEmpty()) {
            Toast.makeText(AddTaskActivity.this, "Please fill empty fields", Toast.LENGTH_SHORT).show();
        }
        else {
            // Create a new Task object with the entered details
            task = new Task(0, courseCode, taskTitle, taskDescription, taskDeadline);

            // Initialize DatabaseHelper and add the task to the course
            databaseHelper = new DatabaseHelper(AddTaskActivity.this);
            databaseHelper.addTaskToCourse(task);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addTask_btn_add) {
            addTaskToCourse();

            edtTaskTitle.setText("");
            edtTaskDescription.setText("");
            edtTaskDeadline.setText("");
        }

        if (v.getId() == R.id.addTask_btn_back) {
            ActivityHelper.openNewActivity(AddTaskActivity.this, MainActivity.class);
        }
    }

    public void onBackPressed() {
        ActivityHelper.openNewActivity(AddTaskActivity.this, MainActivity.class);
    }
}