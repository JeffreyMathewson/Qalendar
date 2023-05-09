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

    private ArrayList<HourEvent> hourEventList()
    {
        LocalTime time, time2;
        ArrayList<Event> events, events2;
        HourEvent hourEvent;
        HourEvent halfHourEvent;
        ArrayList<HourEvent> list = new ArrayList<>();
        ArrayList<HourEvent> list2 = new ArrayList<>();
        for(int hour = 0; hour < 24; hour++)
        {
            time = LocalTime.of(hour, 0);
            events = Event.eventsForDateAndTime(selectedDate, time);
            hourEvent = new HourEvent(time, events);
            list.add(hourEvent);
            time2 = LocalTime.of(hour, 30);
            events2 = Event.eventsForDateAndTime(selectedDate, time2);
            halfHourEvent = new HourEvent(time2, events2);
            list2.add(halfHourEvent);
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
}