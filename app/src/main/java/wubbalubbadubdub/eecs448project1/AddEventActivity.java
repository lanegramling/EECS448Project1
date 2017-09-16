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


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import wubbalubbadubdub.eecs448project1.data.Event;
import wubbalubbadubdub.eecs448project1.data.HelperMethods; //For toTime() method

/**
 * AddEventActivity.java
 * @author Dustin, Damian, Lane
 * @version 1.0
 * This Class allows the user to create an event and select timeslots for the event created
 */
public class AddEventActivity extends Activity {

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

        Intent intent = getIntent();
        currentUser = intent.getStringExtra("currentUser");

        TextView welcome = (TextView) findViewById(R.id.tvWelcome);
        welcome.setText(currentUser + ", create your event");
        selectedTimeslots = new ArrayList<>();

        createTimeslotTable();

        //Set Date Picker to current date
        int[] date = HelperMethods.getCurrentDate();
        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        int month = date[0];
        int day = date[1];
        int year = date[2];
        datePicker.updateDate(year, month - 1, day);




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
                            selected = false;
                        } else {
                            obj.setBackgroundColor(BLUE_MAT);
                            selectedTimeslots.add(id);
                            selected = true;
                        }
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

    private void updateTimeDisplay() {
        TextView timeDisplay = (TextView) findViewById(R.id.tvSelectedTimes);

        String disp = "Event Timeframe: " + HelperMethods.getTimeString(selectedTimeslots, format);

        timeDisplay.setText(disp);
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

    /**
     * @param v - view passed from tbTimeFormat's onClick event
     * @since 1.0
     */
    public void formatTimes(View v) {

        format = !format;

        //Loop through table and update the text on each button
        int count = 0;
        TableLayout layout = (TableLayout) findViewById(R.id.tbLayout);
        for (int i = 0; i < 4; i++) {
            TableRow tr = (TableRow)layout.getChildAt(i);
            for (int j = 0; j < 12; j++) {
                Button b = (Button)tr.getChildAt(j);
                b.setText(HelperMethods.toTime(count,format));
                count++;
            }
        }

    }

    boolean verify(Event e) {
        //conditions for false:
        // eventName not  within parameters
        // eventDate is a valid date
        //no timeSlots selected






        return (true); //will be changed later
    }

    /**
     * onButtonClick() - Handles Save button - creates event object, verifies, and adds event
     */
    void onButtonClick() {

        //Build date string for event
        DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
        int month = datePicker.getMonth() + 1;
        int day = datePicker.getDayOfMonth();
        int year = datePicker.getYear();
        String date = HelperMethods.dateToString(month, day, year);

        //get name of event
        EditText nameText = (EditText) findViewById(R.id.textName);
        String name = nameText.getText().toString();

        //Listify timeslots in int format for storage in db
        String timeslotIntList = HelperMethods.stringifyTimeslotInts(selectedTimeslots);

        //Create an event, attempt to verify it, and send to db if all is well
        Event e = new Event(-1, date, name, currentUser, timeslotIntList);

        if (verify(e)){

            String userDate = HelperMethods.dateToString(day, month, year);
            //to-do add user date info to database
        }
        else{
            //tell user there has been an error and let user fill out text boxes again
        }


    }


}
