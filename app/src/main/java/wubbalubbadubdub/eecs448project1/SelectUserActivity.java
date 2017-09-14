package wubbalubbadubdub.eecs448project1;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import wubbalubbadubdub.eecs448project1.data.DatabaseHelper;


public class SelectUserActivity extends Activity {
    private DatabaseHelper dbHelper;
    private Toast statusMessage;

    /**
     * This function is called when the activity is first created
     * @param savedInstanceState Every oncreate needs this. Allows to revert to previous state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        dbHelper = new DatabaseHelper(getApplicationContext());
        statusMessage = Toast.makeText(this, "", Toast.LENGTH_SHORT);
    }

    /**
     * This function will utilize the DatabaseHelper to add a new user
     * @param v The View that fired the addUser() function. In this case it is the addUserButton
     */
    public void addUser(View v) {
        EditText textbox = (EditText) findViewById(R.id.newUsername);
        String name = textbox.getText().toString();

        if (TextUtils.isEmpty(name)) {
            statusMessage.setText("ERROR: Please input a name for the new user");
        } else if (!isValidName(name)) {
            statusMessage.setText("ERROR: Name contains invalid characters");
        } else if (dbHelper.addUser(name) == -1) { // -1 when failed sql insert
            statusMessage.setText("ERROR: That Username already exists");
        } else {
            statusMessage.setText(name + " was added to the list of users");
        }
        statusMessage.show();
    }

    /**
     *
     * @param name the string to check the validity of
     * @return false if the name contains invalid characters
     */
    private boolean isValidName(String name) {
        // TODO Change this to actual conditions later
        if (TextUtils.isEmpty(name)) {
            return false;
        } else {
            return true;
        }
    }
}
