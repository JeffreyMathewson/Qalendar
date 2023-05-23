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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalTime;
import java.util.Locale;

public class EventEditActivity extends AppCompatActivity
{
    private EditText eventNameET;
    private int eventDateET;
    private TextView eventTimeET;
    private int descriptionEt;
    protected Button startTimeButton, endTimeButton, saveButton;

    private Event event;
    private LocalTime time;
    private String eventName;
    FirebaseFirestore firestore;
    private MainActivity mainActivity;

    //Event Time picker variables
    int hour, minute;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        initWidgets();
        //Event Time picker logic
    }

    private void initWidgets()
    {
        descriptionEt = (R.id.descriptionEt);
        eventDateET = (R.id.eventDateET);
        eventTimeET = findViewById(R.id.eventTimeET);
        startTimeButton = findViewById(R.id.startTimeButton);
        endTimeButton = findViewById(R.id.endTimeButton);
        saveButton = (Button) findViewById(R.id.saveButtonBT);
        eventNameET = findViewById(R.id.fuckingWORKET);
    }


    public void saveEventAction(View view)
    {
        String input = eventNameET.getText().toString().trim();
        Event newEvent = new Event(input, CalendarUtils.selectedDate, LocalTime.now());
        Event.eventsList.add(newEvent);
        finish();
    }

    private void addDataToFirestore(String name, String date, String description) {
        CollectionReference firestoreEvents = firestore.collection("Events");

        EventEditActivity editActivity = new EventEditActivity(name, date, description);

        firestoreEvents.add(editActivity).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(EventEditActivity.this, "success", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EventEditActivity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Event time picker methods
    public void startTimePicker(View view) {

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                startTimeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
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
                endTimeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeListener, hour, minute, true);
        timePickerDialog.setTitle("Select End Time");
        timePickerDialog.show();
    }

    private String name, description, date;

    public EventEditActivity(){

    }
    public EventEditActivity(String name, String description, String date){
        this.name = name;
        this.description = description;
        this.date = date;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getDate() {
        return date;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setDate(String date) {
        this.date = date;
    }
}