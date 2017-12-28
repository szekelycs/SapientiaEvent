package com.example.szekcsbobo.sapientiaevent;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by szekc on 28/12/2017.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    private List<Event> eventList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView eventTitle, eventShortDescription;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();

            eventTitle = (TextView) view.findViewById(R.id.event_title);
            eventShortDescription = (TextView) view.findViewById(R.id.event_short_description);
        }
    }


    public EventAdapter(List<Event> eventList) {
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

//    @Override
//    public void onClick(View v){
//        final Intent intent = new Intent(context, EventOpenActivity.class);
//        context.startActivity(intent);
//    }
}
