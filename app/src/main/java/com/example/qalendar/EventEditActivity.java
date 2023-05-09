package com.example.qalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EventEditActivity extends AppCompatActivity
{
    private EditText eventNameET;
    private TextView eventDateET, eventTimeET, descriptionEt;
    private Button startTimeButton, endTimeButton;
    private LocalTime time;
    private Event event;
    FirebaseFirestore firestore;


    //Event Time picker variables
    Button startButton;
    int hour, minute;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();

        time = LocalTime.now();

        //Event Time picker logic
        startButton = findViewById(R.id.startTimeButton);

    }
    private void initWidgets()
    {
        descriptionEt = findViewById(R.id.descriptionEt);
        eventNameET = findViewById(R.id.eventNameET);
        eventDateET = findViewById(R.id.eventDateET);
        eventTimeET = findViewById(R.id.eventTimeET);
        startTimeButton = findViewById(R.id.startTimeButton);
        endTimeButton = findViewById(R.id.endTimeButton);
    }


    public void saveEventAction(View view)
    {
        LocalDate date = LocalDate.now();
        //event = new Event(eventNameET, date ,startTimeButton, endTimeButton, descriptionEt);

        firestore = FirebaseFirestore.getInstance();

        //need start,end,name,duration,description
        Map<String,Object> events = new HashMap<>();
        events.put("event Name", eventNameET);
        events.put("date", date);
        events.put("start time", startTimeButton);
        events.put("End Time", endTimeButton);
        events.put("description", descriptionEt);
        firestore.collection("Events").add(events).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(),"Success", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG).show();
            }
        });



        //Event newEvent = new Event(eventNameET, CalendarUtils.selectedDate, time);
        //Event.eventsList.add(newEvent);
        finish();
    }

    //Event time picker methods
    public void startTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                startButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select Start Time");
        timePickerDialog.show();
    }

    public void endTimePicker(View view) {
        TimePickerDialog.OnTimeSetListener onTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                startButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeListener, hour, minute, true);
        timePickerDialog.setTitle("Select End Time");
        timePickerDialog.show();
    }
}