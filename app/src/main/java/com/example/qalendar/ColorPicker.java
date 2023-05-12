package com.example.qalendar;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

interface ColorPickerListener {
    void onColorSelected(int color);
}

public class ColorPicker {
    private MainActivity activity;
    private int defaultColor;
    private ColorPickerListener listener;

    public ColorPicker(MainActivity activity, int defaultColor) {
        this.activity = activity;
        this.defaultColor = defaultColor;
    }

    public void setListener(ColorPickerListener listener) {
        this.listener = listener;
    }

    public void openColorPicker(String pickerTitle) {
        ColorPickerDialogBuilder
                .with(activity)
                .setTitle(pickerTitle)
                .initialColor(defaultColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setPositiveButton("Apply", (dialog, selectedColor, allColors) -> {
                    defaultColor = selectedColor;
                    if (listener != null) {
                        listener.onColorSelected(selectedColor);
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    // Handle cancel button action here
                })
                .build()
                .show();
    }
}
