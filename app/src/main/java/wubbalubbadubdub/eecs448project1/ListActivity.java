package wubbalubbadubdub.eecs448project1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ListActivity extends Activity {
    private String currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Intent intent = getIntent();
        currentUser = intent.getStringExtra("currentUser");
        TextView welcomeText = (TextView) findViewById(R.id.tvWelcome);
        welcomeText.setText("Hello " + currentUser + "!");
    }

    public void newActivity(View v) {
        Intent intent = new Intent(this, AddEventActivity.class);
        intent.putExtra("currentUser", currentUser);

        startActivity(intent);
    }
}
