package com.example.qalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import yuku.ambilwarna.AmbilWarnaDialog;

public class ColorPicker3 extends AppCompatActivity {

    // Is this layout going to work with all three?
    ConstraintLayout mLayout;

    Button mButton1;
    Button mButton2;
    Button mButton3;

    int mDefaultColor1;
    int mDefaultColor2;
    int mDefaultColor3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker3);

        mLayout = (ConstraintLayout) findViewById(R.id.layout);

        mDefaultColor1 = ContextCompat.getColor(ColorPicker3.this, com.google.android.material.R.color.design_default_color_primary); // R.color.colorPrimary
        mDefaultColor2 = ContextCompat.getColor(ColorPicker3.this, com.google.android.material.R.color.design_default_color_secondary); // R.color.colorSecondary

        mButton1 = (Button) findViewById(R.id.button1);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker1();
            }
        });

        mButton2 = (Button) findViewById(R.id.button2);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker2();
            }
        });
    }

    public void openColorPicker1() {
        AmbilWarnaDialog colorPicker1 = new AmbilWarnaDialog(this, mDefaultColor1, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefaultColor1 = color;
                mLayout.setBackgroundColor(mDefaultColor1);
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
                mLayout.setBackgroundColor(mDefaultColor2);
            }
        });
    }
}