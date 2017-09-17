package wubbalubbadubdub.eecs448project1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wubbalubbadubdub.eecs448project1.data.DatabaseHelper;
import wubbalubbadubdub.eecs448project1.data.Event;

/**
 * This page list all the activities created by other users.
 * Also this page allows the user to add an event which brings them to the AddEvent page
 * @author Dustin
 * @version 1.0
 *
 */

public class ListActivity extends Activity {

    private DatabaseHelper dbHelper;
    private String currentUser;
    private ListView eventList;

    private boolean allEvents = true; //Determines whether to show all events or own events



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        dbHelper = new DatabaseHelper(getApplicationContext());
        eventList = (ListView) findViewById(R.id.lvEvents);

        Intent intent = getIntent();
        currentUser = intent.getStringExtra("currentUser");
        TextView welcomeText = (TextView) findViewById(R.id.tvWelcome);
        welcomeText.setText("Hello " + currentUser + "!");

        //Populate list of events
        populateEvents();

    }

    private void populateEvents() {
        List<Event> events = (allEvents) ? dbHelper.getAllEvents() : dbHelper.getUserEvents(currentUser);
        List<String> eventTitles = new ArrayList<>(); //Titles of events will be shown //TODO subItems to show dates?

        for (Event e : events) eventTitles.add(e.getName());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                eventTitles);

        eventList.setAdapter(arrayAdapter);
    }

    public void toggleEventList() {
        allEvents = !allEvents;
        populateEvents();
    }

    /**
     * Handles Add new Event button
     * @param v - (given view)
     */
    public void newEvent(View v) {
        Intent intent = new Intent(this, AddEventActivity.class);
        intent.putExtra("currentUser", currentUser);

        startActivity(intent);
    }


}
