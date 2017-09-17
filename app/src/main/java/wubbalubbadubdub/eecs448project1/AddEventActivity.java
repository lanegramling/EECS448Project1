package wubbalubbadubdub.eecs448project1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.graphics.Color;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;


import wubbalubbadubdub.eecs448project1.data.DatabaseHelper;
import wubbalubbadubdub.eecs448project1.data.Event;
import wubbalubbadubdub.eecs448project1.data.HelperMethods; //For toTime() method

/**
 * AddEventActivity.java
 * @author Dustin, Damian, Lane
 * @version 1.0
 * This Class allows the user to create an event and select timeslots for the event created
 */
public class AddEventActivity extends Activity {

    private DatabaseHelper dbHelper;
    private Toast statusMessage;

    private String currentUser;
    private List<Integer> selectedTimeslots;
    private boolean format = false; //Time format: false=12h | true=24h

    //Color Variables - Material Design
    int BLUE_MAT = Color.rgb(2,136,209);
    int GREEN_MAT = Color.rgb(139,195,74);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        dbHelper = new DatabaseHelper(getApplicationContext());
        statusMessage = Toast.makeText(this, "", Toast.LENGTH_SHORT);

        Intent intent = getIntent();
        currentUser = intent.getStringExtra("currentUser");

        TextView welcome = (TextView) findViewById(R.id.tvWelcome);
        welcome.setText(currentUser + ", create your event");
        selectedTimeslots = new ArrayList<>();

        createTimeslotTable();

        //Set Date Picker to current date, datePicker constraints etc.
        int[] date = HelperMethods.getCurrentDate();
        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        int month = date[0];
        int day = date[1];
        int year = date[2];

        //Now set the default date to today thru this init method I found
        datePicker.init(year, month, day, new OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker dp, int y, int m, int d) {
                //clearTimeslotTable(); ENABLE TO RESET TIMESLOTS UPON DATE SWITCH
            }
        });

        datePicker.setMinDate(System.currentTimeMillis() - 1000);
        Calendar max = Calendar.getInstance();
        max.set(Calendar.YEAR, 2100);

        datePicker.setMaxDate((max.getTime()).getTime());


    }

    private void createTimeslotTable() {
        TableLayout layout = (TableLayout) findViewById(R.id.tbLayout);

        int count = 0;
        for (int i = 0; i < 4; i++) {
            TableRow tr = new TableRow(this);
            for (int j = 0; j < 12; j++) {
                final int current = count;
                Button b = new Button(this);
                b.setText(HelperMethods.toTime(count,format));
                b.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                TableRow.LayoutParams cellParams = new TableRow.LayoutParams();
                cellParams.rightMargin = 5;
                b.setLayoutParams(cellParams);
                b.setBackgroundColor(GREEN_MAT);
                b.setOnClickListener(new Button.OnClickListener() {
                    int id = current;
                    boolean selected = false;

                    @Override
                    public void onClick(View v) {
                        Button obj = (Button) v;
                        if (selected) {
                            obj.setBackgroundColor(GREEN_MAT);
                            selectedTimeslots.remove(Integer.valueOf(id));
                        } else {
                            obj.setBackgroundColor(BLUE_MAT);
                            selectedTimeslots.add(id);
                        }
                        selected = !selected;
                        updateTimeDisplay();
                    }
                });
                tr.addView(b);
                count++;
            }
            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);

            tableRowParams.setMargins(10, 2, 10, 2);

            tr.setLayoutParams(tableRowParams);


            layout.addView(tr, tableRowParams);
        }
    }

    /**
     * clear timeslot table - originally wrote for onDateChangedListener, realized it's probably
     * unnecessary but keeping it in case we want it for anything else, e.g. a 'clear times' button
     */
    private void clearTimeslotTable() {

        selectedTimeslots.clear();

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tbLayout);
        int count = 0;
        for (int i = 0; i < 4; i++) {
            TableRow row = (TableRow)tableLayout.getChildAt(i);
            for (int j = 0; j < 12; j++) {
                Button b = (Button) row.getChildAt(j);
                b.setBackgroundColor(GREEN_MAT);
                count++;
            }
        }
        updateTimeDisplay();

    }

    public void toggleFormat(View v) {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.tbLayout);

        format = !format;

        int count = 0;
        for (int i = 0; i < 4; i++) {
            TableRow row = (TableRow)tableLayout.getChildAt(i);
            for (int j = 0; j < 12; j++) {
                Button b = (Button) row.getChildAt(j);
                b.setText(HelperMethods.toTime(count, format));
                count++;
            }
        }
        updateTimeDisplay();

    }

    private void updateTimeDisplay() {
        TextView timeDisplay = (TextView) findViewById(R.id.tvSelectedTimes);

        String disp = "Event Timeframe: " + HelperMethods.getTimeString(selectedTimeslots, format);

        timeDisplay.setText(disp);
    }

    boolean verify(Event e) {

        //Dates should have no way of being invalid

        //Name verification
        if (e.getName().isEmpty()) {
            statusMessage.setText("ERROR: Please name your event!");
            return false;
        } else if (!e.getName().matches("[a-zA-Z0-9\\s]+")) {  // TODO establish rules for event names. apostrophes? etc. (e.g. "Lane's b-day")
            statusMessage.setText("ERROR: Event name has invalid characters!");
            return false;
        }

        //Timeslot verification
        if (e.getTimeslots().isEmpty()) {
            statusMessage.setText("ERROR: Please choose times for your event!");
            return false;
        }

        //Check if user is already signed up for any conflicting events
        /*pseudo: if intersection of (currentuser.signups.timeslots) list with (e.timeslots) list
         * is nonempty, -> conflict found, return false*/

        return true;
    }

    /**
     * onButtonClick() - Handles Save button - creates event object, verifies, and adds event
     * @param v - (Given view)
     */
    public void onSaveButtonClick(View v) {

        //Build date string for event
        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        int month = datePicker.getMonth() + 1;
        int day = datePicker.getDayOfMonth();
        int year = datePicker.getYear();
        String date = HelperMethods.dateToString(month, day, year);

        //Get name of event
        EditText nameText = (EditText) findViewById(R.id.textName);
        String name = nameText.getText().toString();

        //Stringify timeslot list in int format for storage in db
        String timeslotIntList = HelperMethods.stringifyTimeslotInts(selectedTimeslots);

        //Create an event, attempt to verify it, and send to db if all is well
        /*Event ID is set to -1 because it's useless until a real ID is assigned
         *by the primary key upon insertion to the database after successful verification.*/
        Event e = new Event(-1, date, name, currentUser, timeslotIntList);

        if (verify(e)){

            dbHelper.addEvent(e);
            statusMessage.setText("Your event has been created.");
            statusMessage.show();
            Intent intent = new Intent(getApplicationContext(), ListActivity.class);
            intent.putExtra("currentUser", currentUser);
            finish();
            startActivity(intent);

        }else statusMessage.show();
    }


}
