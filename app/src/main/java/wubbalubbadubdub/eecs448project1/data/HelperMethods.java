package wubbalubbadubdub.eecs448project1.data;

import android.provider.CalendarContract;

import java.util.Calendar;

/**
 * HelperMethods.java
 * @author Lane, Damian
 * @version 1.0
 * Contains methods used to assist functionality of activities
 */
public class HelperMethods {

    private HelperMethods(){}

    /**
     * @param timeslot - int from 0-47, represents the 48 30min timeslots from 00:00-23:30
     * @param format - True: 24h format | False: 12h format
     * @return - A time formatted in either 12h or 24h format.
     * @since 1.0
     */
    public static String toTime(int timeslot, boolean format) {

        int hour;
        String min;

        //Convert 0-47 integer format to 12h or 24h format
        if (format) hour = timeslot / 2;
        else hour = (timeslot >= 26) ? ((timeslot - 24) / 2) : (timeslot / 2);
        min = ((timeslot % 2) == 0) ? "00" : "30";
        if (!format && (hour == 0)) hour = 12; //Special case: 0(int)->12:00(12h)

        String time = hour + ":" + min; //Build time string
        time = (format && (hour < 10)) ? ("0" + time) : time; //Add leading zero if needed in 24h format
        if (!format) time = (timeslot < 24) ? time + "AM" : time + "PM"; // AM/PM for 12h format

        return time;
    }


    /**
     * @param month - month of a given date
     * @param day - day of a given date
     * @param year - year of a given date
     * @return a string in MM/DD/YYYY format
     * @since 1.0
     */
    public static String dateToString(int month, int day, int year)
    {return (month + "/" + day + "/" + year);}

    /**
     * @return a string in MM/DD/YYYY format of the current year
     * @since 1.0
     */
    public static String getCurrentDate()
    {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);
        return dateToString(month, day, year);
    }

}
