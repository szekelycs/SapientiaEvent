package com.example.szekcsbobo.sapientiaevent;

import android.widget.ImageView;

import java.util.List;

/**
 * Created by szekc on 28/12/2017.
 */

public class Event {
    private int eventID;
    private String eventTitle, eventShortDescription, eventLongDescription;
    private List<ImageView> eventImages;

    public Event() {
    }

    public Event(int eventID, String eventTitle, String eventLongDescription, String eventShortDescription, List<ImageView> eventImages) {
        this.eventID = eventID;
        this.eventTitle = eventTitle;
        this.eventLongDescription = eventLongDescription;
        this.eventShortDescription = eventShortDescription;
        this.eventImages = eventImages;
    }

    public Event(String eventTitle, String eventShortDescription) {
        this.eventTitle = eventTitle;
        this.eventShortDescription = eventShortDescription;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public String getEventTitle() {
        return this.eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventShortDescription() {
        return this.eventShortDescription;
    }

    public void setEventShortDescription(String eventShortDescription) {
        this.eventShortDescription = eventShortDescription;
    }

    public String getEventLongDescription() {
        return eventLongDescription;
    }

    public void setEventLongDescription(String eventLongDescription) {
        this.eventLongDescription = eventLongDescription;
    }

    public List<ImageView> getEventImages() {
        return eventImages;
    }

    public void setEventImages(List<ImageView> eventImages) {
        this.eventImages = eventImages;
    }
}
