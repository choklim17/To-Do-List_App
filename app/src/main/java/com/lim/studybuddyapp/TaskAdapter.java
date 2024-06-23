package com.lim.studybuddyapp;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewAdapter> {
    private List<Task> taskList;
    private Activity activity;

    // Constructor to initialize the adapter with a list of tasks and an activity
    public TaskAdapter(List<Task> taskList, Activity activity) {
        this.taskList = taskList;
        this.activity = activity;
    }

    // Method invoked when RecyclerView needs a new ViewHolder of the given type to represent an item
    @NonNull
    @Override
    public TaskViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for a single task item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_task, parent, false);
        return new TaskViewAdapter(view);
    }

    // Method called by RecyclerView to display the data at the specified position
    @Override
    public void onBindViewHolder(@NonNull TaskViewAdapter holder, int position) {
        // Get the task at the specified position
        Task task = taskList.get(position);

        // Set task details to the ViewHolder's views
        holder.txtTaskTitle.setText(task.getTaskTitle());
        holder.txtTaskDescription.setText(task.getTaskDescription());
        holder.txtTaskDeadline.setText(task.getTaskDeadline());

        // Set click listener for managing task to navigate to UpdateTaskActivity with task details
        holder.btnManageTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, UpdateTaskActivity.class);
                intent.putExtra("taskID", task.getTaskID());
                intent.putExtra("taskCourseCode", task.getCourseCode());
                intent.putExtra("taskTitle", task.getTaskTitle());
                intent.putExtra("taskDescription", task.getTaskDescription());
                intent.putExtra("taskDeadline", task.getTaskDeadline());

                activity.startActivity(intent);
                activity.finish();
            }
        });

        // Toggle visibility of expanded layout based on task expansion state
        if (task.isExpanded()) {
            TransitionManager.beginDelayedTransition(holder.parentLayout);
            holder.taskExpandedLayout.setVisibility(View.VISIBLE);
            holder.imgDownArrow.setVisibility(View.GONE);
        }
        else {
            TransitionManager.beginDelayedTransition(holder.parentLayout);
            holder.taskExpandedLayout.setVisibility(View.GONE);
            holder.imgDownArrow.setVisibility(View.VISIBLE);
        }
    }

    // Method to get the total number of items in the data set
    @Override
    public int getItemCount() {
        return taskList.size();
    }

    // ViewHolder class to hold the views for each task item
    public class TaskViewAdapter extends RecyclerView.ViewHolder {
        // Views within the item layout
        private MaterialCardView parentLayout;
        private TextView txtTaskTitle, txtTaskDescription, txtTaskDeadline;
        private ImageView imgDownArrow, imgUpArrow;
        private Button btnManageTask;
        private ConstraintLayout taskExpandedLayout;

        // Constructor to initialize views and click listeners
        public TaskViewAdapter(@NonNull View itemView) {
            super(itemView);

            initViews();

            // Set click listeners for expanding/collapsing the task details
            imgDownArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleExpandedState();
                }
            });

            imgUpArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    toggleExpandedState();
                }
            });
        }

        // Method to initialize views
        private void initViews() {
            txtTaskTitle = itemView.findViewById(R.id.task_txt_taskTitle);
            txtTaskDescription = itemView.findViewById(R.id.task_txt_taskDescription);
            txtTaskDeadline = itemView.findViewById(R.id.task_txt_taskDeadline);

            imgDownArrow = itemView.findViewById(R.id.task_btn_down);
            imgUpArrow = itemView.findViewById(R.id.task_btn_up);
            btnManageTask = itemView.findViewById(R.id.task_btn_edit);

            parentLayout = itemView.findViewById(R.id.task_parentLayout);
            taskExpandedLayout = itemView.findViewById(R.id.task_expanded_layout);
        }

        // Method to toggle the expanded state of the task
        private void toggleExpandedState() {
            Task task = taskList.get(getAdapterPosition());
            task.setExpanded(!task.isExpanded());
            notifyItemChanged(getAdapterPosition());
        }
    }
}
