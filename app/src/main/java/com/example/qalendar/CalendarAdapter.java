package com.example.qalendar;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;


    public CalendarAdapter(ArrayList<LocalDate> days, OnItemListener onItemListener) {
        this.days = days;
        this.onItemListener = onItemListener;
    }


    public CalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(parent.getContext());
        View view = inflator.inflate(R.layout.calendarcell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if(days.size() > 15) //month view
        {
            layoutParams.height = (int) (parent.getHeight() * 0.16666666666666);
        }
        else //week view
        {
            layoutParams.height = (int) (parent.getHeight());
        }

        return new CalendarViewHolder(view, onItemListener, days);
    }


    public void onBindViewHolder(CalendarViewHolder holder, int position) {
        final LocalDate date = days.get(position);
        holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
        if(date.equals(CalendarUtils.selectedDate))
        {
            holder.parentView.setBackgroundColor(Color.LTGRAY);
        }
        if(date.getMonth().equals(CalendarUtils.selectedDate.getMonth()))
        {
            holder.dayOfMonth.setTextColor(Color.BLACK);
        }
        else
        {
            holder.dayOfMonth.setTextColor(Color.LTGRAY);
        }
    }


    public int getItemCount() {
        return days.size();
    }

    public interface OnItemListener{
        void OnItemClick(int position, LocalDate date);
    }


}