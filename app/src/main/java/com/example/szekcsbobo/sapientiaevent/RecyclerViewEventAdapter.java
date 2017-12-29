package com.example.szekcsbobo.sapientiaevent;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();

            eventTitle = (TextView) view.findViewById(R.id.event_title);
            eventShortDescription = (TextView) view.findViewById(R.id.event_short_description);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            final Intent intent;
            intent =  new Intent(context, EventOpenActivity.class);
            int pos = getAdapterPosition();
            if(pos != RecyclerView.NO_POSITION){
                Event e = eventList.get(pos);
//                Toast.makeText(v.getContext(), "You clicked " + e.getEventTitle(), Toast.LENGTH_SHORT).show();

                //AZ ALATTAM LEVO PUTEXTRA HIBAS<FUCKING STRINGET VAR BE

                //intent.putExtra("Event", e);
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
