package wubbalubbadubdub.eecs448project1;

import java.util.Vector;

/**
 * Dataclass.java
 * @author Damian
 * @version 1.0
 *
 * This class provides an interface between activity classes and the Database Helper Class
 */
public class Dataclass {

    /**
     *
     * @return A sorted Vector of Events from the Database
     * @since 1.0
     */
    public static Vector<Event> getAllEvents() {
        Vector<Event> sortedListOfEvents = new Vector<Event>(); // Will be sorted through SQL

        return sortedListOfEvents;
    }

    /**
     *
     * @param eventID ID of event in Table
     * @return String of timeslots for a given event ID
     */
    public static String getTimeslots(int eventID) {

        return "";
    }

}
