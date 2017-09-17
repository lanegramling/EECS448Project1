package wubbalubbadubdub.eecs448project1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Button;

import org.w3c.dom.Text;

import java.util.ArrayList;

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
    private String currentUser;
    private DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intent = getIntent();
        currentUser = intent.getStringExtra("currentUser");
        TextView welcomeText = (TextView) findViewById(R.id.tvWelcome);
        welcomeText.setText("Hello " + currentUser + "!");

        dbHelper = new DatabaseHelper(getApplicationContext());


        populateEventTable();

    }

    private void populateEventTable() {
        TableLayout layout = (TableLayout) findViewById(R.id.eventTableLayout);

        ArrayList<Event> events = dbHelper.getAllEvents();

        int numberOfEvents = events.size();

        for (int i = 0; i < numberOfEvents; i++) {
            TableRow row = new TableRow(this);

            Event workingEvent = events.get(i);

            TextView eventName = new TextView(this);
            TextView eventCreator = new TextView(this);
            TextView eventDay = new TextView(this);
            TextView eventTimeslots = new TextView(this);

            eventName.setText(workingEvent.getName());
            eventCreator.setText(workingEvent.getCreator());
            eventDay.setText(workingEvent.getDate());
            eventTimeslots.setText(workingEvent.getTimeslots());

            row.addView(eventName);
            row.addView(eventCreator);
            row.addView(eventDay);
            row.addView(eventTimeslots);

            layout.addView(row);

        }
    }

    public void newActivity(View v) {
        //Button addEvent = (Button) findViewById(R.id.addEvent);
        Intent intent = new Intent(this, AddEventActivity.class);
        intent.putExtra("currentUser", currentUser);

        startActivity(intent);
    }
}
