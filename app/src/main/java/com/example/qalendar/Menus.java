package com.example.qalendar;

import java.time.Month;
import java.util.Scanner;

public class Menus
{

    public static void EVTCreationMenu()
    {

    }

    public static int EVTEditMenu()
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("What event would you like to edit?");
        //add how to get the input and show how to get the events possible to appear
        System.out.print("What would you like to edit about the event?");
        // add input getter for either month, day, year, hour, or minute
        int input = 0;
        if (sc.next()  == "Month")
            input = 1;
        if (sc.next()  == "Day")
            input = 2;
        if (sc.next()  == "Year")
            input = 3;
        if (sc.next()  == "Hour")
            input = 4;
        if (sc.next()  == "Minute")
            input = 5;
        //while (menu.is.open() == true)
        {


        switch (input)
        {
            case 1:
            {
                /*
                if(button pressed -- up)
                DateTime.ChangeMonth(1);
                else if(button pressed == down)
                Date.Time.ChangeMonth(-1);
                break;
                */
            }
            case 2:
            {
                /*
                if(button pressed -- up)
                DateTime.ChangeDay(1);
                else if(button pressed == down)
                Date.Time.ChangeDay(-1);
                break;
                */
            }
            case 3:
            {
                /*
                if(button pressed -- up)
                DateTime.ChangeYear(1);
                else if(button pressed == down)
                Date.Time.ChangeYear(-1);
                break;
                */
            }
            case 4:
            {
                /*
                if(button pressed -- up)
                DateTime.ChangeHour(1);
                else if(button pressed == down)
                Date.Time.ChangeHour(-1);
                break;
                */
            }
            case 5:
            {
                /*
                if(button pressed -- up)
                DateTime.ChangeMinute(1);
                else if(button pressed == down)
                Date.Time.ChangeMinute(-1);
                break;
                */
            }
            }
        }
        return 0;
    }
}
