package com.lim.studybuddyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;

    // Database Name and Version
    private static final String DATABASE_NAME = "studyBuddy.db";
    private static final int DATABASE_VERSION = 1;

    // Course Table
    private static final String COURSE_TABLE_NAME = "Course_Table";
    private static final String COLUMN_COURSE_CODE = "course_code";
    private static final String COLUMN_COURSE_NAME = "course_name";
    private static final String COLUMN_COURSE_SCHEDULE = "course_schedule";

    // Task Table
    private static final String TASK_TABLE_NAME = "Task_Table";
    private static final String COLUMN_TASK_ID = "task_id";
    private static final String COLUMN_TASK_TITLE = "task_title";
    private static final String COLUMN_TASK_DESCRIPTION = "task_description";
    private static final String COLUMN_TASK_DEADLINE = "task_deadline";

    // Foreign Key Constraint for Task Table
    private static final String FOREIGN_KEY_CONSTRAINT = "FOREIGN KEY (" + COLUMN_COURSE_CODE + ") REFERENCES " +
            COURSE_TABLE_NAME + "(" + COLUMN_COURSE_CODE + ") ON DELETE CASCADE";

    // Constructor
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Course Table
        String createCourseTable = "CREATE TABLE " + COURSE_TABLE_NAME + " (" +
                COLUMN_COURSE_CODE + " VARCHAR(10) PRIMARY KEY NOT NULL, " +
                COLUMN_COURSE_NAME + " VARCHAR(55) NOT NULL, " +
                COLUMN_COURSE_SCHEDULE + " VARCHAR(40) NOT NULL " +
                ");";

        // Create Task Table
        String createTaskTable = "CREATE TABLE " + TASK_TABLE_NAME + " (" +
                COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                COLUMN_COURSE_CODE + " VARCHAR(10) NOT NULL, " +
                COLUMN_TASK_TITLE + " VARCHAR(15) NOT NULL, " +
                COLUMN_TASK_DESCRIPTION + " VARCHAR(60) NOT NULL, " +
                COLUMN_TASK_DEADLINE + " VARCHAR(25) NOT NULL, " +
                FOREIGN_KEY_CONSTRAINT +
                ");";

        db.execSQL(createCourseTable);
        db.execSQL(createTaskTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing table if they exists
        db.execSQL("DROP TABLE IF EXISTS " + COURSE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE_NAME);

        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);

        // Enable foreign key constraints
        db.execSQL("PRAGMA foreign_keys = ON");
    }

    // Method to add a new course to the Course_Table
    public void addCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_COURSE_CODE, course.getCourseCode());
        contentValues.put(COLUMN_COURSE_NAME, course.getCourseName());
        contentValues.put(COLUMN_COURSE_SCHEDULE, course.getCourseSchedule());

        long insert = db.insert(COURSE_TABLE_NAME, null, contentValues);

        checkResult(insert, "Course added successfully", "Fail to add Course");

        /* SQL Query
            INSERT INTO COURSE_TABLE_NAME (course_code, course_name, course_schedule)
            VALUES ('course.getCourseCode()', 'course.getCourseName()', 'course.getCourseSchedule()');
         */
    }

    // Method to retrieve all courses from the Course_Table
    public List<Course> getCourses() {
        List<Course> courseList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + COURSE_TABLE_NAME + ";";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String courseCode = cursor.getString(0);
                String courseName = cursor.getString(1);
                String courseSchedule = cursor.getString(2);

                Course course = new Course(courseCode, courseName, courseSchedule);
                courseList.add(course);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return courseList;

        /* SQL Query
            SELECT * FROM Course_Table;
         */
    }

    // Method to update a course in the Course_Table
    public void updateCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_COURSE_SCHEDULE, course.getCourseSchedule());

        long update = db.update(COURSE_TABLE_NAME, contentValues, COLUMN_COURSE_CODE + "=?", new String[] {course.getCourseCode()});

        checkResult(update, "Course updated successfully", "Fail to update Course");

        /* SQL Query
            UPDATE Course_Table
            SET course_schedule = 'course.getCourseSchedule()'
            WHERE course_code = 'course.getCourseCode()';
         */
    }

    // Method to delete a course from the Course_Table
    public void deleteCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();

        long delete = db.delete(COURSE_TABLE_NAME, COLUMN_COURSE_CODE + "=?", new String[] {course.getCourseCode()});

        checkResult(delete, "Course deleted successfully", "Fail to delete course");

        /* SQL Query
            DELETE FROM Course_Table WHERE course_code = 'course.getCourseCode()';
         */
    }

    // Method to add a task to the Task_Table
    public void addTaskToCourse(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_COURSE_CODE, task.getCourseCode());
        contentValues.put(COLUMN_TASK_TITLE, task.getTaskTitle());
        contentValues.put(COLUMN_TASK_DESCRIPTION, task.getTaskDescription());
        contentValues.put(COLUMN_TASK_DEADLINE, task.getTaskDeadline());

        long insert = db.insert(TASK_TABLE_NAME, null, contentValues);

        checkResult(insert, "Task added successfully", "Fail to add task");

        /* SQL Query
            INSERT INTO Task_Table (course_code, task_title, task_description, task_deadline)
            VALUES ('task.getCourseCode()', 'task.getTaskTitle()', 'task.getTaskDescription()', 'task.getTaskDeadline()');
         */
    }

    // Method to retrieve tasks for a specific course from the Task_Table
    public List<Task> getTasks(String courseCode) {
        List<Task> tasks = new ArrayList<>();

        String query = "SELECT * FROM " + TASK_TABLE_NAME +
                " WHERE " + COLUMN_COURSE_CODE + " =?";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, new String[] {courseCode});

        if (cursor.moveToFirst()) {
            do {
                int taskID = cursor.getInt(0);
                String code = cursor.getString(1);
                String taskTitle = cursor.getString(2);
                String taskDescription = cursor.getString(3);
                String taskDeadline = cursor.getString(4);

                Task task = new Task(taskID, code, taskTitle, taskDescription, taskDeadline);
                tasks.add(task);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return tasks;

        /* SQL Query
            SELECT * FROM Task_Table WHERE course_code = 'courseCode';
         */
    }

    // Method to update a task in the Task_Table
    public void updateTaskToCourse(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TASK_TITLE, task.getTaskTitle());
        contentValues.put(COLUMN_TASK_DESCRIPTION, task.getTaskDescription());
        contentValues.put(COLUMN_TASK_DEADLINE, task.getTaskDeadline());

        long update = db.update(TASK_TABLE_NAME, contentValues, COLUMN_TASK_ID + "=? AND " + COLUMN_COURSE_CODE + "=?",
                new String[] {String.valueOf(task.getTaskID()), task.getCourseCode()});

        checkResult(update, "Task updated successfully", "Fail to update task");

        /* SQL Query
            UPDATE Task_Table
            SET task_title = 'task.getTaskTitle()', task_description = 'task.getTaskDescription()', task_deadline = 'task.getTaskDeadline()'
            WHERE task_id = 'task.getTaskID()' AND course_code = 'task.getCourseCode()';
         */
    }

    // Method to delete a task from the Task_Table
    public void deleteTaskToCourse(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();

        long delete = db.delete(TASK_TABLE_NAME, COLUMN_TASK_ID + "=? AND " + COLUMN_COURSE_CODE + "=?",
                new String[] {String.valueOf(task.getTaskID()), task.getCourseCode()});

        checkResult(delete, "Task deleted successfully", "Fail to delete task");

        /* SQL Query
            DELETE FROM Task_Table
            WHERE task_id = 'task.getTaskID()' AND course_code = 'task.getCourseCode()';
         */
    }

    // Method to display a success or fail message
    private void checkResult(long result, String successMessage, String failMessage) {
        if (result == -1) {
            Toast.makeText(context, failMessage, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
