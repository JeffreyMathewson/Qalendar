package com.example.qalendar;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;

public class EventEditActivity extends AppCompatActivity {
    private EditText eventNameEt;
    private TextView eventDateEt, eventTimeEt;

    private LocalTime time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        initWidgets();
        time = LocalTime.now();
        eventDateEt.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTimeEt.setText("Time: " + CalendarUtils.formattedTime(time));
        loadEventsFromFile();
    }

    private void initWidgets() {
        eventNameEt = findViewById(R.id.eventNameET);
        eventDateEt = findViewById(R.id.eventDateET);
        eventTimeEt = findViewById(R.id.eventTimeET);
    }

    private void saveEventsToFile() {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(Event.eventsList);
            FileOutputStream fos = openFileOutput("events.json", Context.MODE_PRIVATE);
            fos.write(json.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadEventsFromFile() {
        try {
            File file = new File(getFilesDir(), "events.json");
            if (file.exists()) {
                FileInputStream fis = openFileInput("events.json");
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                fis.close();
                String json = sb.toString();
                Gson gson = new Gson();
                Event.eventsList = gson.fromJson(json, Event.eventsList.getClass());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveEventAction(View view) {
        String eventName = eventNameEt.getText().toString();
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, time);
        Event.eventsList.add(newEvent);
        saveEventsToFile();
        finish();
    }
}
