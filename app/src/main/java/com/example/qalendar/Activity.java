package com.example.qalendar;

import java.util.Scanner;
public class Activity
{

    Scanner sc = new Scanner(System.in); //setting up sc as a scanner to get user input
    double sTime,eTime,duration;
    void NewActivity(String input)
    {
        System.out.print("What activity would you like to create?");
        input = sc.nextLine(); // gets the string of input
        sTime =  GetEvtStart(); // see method for how the start time is set
        eTime = GetEvtEnd(); // see method for how the end time is set
        duration = GetEvtDuration(); // see method for how duration is set
        System.out.print(input + "has been created at " +sTime + "and will last for " + duration + " .");

    }
    double GetEvtDuration()
    {
        //need to get the start and end times
        sTime = GetEvtStart();
        eTime = GetEvtEnd();
        duration = eTime - sTime;
        if(duration < 24.00)
        {
            System.out.print("This event is way too long, you should shorten the time.");
        }
        return duration;

    }

    double GetEvtStart()
    {
        System.out.print("What time would you like this event to start?");
        sTime = sc.nextDouble();
        return sTime;
    }

    double GetEvtEnd()
    {
        System.out.print("When will this event be ending?");
        eTime = sc.nextDouble();
        return eTime;
    }

}