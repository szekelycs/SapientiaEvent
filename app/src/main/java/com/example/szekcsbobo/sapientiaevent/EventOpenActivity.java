package com.example.szekcsbobo.sapientiaevent;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class EventOpenActivity extends AppCompatActivity {
    private TextView eventTitleTV, eventShortDescriptionTV, eventLongDescriptionTV;
    private static final String TAG = "EOPENA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_open);
        Event e = (Event) getIntent().getSerializableExtra("Event");
        eventTitleTV = (TextView) findViewById(R.id.event_title);
        eventShortDescriptionTV = (TextView) findViewById(R.id.event_short_description);
        eventLongDescriptionTV = (TextView) findViewById(R.id.event_long_description);
        Log.d(TAG, e.getEventTitle());
        eventTitleTV.setText(e.getEventTitle());
        eventShortDescriptionTV.setText(e.getEventShortDescription());
        eventLongDescriptionTV.setText(e.getEventLongDescription());
    }

}
