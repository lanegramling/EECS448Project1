package wubbalubbadubdub.eecs448project1.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * DatabaseHelper.java
 * @author Damian
 * @version 1.0
 * This class contains helper methods that interact with the Database. This replaced the Dataclass
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, DBContract.DATABASE_NAME, null, DBContract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create all tables
        db.execSQL(DBContract.UserTable.CREATE_TABLE);
        db.execSQL(DBContract.EventTable.CREATE_TABLE);
        db.execSQL(DBContract.SignupTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Delete all tables
        db.execSQL(DBContract.UserTable.DROP_TABLE);
        db.execSQL(DBContract.EventTable.DROP_TABLE);
        db.execSQL(DBContract.SignupTable.DROP_TABLE);
        onCreate(db);
    }

    //region User Table Methods

    /**
     *
     * @param name the name of the new user to add
     * @return -1 for failure, otherwise will return the row inserted at.
     */
    public long addUser(String name) {
        SQLiteDatabase db = this.getWritableDatabase(); // is this okay?

        ContentValues values = new ContentValues();
        values.put(DBContract.UserTable.COLUMN_NAME_NAME, name);

        return db.insert(DBContract.UserTable.TABLE_NAME, null, values);
    }

    /**
     * This method queries our User table for all Usernames
     * @return List of strings containing all users
     */
    public List<String> getUsers() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Even though we only get one column, SQLiteDatabase.query() requires a string array
        String[] columns = {
                DBContract.UserTable.COLUMN_NAME_NAME
        };
        String sortOrder = DBContract.UserTable.COLUMN_NAME_NAME + " COLLATE NOCASE ASC";

        Cursor query = db.query(
                DBContract.UserTable.TABLE_NAME,
                columns,
                null, null, null, null,
                sortOrder
        );

        List<String> names = new ArrayList<>();
        while (query.moveToNext()) {
            String name = query.getString(query.getColumnIndexOrThrow(DBContract.UserTable.COLUMN_NAME_NAME));
            names.add(name);
        }

        return names;
    }

    //endregion



    //region Event Table Methods

    /**
     * @param e - Event object passed when the save button is clicked with valid event params
     * @since 1.0
     */
    public long addEvent(Event e) {

        SQLiteDatabase db = this.getWritableDatabase(); // is this okay?
        ContentValues values = new ContentValues();

        values.put(DBContract.EventTable.COLUMN_NAME_TITLE, e.getName());
        values.put(DBContract.EventTable.COLUMN_NAME_TIMESLOTS, e.getTimeslots());
        values.put(DBContract.EventTable.COLUMN_NAME_CREATOR, e.getCreator());
        values.put(DBContract.EventTable.COLUMN_NAME_DAY, e.getDate());

        return db.insert(DBContract.EventTable.TABLE_NAME, null, values);
    }

    /**
     * @return A sorted ArrayList of Events from the Database
     * @since 1.0
     */
    public ArrayList<Event> getAllEvents() {

        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Event> sortedListOfEvents = new ArrayList<>(); // Will be sorted through SQL

        String[] columns = {
                DBContract.EventTable._ID,
                DBContract.EventTable.COLUMN_NAME_TITLE,
                DBContract.EventTable.COLUMN_NAME_TIMESLOTS,
                DBContract.EventTable.COLUMN_NAME_CREATOR,
                DBContract.EventTable.COLUMN_NAME_DAY
        };

        //Sort by day for now. Could hypothetically get weird when multiple years are involved
        String sortOrder = DBContract.EventTable.COLUMN_NAME_DAY + " COLLATE NOCASE ASC";

        Cursor query = db.query(
                DBContract.EventTable.TABLE_NAME,
                columns,
                null, null, null, null,
                sortOrder
        );

        //Populate event vector
        while (query.moveToNext()) {

            int id;
            String title, timeslots, creator, day;

            id = Integer.parseInt(query.getString(query.getColumnIndexOrThrow(DBContract.EventTable._ID)));
            title = query.getString(query.getColumnIndexOrThrow(DBContract.EventTable.COLUMN_NAME_TITLE));
            timeslots = query.getString(query.getColumnIndexOrThrow(DBContract.EventTable.COLUMN_NAME_TIMESLOTS));
            creator = query.getString(query.getColumnIndexOrThrow(DBContract.EventTable.COLUMN_NAME_CREATOR));
            day = query.getString(query.getColumnIndexOrThrow(DBContract.EventTable.COLUMN_NAME_DAY));

            //Create Event object from row and add to Vector
            Event e = new Event(id, day, title, creator, timeslots); // LOL This stuff was in the wrong order... Come on guys...
            sortedListOfEvents.add(e);
        }

        return sortedListOfEvents;
    }

    /**
     * Get events from given user.
     * @param user - Username to retrieve events for.
     * @return Sorted Event vector of a given user's created events
     */
    public ArrayList<Event> getUserEvents(String user) {
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Event> sortedListOfEvents = new ArrayList<>(); // Will be sorted through SQL

        String[] userArr = {user}; //(Needs to be in an array to use as a WHERE argument)

        String[] columns = {
                DBContract.EventTable._ID,
                DBContract.EventTable.COLUMN_NAME_TITLE,
                DBContract.EventTable.COLUMN_NAME_TIMESLOTS,
                DBContract.EventTable.COLUMN_NAME_CREATOR,
                DBContract.EventTable.COLUMN_NAME_DAY
        };

        //Sort by day for now. Could hypothetically get weird when multiple years are involved
        String sortOrder = DBContract.EventTable.COLUMN_NAME_DAY + " COLLATE NOCASE ASC";

        Cursor query = db.query(
                DBContract.EventTable.TABLE_NAME,
                columns,
                "creator = ?", userArr, null, null,
                sortOrder
        );

        //Populate event List
        while (query.moveToNext()) {

            int id;
            String title, timeslots, creator, day;

            id = Integer.parseInt(query.getString(query.getColumnIndexOrThrow(DBContract.EventTable._ID)));
            title = query.getString(query.getColumnIndexOrThrow(DBContract.EventTable.COLUMN_NAME_TITLE));
            timeslots = query.getString(query.getColumnIndexOrThrow(DBContract.EventTable.COLUMN_NAME_TIMESLOTS));
            creator = query.getString(query.getColumnIndexOrThrow(DBContract.EventTable.COLUMN_NAME_CREATOR));
            day = query.getString(query.getColumnIndexOrThrow(DBContract.EventTable.COLUMN_NAME_DAY));

            //Create Event object from row and add to Vector
            Event e = new Event(id, day, title, creator, timeslots);
            sortedListOfEvents.add(e);
        }

        return sortedListOfEvents;
    }

    /**
     * @param eventID ID of event in Table
     * @return String of timeslots for a given event ID
     */
    public String getTimeslots(int eventID) { // TODO return timeslots cell for a given eventID from db

        SQLiteDatabase db = this.getReadableDatabase();

        String requestedTimeslots;
        String eidAsStr = String.valueOf(eventID);
        String[] eventIDArr = {eidAsStr}; //(Needs to be in an array to use as a WHERE argument)

        String[] columns = {
                DBContract.EventTable._ID,
                DBContract.EventTable.COLUMN_NAME_TIMESLOTS,
        };

        Cursor query = db.query(
                DBContract.EventTable.TABLE_NAME,
                columns,
                "timeslots = ?", eventIDArr, null, null,
                null
        );

        requestedTimeslots = query.getString(query.getColumnIndexOrThrow(DBContract.EventTable.COLUMN_NAME_TIMESLOTS));
        return requestedTimeslots;
    }

    //endregion



    //region Signup Table Methods

    public void addSignup(int eventID, String user) {// TODO create entry in signups table
        //Q: Should we pass an eventID or an Event object?

    }

    public List<String> getSignups(int eventID) { // TODO return list of signed up users(?) for given event
        //Q: Are we returning a list of users or something else?
        //Q: Should we pass an eventID or an Event object?
        List<String> dummyreturn = new ArrayList<>();

        return dummyreturn;
    }



    //endregion
}
