package com.example.qalendar;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Event extends AppCompatActivity
{
    FirebaseFirestore firestore;

    public static ArrayList<Event> eventsList = new ArrayList<>();

    public static ArrayList<Event> eventsForDate(LocalDate date) {
        ArrayList<Event> events = new ArrayList<>();

        for (Event event : eventsList) {
            if (event.getDate().equals(date)) {
                events.add(event);
            }
        }

        return events;
    }

    public static ArrayList<Event> eventsForDateAndTime(LocalDate date, LocalTime time) {
        ArrayList<Event> events = new ArrayList<>();

        for (Event event : eventsList) {
            int eventHour = event.time.getHour();
            int cellHour = time.getHour();
            if (event.getDate().equals(date) && eventHour == cellHour) {
                events.add(event);
            }
        }

        return events;
    }
    public LocalDate getDate() {
        return date;
    }

    private String name;
    private LocalDate date;
    private LocalTime time;
    private Date startTime;
    private Date endTime;
    private String location;


    private String description;

    private DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference("Events");

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }


    public Event(String title, Date startTime, Date endTime, LocalDate date, String description) {
        this.name = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.description = description;
    }

    EditText editText;

    String stringDateSelected = "null";

    DatabaseReference dbRef;
    FirebaseFirestore fRef;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void saveToFirebase() {
        DatabaseReference eventRef = eventsRef.push();
        eventRef.setValue(this);
    }

    private void calendarClicked() {

        dbRef.child(stringDateSelected).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue() != null) {
                    editText.setText(snapshot.getValue().toString());
                } else {
                    editText.setText("null");
                }
            }
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }





}


