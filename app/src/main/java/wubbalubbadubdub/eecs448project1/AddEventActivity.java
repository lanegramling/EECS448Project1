package wubbalubbadubdub.eecs448project1;

import android.app.Activity;
import android.os.Bundle;

/**
 * AddEventActivity.java
 * @author Dustin
 * @version 1.0
 * This Class allows the user to create an event and select timeslots for the event created
 */
public class AddEventActivity extends Activity {

    private String userName;

    boolean verify() {
        //conditions for false: eventName not  within parameters, eventDate isn't real
        //no timeSlots selected
        return (true); //will be changed later
    }
    void onButtonClick() {
        //will run verify function and check for trueness
        //if verift returns true, we can push the information given in the AddEventActivity class
        //to the database
        if (verify()){
            //add user info to database
        }
        else{
            //tell user there has been an error and let user fill out text boxes again
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
    }
}
