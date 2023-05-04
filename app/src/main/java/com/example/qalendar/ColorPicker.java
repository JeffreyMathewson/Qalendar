package com.example.qalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import yuku.ambilwarna.AmbilWarnaDialog;

public class ColorPicker extends AppCompatActivity {

    ConstraintLayout mLayout;

    int mDefaultColor1;
    int mDefaultColor2;
    int mDefaultColor3;
    private Button colorButton;
    Button mButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);
        colorButton = findViewById(R.id.colorButton);
        mLayout = (ConstraintLayout) findViewById(R.id.layout);

        mDefaultColor1 = ContextCompat.getColor(ColorPicker.this, com.google.android.material.R.color.design_default_color_primary); // R.color.colorPrimary.
        mDefaultColor2 = ContextCompat.getColor(ColorPicker.this, com.google.android.material.R.color.design_default_color_secondary); // R.color.colorSecondary.
        mDefaultColor3 = ContextCompat.getColor(ColorPicker.this, com.google.android.material.R.color.design_default_color_background); // R.color.colorBackground. There seems to be no colorTertiary.


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker(view);
            }
        });
    }



    public void openColorPicker(View view) {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, mDefaultColor1, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefaultColor1 = color;
                mLayout.setBackgroundColor(mDefaultColor1);
            }
        });

        colorPicker.show();
    }
}