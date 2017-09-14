package wubbalubbadubdub.eecs448project1.data;

/**
 * Created by lane on 9/14/17.
 */

/**
 * HelperMethods.java
 * @author Lane
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

        return time;
    }

}
