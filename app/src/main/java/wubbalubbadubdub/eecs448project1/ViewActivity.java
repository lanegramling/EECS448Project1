package wubbalubbadubdub.eecs448project1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wubbalubbadubdub.eecs448project1.data.DatabaseHelper;
import wubbalubbadubdub.eecs448project1.data.Event;
import wubbalubbadubdub.eecs448project1.data.HelperMethods;

public class ViewActivity extends Activity {

    DatabaseHelper dbHelper;

    Boolean format = false;

    private int currentID;
    private String currentUser;
    private Event currentEvent;

    private List<Integer> currentTimeslots;
    private List<Integer> selectedTimeslots;

    //Color Variables - Material Design
    int BLUE_MAT = Color.rgb(2,136,209);
    int GREEN_MAT = Color.rgb(139,195,74);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Intent intent = getIntent();
        currentID = intent.getIntExtra("eventID", -1);
        currentUser = intent.getStringExtra("currentUser");

        dbHelper = new DatabaseHelper(getApplicationContext());

        currentEvent = dbHelper.getEvent(currentID);

        String creatorString = "Created by: " + currentEvent.getCreator();

        TextView eventName = (TextView) findViewById(R.id.tvEventName);
        TextView eventCreator = (TextView) findViewById(R.id.tvCreator);
        TextView eventDate = (TextView) findViewById(R.id.tvDate);

        eventName.setText(currentEvent.getName());
        eventCreator.setText(creatorString);
        eventDate.setText(currentEvent.getDate());

        currentTimeslots = HelperMethods.listifyTimeslotInts(currentEvent.getTimeslots());
        selectedTimeslots = new ArrayList<>();

        if (currentUser.equals(currentEvent.getCreator())) {
            // View event status
            displayEventSignups();
        } else {
            // Set availability

            populateTimeslotTable();
        }

    }

    private void populateTimeslotTable() {
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
                if (currentTimeslots.contains(count)) {
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
                } else {
                    b.setBackgroundColor(Color.DKGRAY);
                }
                tr.addView(b);
                count++;
            }
            TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);

            tableRowParams.setMargins(10, 2, 10, 2);

            tr.setLayoutParams(tableRowParams);


            layout.addView(tr, tableRowParams);
        }
    }

    private void displayEventSignups() {

    }

    private void updateTimeDisplay() {
        TextView timeDisplay = (TextView) findViewById(R.id.tvSelectedTimes);

        String disp = "Your Selected Availability: " + HelperMethods.getTimeString(selectedTimeslots, format);

        timeDisplay.setText(disp);
    }
}
