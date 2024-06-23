package com.lim.studybuddyapp;

public class Task {
    private int taskID; // Unique identifier for the task
    private String courseCode; // Course code associated with the task
    private String taskTitle;
    private String taskDescription;
    private String taskDeadline;
    private boolean isExpanded;

    // Constructor to initialize Task object with provided values
    public Task(int taskID, String courseCode, String taskTitle, String taskDescription, String taskDeadline) {
        this.taskID = taskID;
        this.courseCode = courseCode;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskDeadline = taskDeadline;
        this.isExpanded = false;
    }

    // Getters
    public int getTaskID() {
        return taskID;
    }
    public String getCourseCode() {
        return courseCode;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public String getTaskDeadline() {
        return taskDeadline;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    // Setter
    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
