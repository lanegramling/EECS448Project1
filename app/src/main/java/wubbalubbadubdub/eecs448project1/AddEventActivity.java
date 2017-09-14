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

/**
 * AddEventActivity.java
 * @author Dustin, Damian
 * @version 1.0
 * This Class allows the user to create an event and select timeslots for the event created
 */
public class AddEventActivity extends Activity {

    private String currentUser;
    private List<Integer> timeslots;
    private List<Integer> selectedTimeslots;

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
        timeslots.add(1);
        timeslots.add(5);
        timeslots.add(10);

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
                b.setText(Integer.toString(count)); // TODO Use Lane's method here
                b.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
                if (timeslots.contains(count)) {
                    b.setBackgroundColor(Color.RED);
                    b.setOnClickListener(new Button.OnClickListener() {
                        int id = current;

                        @Override
                        public void onClick(View v) {
                            Button obj = (Button) v;
                            if (((ColorDrawable)obj.getBackground()).getColor() == Color.RED) {
                                obj.setBackgroundColor(Color.GREEN);
                                selectedTimeslots.add(id);
                            } else {
                                obj.setBackgroundColor(Color.RED);
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
