package wubbalubbadubdub.eecs448project1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import wubbalubbadubdub.eecs448project1.data.DatabaseHelper;

public class ViewActivity extends Activity {

    DatabaseHelper dbHelper;

    private int currentID;
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Intent intent = getIntent();
        currentID = intent.getIntExtra("eventID", -1);
        currentUser = intent.getStringExtra("currentUser");

        dbHelper = new DatabaseHelper(getApplicationContext());


    }
}
