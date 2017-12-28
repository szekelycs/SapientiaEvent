package com.example.szekcsbobo.sapientiaevent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Event> eventList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EventAdapter mAdapter;
    private static final String TAG = "MAINA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.event_recycler_view);
//        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.event_recycler_view);

        mAdapter = new EventAdapter(eventList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareEventData();
    }

    private void prepareEventData() {
        Log.d(TAG, "im here");
        Event event = new Event("Sapientia Golyabal", "Ballagnak a fiatalok s mennek a munkanelkulisegbe.");
        eventList.add(event);

        event = new Event("Szureti bal", "Mindenki leissza magat s a placcon verekednek az ertelmisegiek.");
        eventList.add(event);

        event = new Event("Sapis Filmmaraton", "Jobbnal jobb horror es egyeb filmek.");
        eventList.add(event);



        mAdapter.notifyDataSetChanged();
    }
}
