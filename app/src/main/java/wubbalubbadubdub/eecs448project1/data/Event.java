package wubbalubbadubdub.eecs448project1.data;


/**
 * Event.java
 * @author Damian, Lane
 * @version 1.0
 *
 * Event DataType for keeping track of events
 */
public class Event implements Comparable<Event> {

    private int id;
    private String date;
    private String name;
    private String creator;
    private String timeslots;

    public Event(int inputID, String inputDate, String inputName, String inputCreator, String inputTimeslots) {
        id = inputID;
        name = inputName;
        date = inputDate;
        creator = inputCreator;
        timeslots = inputTimeslots;
    }

    public int compareTo(Event otherEvent) {
        int[] currentDate = HelperMethods.getMonthDayYear(date);
        int[] otherDate = HelperMethods.getMonthDayYear(otherEvent.getDate());

        if (currentDate[2] == otherDate[2]) {
            if (currentDate[0] == otherDate[0]) {
                return currentDate[1] - otherDate[1];
            } else {
                return currentDate[0] - otherDate[0];
            }
        } else {
            return currentDate[2] - otherDate[2];
        }
    }

    // We will probably not need setters.

    // Getters

    public int getID() { return id; }

    public String getDate() {
        return date;
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