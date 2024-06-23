package com.lim.studybuddyapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateTaskActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtTaskTitle, edtTaskDescription, edtTaskDeadline;
    private Button btnUpdate, btnDelete, btnBack;
    private int taskID;
    private String taskCourseCode, taskTitle, taskDescription, taskDeadline;
    private DatabaseHelper databaseHelper;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        initViews();
        getAndSetIntentData();

        btnUpdate.setOnClickListener(UpdateTaskActivity.this);
        btnDelete.setOnClickListener(UpdateTaskActivity.this);
        btnBack.setOnClickListener(UpdateTaskActivity.this);
    }

    // Method to initialize views
    private void initViews() {
        edtTaskTitle = findViewById(R.id.updateTask_edt_title);
        edtTaskDescription = findViewById(R.id.updateTask_edt_description);
        edtTaskDeadline = findViewById(R.id.updateTask_edt_deadline);

        btnUpdate = findViewById(R.id.updateTask_btn_update);
        btnDelete = findViewById(R.id.updateTask_btn_delete);
        btnBack = findViewById(R.id.updateTask_btn_back);
    }

    // Method to get task details from the intent and set them to EditText fields
    private void getAndSetIntentData() {
        taskID = getIntent().getIntExtra("taskID", -1);
        taskCourseCode = getIntent().getStringExtra("taskCourseCode");
        taskTitle = getIntent().getStringExtra("taskTitle");
        taskDescription = getIntent().getStringExtra("taskDescription");
        taskDeadline = getIntent().getStringExtra("taskDeadline");

        edtTaskTitle.setText(taskTitle);
        edtTaskDescription.setText(taskDescription);
        edtTaskDeadline.setText(taskDeadline);
    }

    // Method to update task details
    private void updateTask() {
        String updatedTaskTitle = edtTaskTitle.getText().toString().trim();
        String updatedTaskDescription = edtTaskDescription.getText().toString().trim();
        String updatedTaskDeadline = edtTaskDeadline.getText().toString().trim();

        if (updatedTaskTitle.isEmpty() || updatedTaskDescription.isEmpty() || updatedTaskDeadline.isEmpty()) {
            Toast.makeText(UpdateTaskActivity.this, "Please fill empty fields", Toast.LENGTH_SHORT).show();
        }
        else {
            if (taskID == -1) {
                Toast.makeText(UpdateTaskActivity.this, "Invalid task ID", Toast.LENGTH_SHORT).show();
            }
            else {
                task = new Task(taskID, taskCourseCode, updatedTaskTitle, updatedTaskDescription, updatedTaskDeadline);

                databaseHelper = new DatabaseHelper(UpdateTaskActivity.this);
                databaseHelper.updateTaskToCourse(task); // Update the task in the database
            }
        }
    }

    // Method to delete the task
    private void deleteTask(Task task) {
        databaseHelper = new DatabaseHelper(UpdateTaskActivity.this);
        databaseHelper.deleteTaskToCourse(task); // Delete the task from the database
    }

    @Override
    public void onClick(View v) {
        // If update button is clicked
        if (v.getId() == R.id.updateTask_btn_update) {
            updateTask(); // Update the task details
            ActivityHelper.openNewActivity(UpdateTaskActivity.this, MainActivity.class);
        }

        // If delete button is clicked
        if (v.getId() == R.id.updateTask_btn_delete) {
            // Show confirmation dialog for task deletion
            showConfirmDialog();
        }

        if (v.getId() == R.id.updateTask_btn_back) {
            ActivityHelper.openNewActivity(UpdateTaskActivity.this, MainActivity.class);
        }
    }

    // Method to show confirmation dialog for task deletion
    private void showConfirmDialog() {
        task = new Task(taskID, taskCourseCode, taskTitle, taskDescription, taskDeadline);

        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateTaskActivity.this);
        builder.setTitle("Delete Task from " + taskCourseCode);
        builder.setMessage("Are you sure you want to delete " + task.getTaskTitle() + "?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Delete the task
                deleteTask(task);
                ActivityHelper.openNewActivity(UpdateTaskActivity.this, MainActivity.class);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(UpdateTaskActivity.this, "Task deletion cancelled", Toast.LENGTH_SHORT).show();
            }
        });

        // Create and show the AlertDialog
        builder.create().show();
    }

    public void onBackPressed() {
        ActivityHelper.openNewActivity(UpdateTaskActivity.this, MainActivity.class);
    }
}