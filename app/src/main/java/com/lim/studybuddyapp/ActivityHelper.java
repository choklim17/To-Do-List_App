package com.lim.studybuddyapp;

import android.app.Activity;
import android.content.Intent;

public class ActivityHelper {

    // Method to open a new activity and finish the current activity
    public static void openNewActivity(Activity currentActivity, Class<?> targetActivity) {
        Intent intent = new Intent(currentActivity, targetActivity);
        currentActivity.startActivity(intent);
        currentActivity.finish();
    }
}
