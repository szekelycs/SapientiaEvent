package com.example.szekcsbobo.sapientiaevent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * Created by szekc on 28/12/2017.
 */

public class RecyclerViewEventAdapter extends RecyclerView.Adapter<RecyclerViewEventAdapter.MyViewHolder> {

    private List<Event> eventList;
    private Context context;

    private static final String TAG = "RVEVENTA";

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView eventTitle, eventShortDescription;
        public Event tempEvent;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();

            eventTitle = (TextView) view.findViewById(R.id.event_title);
            eventShortDescription = (TextView) view.findViewById(R.id.event_short_description);

            view.setOnClickListener(this);
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context);
                    dlgAlert.setMessage("Are you sure?");
                    dlgAlert.setTitle("Delete");
                    dlgAlert.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    int pos = getAdapterPosition();
                                    if(pos != RecyclerView.NO_POSITION) {
                                        Event e = eventList.get(pos);
                                        tempEvent = e;
                                        DeleteData();
                                    }
                                }
                            });
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();

                    return true;
                }
            });
        }

        //By Gagyi Zalan Robert
        private void DeleteData(){

            DatabaseReference myRef = database.getReference().child("events");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for(DataSnapshot ds : dataSnapshot.getChildren()){

                        //Log.d(TAG,ds.child("eventTitle").getValue().toString());
                        if(tempEvent != null) {
                            if (ds.child("eventTitle").getValue() == tempEvent.getEventTitle()) {

                                //Here we delete the images of the event
                                for(DataSnapshot dsi : ds.child("eventImages").getChildren()){
                                    StorageReference tmp = storageReference.child(dsi.getValue(String.class));
                                    tmp.delete();

                                }

                                //Here we delete the event
                                ds.getRef().removeValue();
                            }
                        }
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });

        }
        @Override
        public void onClick(View v) {

            final Intent intent;
            intent =  new Intent(context, EventOpenActivity.class);
            int pos = getAdapterPosition();
            if(pos != RecyclerView.NO_POSITION){
                Event e = eventList.get(pos);
//                Toast.makeText(v.getContext(), "You clicked " + e.getEventTitle(), Toast.LENGTH_SHORT).show();

                intent.putExtra("Event", e);
                Log.d(TAG, e.getEventTitle() + " " + e.getEventShortDescription() + " " + e.getEventLongDescription());
                context.startActivity(intent);
            }

            //
        }

    }


    public RecyclerViewEventAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.eventTitle.setText(event.getEventTitle());
        holder.eventShortDescription.setText(event.getEventShortDescription());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

}
