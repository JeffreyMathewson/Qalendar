package com.example.qalendar;

import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Event {
    public class Vars
    {
        String name = "test";
        int date = 1;
    }
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

    public static ArrayList<Event> eventsForDateAndTime(LocalDate date, LocalTime time)
    {
        ArrayList<Event> events = new ArrayList<>();

        for (Event event : eventsList)
        {
            int eventHour = event.time.getHour();
            int cellHour = time.getHour();
            int eventMin = event.time.getMinute();
            int cellMin = time.getMinute();
            if (event.getDate().equals(date) && eventHour == cellHour)
            {
                events.add(event);
            }
        }

        return events;
    }

    private String name;
    private LocalDate date;
    public LocalTime time;
    private Date startTime;
    private Date endTime;
    private String location;
    private String description;

    private DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference("Events");

    public Event(String name, LocalDate date, LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public LocalDate getDate() {
        return date;
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


    public Event(String title, Date startTime, Date endTime, String location, String description) {
        this.name = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void buttonSaveEvent(View view) {
        fRef = FirebaseFirestore.getInstance();
        Vars vars = new Vars();
        Map<String, String> user = new HashMap<>();
        user.put("Event name", "test");
        fRef.collection("Events")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                //Toast.makeText(Event.this, "Success", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        //android:onClick="buttonSaveEvent" has to be done in <Button
        //Toast.makeText(getActivity(), "test", Toast.LENGTH_LONG).show();

    }

}


