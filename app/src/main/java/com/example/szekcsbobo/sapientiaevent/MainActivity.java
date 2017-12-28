package com.example.szekcsbobo.sapientiaevent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Event> eventList = new ArrayList<>();
    private RecyclerView recyclerView;
    private EventAdapter mAdapter;
    private static final String TAG = "MAINA";

    private Button uploadButton;
    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uploadButton = (Button)findViewById(R.id.btnUploadMain);
        loginButton = (Button) findViewById(R.id.btnLoginMain);
        registerButton = (Button) findViewById(R.id.btnRegisterMain);

        recyclerView = (RecyclerView) findViewById(R.id.event_recycler_view);

        mAdapter = new EventAdapter(eventList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareEventData();

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UploadActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
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
