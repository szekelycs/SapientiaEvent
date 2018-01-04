package com.example.szekcsbobo.sapientiaevent;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
//".write": "auth != null"
/**
 * MainActivity
 *
 * <b>MainActivity </b> component launches when the application starts. It contains the main view of the application.
 *
 * @author Gagyi Zalan; Szekely Csongor - 04/01/2018
 */

public class MainActivity extends AppCompatActivity {

    /**
     * @variables - here are the variables which are necessary for the layout - RecyclerView is used for the event handling and
     *  the Button type variables are used for the inside navigation (authentication and event upload)
     */
    private List<Event> eventList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewEventAdapter mAdapter;

    private Button uploadButton;
    private Button loginButton;
    private Button registerButton;
    private Button logoutButton;

    /**
     * @TAG: debug tag variable
     */

    private static final String TAG = "MAINA";

    /**
     * @variables - used for firebase storage and database operations
     */

    private FirebaseAuth mAuth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference rootRef = database.getReference();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();


    /**
     * The launcher lifecycle method of the main activity - here are initialized the view elements (with clicklisteners)
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uploadButton = (Button)findViewById(R.id.btnUploadMain);
        registerButton = (Button) findViewById(R.id.btnRegisterMain);
        loginButton = (Button) findViewById(R.id.btnLoginMain);
        logoutButton = (Button) findViewById(R.id.btnLogoutMain);

        /**
         * The login and register buttons are only visible when the user is offline - the logout and upload buttons
         * are available for online users
         */
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            uploadButton.setVisibility(View.GONE);
            registerButton.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.VISIBLE);
            logoutButton.setVisibility(View.GONE);
        }
        else{
            uploadButton.setVisibility(View.VISIBLE);
            registerButton.setVisibility(View.GONE);
            loginButton.setVisibility(View.GONE);
            logoutButton.setVisibility(View.VISIBLE);
        }

        /**
         * RecyclerView initialization and connection to an adapter
         */

        recyclerView = (RecyclerView) findViewById(R.id.event_recycler_view);

        mAdapter = new RecyclerViewEventAdapter(eventList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        /**
         * method for getting the dataset which is shown in the RecyclerVIew
         */
        prepareEventData();

        /**
         * handling Button OnClicks
         * Each Button starts a new activity except the Logout Button because the Logout action is handled here,
         * in the MainActivity class
         */

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,InsertActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        /**
         * AlertDialog for logout confirmation - cancellable
         * After logout the buttons visibility is changed
         */
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(MainActivity.this);
                dlgAlert.setMessage("Are you sure?");
                dlgAlert.setTitle("Logout");
                dlgAlert.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                uploadButton.setVisibility(View.GONE);
                                registerButton.setVisibility(View.VISIBLE);
                                loginButton.setVisibility(View.VISIBLE);
                                logoutButton.setVisibility(View.GONE);
                            }
                        });
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }
        });
    }

    /**
     * Method for filling the eventList ArrayList with values
     *
     *
     */
    private void prepareEventData() {

        DatabaseReference myRef = database.getReference().child("events");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventList.clear();

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    final List<String> eventImages = new ArrayList<>();
                    for(DataSnapshot dsi : ds.child("eventImages").getChildren()){
                        StorageReference tmp = storageReference.child(dsi.getValue(String.class));
                        tmp.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                        {
                            @Override
                            public void onSuccess(Uri downloadUrl)
                            {
                                String dwnldLnk = downloadUrl.toString();
                                eventImages.add(dwnldLnk);
                            }
                        });

                    }
                    Event e = new Event(ds.child("eventTitle").getValue(String.class), ds.child("eventLongDescription").getValue(String.class), ds.child("eventShortDescription").getValue(String.class), eventImages);
                    eventList.add(e);
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
