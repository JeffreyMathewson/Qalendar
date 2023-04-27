package com.example.qalendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalTime;

public class EventEditActivity extends AppCompatActivity {
    private LocalTime startTime;
    private LocalTime endTime;
    private EditText eventNameET;
    private TextView eventDateET, eventTimeET;

    private LocalTime time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        time = LocalTime.now();
        eventDateET.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTimeET.setText("Start time: " + CalendarUtils.formattedTime(time));
        eventTimeET.setOnClickListener(view -> showStartTimePickerDialog());
        eventTimeET.setOnLongClickListener(view -> {
            showEndTimePickerDialog();
            return true;
        });
    }

    private void initWidgets() {
        eventNameET = findViewById(R.id.eventNameET);
        eventDateET = findViewById(R.id.eventDateET);
        eventTimeET = findViewById(R.id.eventTimeET);
    }

    public void saveEventAction(View view) {
        String eventName = eventNameET.getText().toString();
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, startTime, endTime);
        Event.eventsList.add(newEvent);
        finish();
    }

    private void showStartTimePickerDialog() {
        TimePickerFragment timePickerDialog = new TimePickerFragment(
        );
        timePickerDialog.show(getSupportFragmentManager(), "start_time_picker");
    }

    private void showEndTimePickerDialog() {
        TimePickerFragment timePickerDialog = new TimePickerFragment(
        );
        timePickerDialog.show(getSupportFragmentManager(), "end_time_picker");
    }

    private void saveEvent() {
        String eventName = eventNameET.getText().toString();
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, startTime, endTime);
        Event.eventsList.add(newEvent);
        finish();
    }
}