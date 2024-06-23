package com.lim.studybuddyapp;

public class Course {
    private String courseCode; // Unique identifier for the course
    private String courseName;
    private String courseSchedule;
    private boolean isExpanded;

    // Constructor to initialize Course object with provided values
    public Course(String courseCode, String courseName, String courseSchedule) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.courseSchedule = courseSchedule;
        this.isExpanded = false;
    }

    // Getters
    public String getCourseCode() {
        return courseCode;
    }
    public String getCourseName() {
        return courseName;
    }
    public String getCourseSchedule() {
        return courseSchedule;
    }
    public boolean isExpanded() {
        return isExpanded;
    }

    // Setter
    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
