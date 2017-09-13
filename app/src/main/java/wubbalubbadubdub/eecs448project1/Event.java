package wubbalubbadubdub.eecs448project1;

import java.util.Date;

/**
 * Event.java
 * @author Damian
 * @version 1.0
 *
 * Event DataType for keeping track of events
 */
public class Event {

    private int id;
    private Date day;
    private String name;
    private String creator;
    private String timeslots;

    public Event(int inputID, Date inputDay, String inputName, String inputCreator, String inputTimeslots) {
        id = inputID;
        name = inputName;
        day = inputDay;
        creator = inputCreator;
        timeslots = inputTimeslots;
    }

    // We will probably not need setters.

    // Getters

    public int getID() {
        return id;
    }

    public Date getDay() {
        return day;
    }

    public String getName() {
        return name;
    }

    public String getCreator() {
        return creator;
    }

    public String getTimeslots() {
        return timeslots;
    }

}