package com.example.szekcsbobo.sapientiaevent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class EventOpenActivity extends AppCompatActivity {
    private TextView eventTitleTV, eventShortDescriptionTV, eventLongDescriptionTV;
    private ImageView eventImagesTV;
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

        LinearLayout layout = (LinearLayout)findViewById(R.id.image_layout);


        for(String imPath : e.getEventImages()){
            ImageView image = new ImageView(this);
            image.setLayoutParams(new android.view.ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            image.setMaxHeight(20);
            image.setMaxWidth(20);

            // Adds the view to the layout
            layout.addView(image);
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(imPath);

            Glide.with(this)
                    .load(storageReference)
                    .into(image);
        }

    }

}
