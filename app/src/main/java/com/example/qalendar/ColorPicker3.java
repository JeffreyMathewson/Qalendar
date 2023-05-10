package com.example.qalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import yuku.ambilwarna.AmbilWarnaDialog;

public class ColorPicker3 extends AppCompatActivity {

    ConstraintLayout mLayout1;

    int mDefaultColor1;
    int mDefaultColor2;
    int mDefaultColor3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker3);

        mLayout1 = (ConstraintLayout) findViewById(R.id.layout);
        //mLayout2 = (ConstraintLayout) findViewById(R.id.layout);
        //mLayout3 = (ConstraintLayout) findViewById(R.id.layout);

                                                                          // This is not the cause of the second and third Color Pickers not opening.
        mDefaultColor1 = ContextCompat.getColor(ColorPicker3.this, com.google.android.material.R.color.design_default_color_primary); // R.color.colorPrimary
        mDefaultColor2 = ContextCompat.getColor(ColorPicker3.this, com.google.android.material.R.color.design_default_color_secondary); // R.color.colorSecondary
        mDefaultColor3 = ContextCompat.getColor(ColorPicker3.this, com.google.android.material.R.color.design_default_color_background); // R.color.colorBackground

        // This area being first has nothing to do with the second and third Color Pickers not opening.
        Button mButton1 = (Button) findViewById(R.id.button1);
        mButton1.setOnClickListener(view -> {

            // Commenting out this line causes the first Color Picker not to open either.
            openColorPicker1();
        });

        Button mButton2 = (Button) findViewById(R.id.button2);
        mButton2.setOnClickListener(view -> openColorPicker2());

        Button mButton3 = (Button) findViewById(R.id.button3);
        mButton3.setOnClickListener(view -> openColorPicker3());
    }

    public void openColorPicker1() {
        AmbilWarnaDialog colorPicker1 = new AmbilWarnaDialog(this, mDefaultColor1, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefaultColor1 = color;
                mLayout1.setBackgroundColor(mDefaultColor1);
            }
        });
        colorPicker1.show();
    }

    public void openColorPicker2() {
        AmbilWarnaDialog colorPicker2 = new AmbilWarnaDialog(this, mDefaultColor2, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefaultColor2 = color;
                mLayout1.setBackgroundColor(mDefaultColor2);
            }
        });
        colorPicker2.show();
    }

    public void openColorPicker3() {
        AmbilWarnaDialog colorPicker3 = new AmbilWarnaDialog(this, mDefaultColor3, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefaultColor3 = color;
                mLayout1.setBackgroundColor(mDefaultColor3);
            }
        });
        colorPicker3.show();
    }

    public void monthlyAction(View view) {
        startActivity(new Intent(this, MainActivity.class));

    }

    public void weeklyAction(View view) {
        startActivity(new Intent(this, WeeklyViewActivity.class));

    }

    public void dailyAction(View view) {
        startActivity(new Intent(this, DailyCalendarActivity.class));

    }
}