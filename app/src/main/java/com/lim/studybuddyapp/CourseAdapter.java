package com.lim.studybuddyapp;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewAdapter> {
    private List<Course> courses;
    private Activity activity;

    // Constructor to initialize the adapter with a list of courses and an activity
    public CourseAdapter(List<Course> courses, Activity activity) {
        this.courses = courses;
        this.activity = activity;
    }

    // Method invoked when RecyclerView needs a new ViewHolder of the given type to represent an item
    @NonNull
    @Override
    public CourseViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for a single course item
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_course, parent, false);

        return new CourseViewAdapter(itemView);
    }

    // Method called by RecyclerView to display the data at the specified position
    @Override
    public void onBindViewHolder(@NonNull CourseViewAdapter holder, int position) {
        // Get the course at the specified position
        Course course = courses.get(position);

        // Set course details to the ViewHolder's views
        holder.txtCourseCode.setText(course.getCourseCode());
        holder.txtCourseName.setText(course.getCourseName());
        holder.txtCourseSchedule.setText(course.getCourseSchedule());

        // Set click listener for the parent layout to navigate to TaskActivity with course details
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, TaskActivity.class);
                intent.putExtra("code", course.getCourseCode());
                intent.putExtra("name", course.getCourseName());
                intent.putExtra("schedule", course.getCourseSchedule());

                activity.startActivity(intent);
                activity.finish();
            }
        });

        if (course.isExpanded()) {
            TransitionManager.beginDelayedTransition(holder.parentLayout);
            holder.expandedLayout.setVisibility(View.VISIBLE);
            holder.btnDownArrow.setVisibility(View.GONE);
        }
        else {
            TransitionManager.beginDelayedTransition(holder.parentLayout);
            holder.expandedLayout.setVisibility(View.GONE);
            holder.btnDownArrow.setVisibility(View.VISIBLE);
        }

        // Set click listener for editing the course
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, UpdateCourseActivity.class);
                intent.putExtra("courseCode", course.getCourseCode());
                intent.putExtra("courseName", course.getCourseName());
                intent.putExtra("courseSchedule", course.getCourseSchedule());

                activity.startActivity(intent);
                activity.finish();
            }
        });
    }

    // Method to get the total number of items in the data set
    @Override
    public int getItemCount() {
        return courses.size();
    }

    // ViewHolder class to hold the views for each course item
    public class CourseViewAdapter extends RecyclerView.ViewHolder {
        // Views within the item layout
        private MaterialCardView parentLayout;
        private TextView txtCourseCode, txtCourseName, txtCourseSchedule;
        private ImageView btnDownArrow, btnUpArrow;
        private Button btnEdit;
        private ConstraintLayout expandedLayout;

        // Constructor to initialize views
        public CourseViewAdapter(@NonNull View itemView) {
            super(itemView);

            initViews();

            // Set click listener for expanding/collapsing the course details
            btnDownArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Course course = courses.get(getAdapterPosition());
                    course.setExpanded(!course.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            btnUpArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Course course = courses.get(getAdapterPosition());
                    course.setExpanded(!course.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }

        // Method to initialize views
        private void initViews() {
            txtCourseCode = itemView.findViewById(R.id.txt_courseCode);
            txtCourseName = itemView.findViewById(R.id.txt_courseName);
            txtCourseSchedule = itemView.findViewById(R.id.txt_courseSchedule);

            btnEdit = itemView.findViewById(R.id.course_btn_edit);
            btnDownArrow = itemView.findViewById(R.id.course_btn_down);
            btnUpArrow = itemView.findViewById(R.id.course_btn_up);

            expandedLayout = itemView.findViewById(R.id.expanded_layout);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
