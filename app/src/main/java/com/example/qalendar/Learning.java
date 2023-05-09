package com.example.qalendar;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Learning extends AppCompatActivity
{
    private DatabaseReference dRef = FirebaseDatabase.getInstance().getReference();
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    protected TextView rTitle;
    protected TextView rDescription;
    protected LocalTime rStartTime;
    protected LocalDate rStartDate;
    protected LocalDate rEndDate;
    protected LocalTime rEndTime;
    protected LocalTime rDuration;
    protected int rPriority;
    protected int rPopulation;

    private Vector<Vector<Event>> simTitle;
    private Vector<Vector<Event>> simSTime;

    public Learning(TextView rTitle, TextView rDescription, LocalTime rStartTime, LocalDate rStartDate, LocalDate rEndDate, LocalTime rEndTime, LocalTime rDuration, int rPriority, int rPopularity)
    {
        this.rTitle = rTitle;
        this.rDescription = rDescription;
        this.rStartTime = rStartTime;
        this.rStartDate = rStartDate;
        this.rEndDate = rEndDate;
        this.rEndTime = rEndTime;
        this.rDuration = rDuration;
        this.rPriority = rPriority;
        this.rPopulation = rPopularity;
    }

    db.collection("users")
            .get()
        .addOnSuccessListener { result ->
    for (document in result) {
        Log.d(TAG, "${document.id} => ${document.data}")
    }
}
        .addOnFailureListener { exception ->
        Log.w(TAG, "Error getting documents.", exception)

    public TextView getrTitle()
    {
        return rTitle;
    }

    public void setrTitle(TextView rTitle)
    {
        this.rTitle = rTitle;
    }

    public TextView getrDescription()
    {
        return rDescription;
    }

    public void setrDescription(TextView rDescription)
    {
        this.rDescription = rDescription;
    }

    public LocalTime getrStartTime()
    {
        return rStartTime;
    }

    public void setrStartTime(LocalTime rStartTime)
    {
        this.rStartTime = rStartTime;
    }

    public LocalDate getrStartDate()
    {
        return rStartDate;
    }

    public void setrStartDate(LocalDate rStartDate)
    {
        this.rStartDate = rStartDate;
    }

    public LocalDate getrEndDate()
    {
        return rEndDate;
    }

    public void setrEndDate(LocalDate rEndDate)
    {
        this.rEndDate = rEndDate;
    }

    public LocalTime getrEndTime()
    {
        return rEndTime;
    }

    public void setrEndTime(LocalTime rEndTime)
    {
        this.rEndTime = rEndTime;
    }

    public LocalTime getrDuration()
    {
        return rDuration;
    }

    public void setrDuration(LocalTime rDuration)
    {
        this.rDuration = rDuration;
    }

    public int getrPriority() {
        return rPriority;
    }

    public void setrPriority(int rPriority)
    {
        this.rPriority = rPriority;
    }

    public int getrPopulation()
    {
        return rPopulation;
    }

    public void setrPopulation(int rPopulation)
    {
        this.rPopulation = rPopulation;
    }

    public void User()
    {
        Map<String,Object> user = new HashMap<>();
        user.put("firstName", "Easy");
        user.put("lastName", "Hard");
        user.put("description", "PLs Work");
        firestore.collection("users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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
    }

}


