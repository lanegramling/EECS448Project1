package wubbalubbadubdub.eecs448project1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import wubbalubbadubdub.eecs448project1.data.HelperMethods; //For toTime() method

/**
 * AddEventActivity.java
 * @author Dustin, Damian
 * @version 1.0
 * This Class allows the user to create an event and select timeslots for the event created
 */
public class AddEventActivity extends Activity {

    private String currentUser;
    private List<Integer> selectedTimeslots;
    private boolean timeType = false;

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
    }

    private void createTimeslotTable() {
        TableLayout layout = (TableLayout) findViewById(R.id.tbLayout);

        int count = 0;
        for (int i = 0; i < 4; i++) {
            TableRow tr = new TableRow(this);
            for (int j = 0; j < 12; j++) {
                final int current = count;
                Button b = new Button(this);
                b.setText(HelperMethods.toTime(count,timeType));
                b.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                TableRow.LayoutParams cellParams = new TableRow.LayoutParams();
                cellParams.rightMargin = 5;
                b.setLayoutParams(cellParams);
                b.setBackgroundColor(Color.RED);
                b.setOnClickListener(new Button.OnClickListener() {
                    int id = current;
                    boolean selected = false;

                    @Override
                    public void onClick(View v) {
                        Button obj = (Button) v;
                        if (selected) {
                            obj.setBackgroundColor(Color.RED);
                            selectedTimeslots.remove(Integer.valueOf(id));
                            selected = false;
                        } else {
                            obj.setBackgroundColor(Color.GREEN);
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
        String slots = "SELECTED TIMES: ";
        Collections.sort(selectedTimeslots);
        for(Integer slot : selectedTimeslots) {
            slots = slots + HelperMethods.toTime(slot, timeType) +",";
        }
        slots = slots.substring(0,slots.length() - 1);
        timeDisplay.setText(slots);
    }

    public void toggleTimeType(View v) {
        TableLayout tableLayout = (TableLayout) findViewById(R.id.tbLayout);

        timeType = !timeType;

        int count = 0;
        for (int i = 0; i < 4; i++) {
            TableRow row = (TableRow)tableLayout.getChildAt(i);
            for (int j = 0; j < 12; j++) {
                Button b = (Button) row.getChildAt(j);
                b.setText(HelperMethods.toTime(count, timeType));
                count++;
            }
        }
        updateTimeDisplay();

    }

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


}
