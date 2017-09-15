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
import java.util.List;

import wubbalubbadubdub.eecs448project1.data.HelperMethods; //For toTime() method

/**
 * AddEventActivity.java
 * @author Dustin, Damian, Lane
 * @version 1.0
 * This Class allows the user to create an event and select timeslots for the event created
 */
public class AddEventActivity extends Activity {

    private String currentUser;
    private List<Integer> timeslots;
    private List<Integer> selectedTimeslots;
    private boolean format = false; //Time format: false=12h | true=24h

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        Intent intent = getIntent();
        currentUser = intent.getStringExtra("currentUser");

        TextView welcome = (TextView) findViewById(R.id.tvWelcome);
        welcome.setText(currentUser + ", create your event");
        selectedTimeslots = new ArrayList<>();
        timeslots = new ArrayList<>();

        for (int i = 0; i < 48; i++) timeslots.add(i); //(Temporary(?), add all timeslots as available)

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
                b.setText(HelperMethods.toTime(count,format));
                b.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                if (timeslots.contains(count)) {
                    b.setBackgroundColor(Color.GREEN);
                    b.setOnClickListener(new Button.OnClickListener() {
                        int id = current;

                        @Override
                        public void onClick(View v) {
                            Button obj = (Button) v;
                            if (((ColorDrawable)obj.getBackground()).getColor() == Color.GREEN) {
                                obj.setBackgroundColor(Color.BLUE);
                                selectedTimeslots.add(id);
                            } else {
                                obj.setBackgroundColor(Color.GREEN);
                                selectedTimeslots.remove(Integer.valueOf(id));
                            }
                        }
                    });
                } else {
                    b.setBackgroundColor(Color.DKGRAY);
                }
                tr.addView(b);
                count++;
            }
            layout.addView(tr);
        }
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

    boolean verify() {
        //conditions for false: eventName not  within parameters, eventDate isn't real
        //no timeSlots selected





        return (true); //will be changed later
    }
    void onButtonClick() {
        //will run verify function and check for trueness
        //if verify returns true, we can push the information given in the AddEventActivity class
        //to the database
        if (verify()){
            //add user info to database
        }
        else{
            //tell user there has been an error and let user fill out text boxes again
        }


    }


}
