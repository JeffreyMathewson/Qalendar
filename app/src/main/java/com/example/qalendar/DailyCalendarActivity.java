package com.example.qalendar;

import static com.example.qalendar.CalendarUtils.selectedDate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

public class DailyCalendarActivity extends AppCompatActivity
{
    private TextView monthDayText;
    private TextView dayOfWeekTV;
    private ListView hourListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_calendar);
        initWidgets();
    }

    private void initWidgets()
    {
        monthDayText = findViewById(R.id.monthDayText);
        dayOfWeekTV = findViewById(R.id.dayOfWeekTV);
        hourListView = findViewById(R.id.hourListView);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setDayView();
    }

    private void setDayView()
    {
        monthDayText.setText(CalendarUtils.monthDayFromDate(selectedDate));
        String dayOfWeek = selectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        dayOfWeekTV.setText(dayOfWeek);
        setHourAdapter();
    }

    private void setHourAdapter()
    {
        HourAdapter hourAdapter = new HourAdapter(getApplicationContext(), hourEventList());
        HourAdapter hourAdapter2 = new HourAdapter(getApplicationContext(), hourEventList());
        hourListView.setAdapter(hourAdapter);
        hourListView.setAdapter(hourAdapter2);
    }

    private ArrayList<HourEvent> hourEventList() {
        LocalDateTime now = LocalDateTime.now();
        LocalTime currentTime = now.toLocalTime();
        ArrayList<HourEvent> list = new ArrayList<>();

        for (int hour = 0; hour < 24; hour++) {
            LocalTime time;

            if (hour == currentTime.getHour()) {
                int minutes = currentTime.getMinute();
                if (minutes >= 30) {
                    time = LocalTime.of(hour, 30);
                    ArrayList<Event> events = Event.eventsForDateAndTime(selectedDate, time);
                    HourEvent hourEvent = new HourEvent(time, events);
                    list.add(hourEvent);
                }
            } else {
                time = LocalTime.of(hour, 0);
                ArrayList<Event> events = Event.eventsForDateAndTime(selectedDate, time);
                HourEvent hourEvent = new HourEvent(time, events);
                list.add(hourEvent);
            }
        }

        return list;
    }


    public void previousDayAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusDays(1);
        setDayView();
    }

    public void nextDayAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusDays(1);
        setDayView();
    }

    public void newEventAction(View view)
    {
        startActivity(new Intent(this, EventEditActivity.class));
    }
    public void weeklyAction(View view)
    {
        startActivity(new Intent(this, WeeklyViewActivity.class));
    }
    public void monthlyAction(View view)
    {
        startActivity(new Intent(this, MonthlyViewActivity.class));
    }

    public void settingsAction(View view) {
        startActivity(new Intent(this, Settings.class));
    }
}