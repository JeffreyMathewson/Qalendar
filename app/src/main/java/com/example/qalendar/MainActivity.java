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


    FirebaseFirestore firestore;
    private ColorPicker colorPicker = new ColorPicker();


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firestore = FirebaseFirestore.getInstance();

        //need start,end,name.duration,description
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



        mDefaultColor1 = ContextCompat.getColor(this, com.google.android.material.R.color.design_default_color_primary); // R.color.colorPrimary.
        mDefaultColor2 = ContextCompat.getColor(this, com.google.android.material.R.color.design_default_color_secondary); // R.color.colorSecondary.
        mDefaultColor3 = ContextCompat.getColor(this, com.google.android.material.R.color.design_default_color_background); // R.color.colorBackground. There seems to be no colorTertiary.


        // I changed it from mButton to colorButton and then back to mButton.
        colorButton1 = findViewById(R.id.colorButton1);
        colorButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click event
                openColorPicker(v);
            }
        });

        // I was shown this same code. The only thing that could be wrong here would be the openColorPicker(View) method.


        initWidgets();
        LocalDate selectedDate = LocalDate.now();
        setMonthView();
    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calenderRecyclerView);
        monthYearText = findViewById(R.id.monthyeartv);
        colorButton1 = findViewById(R.id.colorButton1);
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

    public void openColorPicker(View view) {
        AmbilWarnaDialog colorer = new AmbilWarnaDialog(this, mDefaultColor1, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefaultColor1 = color;
                mLayout.setBackgroundColor(color);
            }
        });

        colorer.show();
    }
}