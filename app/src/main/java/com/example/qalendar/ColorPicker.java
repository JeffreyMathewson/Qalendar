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
    Button mButton;

    // This is the default primary color.
    int mDefaultColor1;
    // This is the default secondary color.
    int mDefaultColor2;
    // This is the default tertiary color.
    int mDefaultColor3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);

        mLayout = (ConstraintLayout) findViewById(R.id.layout);

        mDefaultColor1 = ContextCompat.getColor(ColorPicker.this, com.google.android.material.R.color.design_default_color_primary); // R.color.colorPrimary
        mDefaultColor2 = ContextCompat.getColor(ColorPicker.this, com.google.android.material.R.color.design_default_color_secondary); // R.color.colorSecondary
        mDefaultColor3 = ContextCompat.getColor(ColorPicker.this, com.google.android.material.R.color.design_default_color_background); // There seems to be no R.color.colorTertiary, so I used color.colorBackground.

        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker();
            }
        });
    }

    public void openColorPicker() {
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