package com.example.qalendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

public class EventEditActivity extends AppCompatActivity
{
    private EditText eventNameET;
    private int eventDateET;
    private TextView eventTimeET;
    private EditText descriptionEt;
    protected Button startTimeButton, endTimeButton, saveButton;
    SQLiteOpenHelper dbHelper;

    FirebaseFirestore firestore;

    int hour, minute;

    //LocalSave Variables
    String startTime;
    String endTime;
    String eventName;
    String eventDate;
    String eventDescription;


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
        descriptionEt = findViewById(R.id.descriptionEt);
        eventDateET = (R.id.eventDateET);
        eventTimeET = findViewById(R.id.eventTimeET);
        startTimeButton = findViewById(R.id.startTimeButton);
        endTimeButton = findViewById(R.id.endTimeButton);
        saveButton = (Button) findViewById(R.id.saveButtonBT);
        eventNameET = findViewById(R.id.fuckingWORKET);
    }


    public void saveEventAction(View view)
    {
        //trying to save locally
        eventName = String.valueOf(eventNameET.getText());
        eventDate = String.valueOf(LocalDate.now());
        eventDescription = String.valueOf(descriptionEt.getText());
        // Save data
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Event Name", eventName);
        values.put("Event Date", eventDate);
        values.put("Start Time", startTime);
        values.put("End Time", endTime);
        values.put("Description", eventDescription);

        long rowId = database.insert("Events", null, values);

// Retrieve data
        database = dbHelper.getReadableDatabase();
        String[] projection = { "column1", "column2" };
        String selection = "column1 = ?";
        String[] selectionArgs = { "value1" };
        Cursor cursor = database.query("tableName", projection, selection, selectionArgs, null, null, null);
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String value1 = cursor.getString(cursor.getColumnIndex("column1"));
            @SuppressLint("Range") int value2 = cursor.getInt(cursor.getColumnIndex("column2"));
            // Use the retrieved values
        }
        cursor.close();

//        String input = eventNameET.getText().toString().trim();
//        Event newEvent = new Event(input, CalendarUtils.selectedDate, LocalTime.now());
//        Event.eventsList.add(newEvent);
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
        startTime = (String) startTimeButton.getText();
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
        endTime = (String) endTimeButton.getText();
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



