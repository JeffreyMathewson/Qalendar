import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTime
{
    public static void main(String[] args)
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
    }

    public static void FormatDT()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        Date date = new Date();
        System.out.println("Current Date " + dateFormat.format(date));
        // Convert Date to Calendar
        Calendar c = Calendar.getInstance();
        c.setTime(date);
    }

    public static void UpdateDT()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
        Calendar c = Calendar.getInstance();
        Date newDate = c.getTime();
        System.out.println("Updated Date " + dateFormat.format(newDate));
    }

    public static void ChangeMonth(int input)
    {
        Calendar c = Calendar.getInstance();
        // need button to track whether its a plus or a minus
        c.add(Calendar.MONTH, input);
    }
    public static void ChangeDay(int input)
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, input);
    }

    public static void ChangeYear(int input)
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, input);
    }
    public static void ChangeMinute(int input)
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, input);
    }

    public static void ChangeHour(int input)
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, input);
    }
}
