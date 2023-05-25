package com.example.qalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import yuku.ambilwarna.AmbilWarnaDialog;

//import com.github.QuadFlask.colorpicker.ColorPickerDialog;
//import com.github.QuadFlask.colorpicker.OnColorSelectedListener;

//interface ColorPickerListener {
//    void onColorSelected(int color);
//}

public class ColorPicker3 extends AppCompatActivity {

    ConstraintLayout mLayout1;

    int mDefaultColor1;
    int mDefaultColor2;
    int mDefaultColor3;

    //private ColorPickerListener listener2;


    // Trigger the method, providing the desired color value as an argument.
        // For example, you could call this method when a button is clicked or based on some other user interaction.
    public void changeBackgroundColors(int color) {
        Intent intent2 = new Intent(this, WeeklyViewActivity.class);
        Intent intent3 = new Intent(this, DailyCalendarActivity.class);
        Intent intent1 = new Intent(this, MainActivity.class);

        intent2.putExtra("BACKGROUND_COLOR", color);
        intent3.putExtra("BACKGROUND_COLOR", color);
        intent1.putExtra("BACKGROUND_COLOR", color);

        startActivity(intent2);
        startActivity(intent3);
        startActivity(intent1);



        // Storing the background color value
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("backgroundColor", color); // Assuming selectedColor is the chosen background color
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker3);

        mLayout1 = findViewById(R.id.layout);

        // Retrieving and applying the background color value
        SharedPreferences sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        int backgroundColor = sharedPref.getInt("backgroundColor", Color.WHITE); // Assuming defaultColor is a fallback color value
        findViewById(R.id.layout).setBackgroundColor(backgroundColor); // Assuming R.id.layout is the root layout of the activity




        mDefaultColor1 = ContextCompat.getColor(ColorPicker3.this, com.google.android.material.R.color.design_default_color_primary); // R.color.colorPrimary
        mDefaultColor2 = ContextCompat.getColor(ColorPicker3.this, com.google.android.material.R.color.design_default_color_secondary); // R.color.colorSecondary
        mDefaultColor3 = ContextCompat.getColor(ColorPicker3.this, com.google.android.material.R.color.design_default_color_background); // R.color.colorBackground

        Button mButton1 = findViewById(R.id.button1);
        mButton1.setOnClickListener(view -> openColorPicker1());

        Button mButton2 = findViewById(R.id.button2);
        mButton2.setOnClickListener(view -> openColorPicker2());

        Button mButton3 = findViewById(R.id.button3);
        mButton3.setOnClickListener(view -> openColorPicker3());


//        Button weeklyButton = findViewById(R.id.weeklyButton);
    }

//    public void setListener(ColorPickerListener listener2) { this.listener2 = listener2; }

//    public void openColorPicker2(String pickerTitle)
//    {
//        ColorPickerDialogBuilder
//                .with(this)
//                .setTitle(pickerTitle)
//                .initialColor(mDefaultColor2)
//                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
//                .density(12)
//                .setPositiveButton("Apply", (dialog, selectedColor, allColors) -> {
//                    // Do something with the selected color
//                    mDefaultColor2 = selectedColor;
//                    if (listener2 != null)
//                    {
//                        listener2.onColorSelected(selectedColor);
//                    }
//                })
//                .setNegativeButton("Cancel", (dialog, which) -> {
//                    // Do something when the user cancels the dialog
//                })
//                .build()
//                .show();
//    }

    public void openColorPicker1() {
        AmbilWarnaDialog colorPicker1 = new AmbilWarnaDialog(this, mDefaultColor1, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefaultColor1 = color;
                mLayout1.setBackgroundColor(mDefaultColor1);
                changeBackgroundColors(color);
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
                mLayout1.setOutlineSpotShadowColor(mDefaultColor3);
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