package wubbalubbadubdub.eecs448project1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private int selectedRow = -1;

    private Map<String, String> userSignups;

    private Toast statusMessage;

    private boolean prevSignup;

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

        statusMessage = Toast.makeText(this, "", Toast.LENGTH_SHORT);

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

        userSignups = dbHelper.getSignups(currentID);

        prevSignup = userSignups.containsKey(currentUser);

        if (currentUser.equals(currentEvent.getCreator())) {
            // View event status
            displayEventSignups();

            ((Button)findViewById(R.id.btnSave)).setVisibility(View.GONE);
        } else {
            // Set availability


            populateTimeslotTable();
        }

    }

    private void populateTimeslotTable() {
        TableLayout layout = (TableLayout) findViewById(R.id.tbLayout);

        List<Integer> currentUserSelection = (prevSignup) ? HelperMethods.listifyTimeslotInts(userSignups.get(currentUser)) : null;

        if (prevSignup) selectedTimeslots = currentUserSelection;

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
                    boolean intSelect = false;
                    if (currentUserSelection != null && currentUserSelection.contains(Integer.valueOf(count))) {
                        intSelect = true;
                        b.setBackgroundColor(BLUE_MAT);
                    } else {
                        b.setBackgroundColor(GREEN_MAT);
                    }
                    final boolean select = intSelect;

                    b.setOnClickListener(new Button.OnClickListener() {
                        int id = current;
                        boolean selected = select;

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
        updateTimeDisplay();
    }

    private void displayEventSignups() {
        TableLayout layout = (TableLayout) findViewById(R.id.tbLayout);

        TableRow header = new TableRow(this);


        TableRow.LayoutParams cellParams = new TableRow.LayoutParams();
        cellParams.setMargins(10, 5, 10, 5);

        TextView userHeader = new TextView(this);
        userHeader.setText("User");
        userHeader.setTextSize(15);
        userHeader.setTypeface(null, Typeface.BOLD);
        userHeader.setLayoutParams(cellParams);
        header.addView(userHeader);

        for (int slot : currentTimeslots) {
            TextView slotHeader = new TextView(this);
            slotHeader.setText(HelperMethods.toTime(slot, format));
            slotHeader.setTextSize(15);
            slotHeader.setTypeface(null, Typeface.BOLD);
            slotHeader.setLayoutParams(cellParams);
            header.addView(slotHeader);
        }

        header.setBackgroundColor(Color.GRAY);

        layout.addView(header);

        for (Map.Entry<String, String> entry : userSignups.entrySet()) {
            TableRow signupRow = new TableRow(this);

            TextView username = new TextView(this);
            username.setPadding(10, 5, 10, 5);
            username.setText(entry.getKey());
            signupRow.addView(username);
            List<Integer> slots = HelperMethods.listifyTimeslotInts(entry.getValue());

            for (int slot : currentTimeslots) {
                TextView avail = new TextView(this);

                if (slots.contains(Integer.valueOf(slot))) {
                    // User is signed up for this
                    avail.setText("AVAILABLE");
                    avail.setBackgroundColor(GREEN_MAT);
                } else {
                    avail.setText("NOT AVAILABLE");
                    avail.setBackgroundColor(Color.rgb(239, 83, 80));
                }
                avail.setPadding(10, 5, 10, 5);

                signupRow.addView(avail);
            }

            layout.addView(signupRow);

        }
    }

    public void saveSelection(View v) {
        if (prevSignup) {
            // User has signed up previously, so call the update method
            if (dbHelper.updateSignup(currentID, currentUser, selectedTimeslots) > 0) {
                statusMessage.setText("Successfully saved your availability");
            } else {
                statusMessage.setText("Something went wrong");
            }
        } else {
            // User has not signed up before, so call the insert method
            if (dbHelper.addSignup(currentID,currentUser,selectedTimeslots) != -1) {
                statusMessage.setText("Successfully saved your availability");
            } else {
                statusMessage.setText("Somethign went wrong");
            }
        }
        statusMessage.show();
    }

    private void updateTimeDisplay() {
        TextView timeDisplay = (TextView) findViewById(R.id.tvSelectedTimes);

        String disp = "Your Selected Availability: " + HelperMethods.getTimeString(selectedTimeslots, format);

        timeDisplay.setText(disp);
    }
}
