package com.example.qalendar;

import static com.example.qalendar.CalendarUtils.daysInMonthArray;
import static com.example.qalendar.CalendarUtils.monthYearFromDate;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import yuku.ambilwarna.AmbilWarnaDialog;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{
    //testing
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;

    private LinearLayout mLayout;

    int mDefaultColor1;
    int mDefaultColor2;
    int mDefaultColor3;

    private Button colorButton1;
    //private Button colorButton2;


    FirebaseFirestore firestore;
    private ColorPicker colorPicker = new ColorPicker();



    // This is for trying to change the background color from the ColorPicker3 class.
    public void updateBackgroundColor(int color) {
        getWindow().getDecorView().setBackgroundColor(color);
    }


    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firestore = FirebaseFirestore.getInstance();


        // This is for trying to change the background color from the ColorPicker3 class:
            // Retrieve the color value from the intent's extras
        int backgroundColor = getIntent().getIntExtra("BACKGROUND_COLOR", Color.WHITE);

            // Update the background color
        updateBackgroundColor(backgroundColor);


        //need start, end, name.duration, description
        Map<String,Object> user = new HashMap<>();
        user.put("firstName", "Easy");
        user.put("lastName", "Hard");
        user.put("description", "PLs Work");
        firestore.collection("users").add(user).addOnSuccessListener(documentReference -> Toast.makeText(getApplicationContext(),"Success", Toast.LENGTH_LONG).show()).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_LONG).show());



        colorButton1 = findViewById(R.id.colorButton1);
        colorButton1.setOnClickListener(v -> {
            // Handle button click event
            //openColorPicker(v);

            startActivity(new Intent(MainActivity.this, ColorPicker3.class));
        });


        initWidgets();
        LocalDate selectedDate = LocalDate.now();
        setMonthView();
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calenderRecyclerView);
        monthYearText = findViewById(R.id.monthyeartv);
        colorButton1 = findViewById(R.id.colorButton1);
        //colorButton2 = findViewById(R.id.colorButton2);
        mLayout = findViewById(R.id.monthLayout);
    }
    void setMonthView()
    {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray();


        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, (CalendarAdapter.OnItemListener) this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    public void previousMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }
    public void nextMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void OnItemClick(int position, LocalDate date)
    {
        if(date != null)
        {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }

    public void weeklyAction(View view)
    {
        startActivity(new Intent(this, WeeklyViewActivity.class));
    }
    public void dailyAction(View view)
    {
        startActivity(new Intent(this, DailyCalendarActivity.class));
    }
    public void newEventAction(View view)
    {
        startActivity(new Intent(this, EventEditActivity.class));
    }

}