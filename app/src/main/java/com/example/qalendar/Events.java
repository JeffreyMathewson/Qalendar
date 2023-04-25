package com.example.qalendar;

import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class Events {
    private String title;
    private Date startTime;
    private Date endTime;
    private String location;
    private String description;

    private DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference("Events");

    public Events(String title, Date startTime, Date endTime, String location, String description) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    EditText editText;

    String stringDateSelected = "null";

    DatabaseReference dbRef;

    public void setTitle(String title) {
        this.title = title;
    }

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

    private void calendarClicked()
    {

        dbRef.child(stringDateSelected).addListenerForSingleValueEvent(new ValueEventListener()
        {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.getValue() != null){
                    editText.setText(snapshot.getValue().toString());
                }else {
                    editText.setText("null");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }

    public void buttonSaveEvent(View view){
        dbRef.child(stringDateSelected).setValue(editText.getText().toString());
    }
    //android:onClick="buttonSaveEvent" has to be done in <Button
}

