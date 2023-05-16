package com.example.qalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Settings extends AppCompatActivity {

    GoogleSignInClient gsc;
    ProfileActivity profileActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void profileActivity(View view) {
        startActivity(new Intent(this, ProfileActivity.class));
    }

    public void dailyAction(View view) {
        startActivity(new Intent(this, DailyCalendarActivity.class));

    }

    public void weeklyAction(View view) {
        startActivity(new Intent(this, WeeklyViewActivity.class));

    }

    public void monthlyAction(View view) {
        startActivity(new Intent(this, MonthlyViewActivity.class));

    }
}