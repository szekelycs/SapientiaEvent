package com.example.szekcsbobo.sapientiaevent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
import com.bumptech.glide.Glide;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Arrays;
import java.util.List;

public class EventOpenActivity extends AppCompatActivity {
    private TextView eventTitleTV, eventShortDescriptionTV, eventLongDescriptionTV;
    private ListView eventImageListView;


    private static final String TAG = "EOPENA";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_open);

        Event e = (Event) getIntent().getSerializableExtra("Event");
        eventTitleTV = (TextView) findViewById(R.id.event_title);
        eventShortDescriptionTV = (TextView) findViewById(R.id.event_short_description);
        eventLongDescriptionTV = (TextView) findViewById(R.id.event_long_description);

        eventTitleTV.setText(e.getEventTitle());
        eventShortDescriptionTV.setText(e.getEventShortDescription());
        eventLongDescriptionTV.setText(e.getEventLongDescription());

        eventImageListView = (ListView) findViewById(R.id.event_image_list);

        eventImageListView.setAdapter(new EventImageListAdapter(EventOpenActivity.this, e.getEventImages()));
    }

}
