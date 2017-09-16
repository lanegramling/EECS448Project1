package wubbalubbadubdub.eecs448project1.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    public void addEvent(Event e) { // TODO create entry in event table for given event

    }

    /**
     * @return A sorted Vector of Events from the Database
     * @since 1.0
     */
    public Vector<Event> getAllEvents() { // TODO set sortedListOfEvents = all Events in db as objs
        Vector<Event> sortedListOfEvents = new Vector<Event>(); // Will be sorted through SQL

        return sortedListOfEvents;
    }

    /**
     * @param eventID ID of event in Table
     * @return String of timeslots for a given event ID
     */
    public String getTimeslots(int eventID) { // TODO return timeslots cell for a given eventID from db
        //Q: Will the timeslots need to be parsed into (contiguous-concatenated) time format here?


        return "";
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
