package com.example.szekcsbobo.sapientiaevent;

import java.io.Serializable;
import java.util.List;


/**
 * Event
 *
 * <b>Event</b> class with getters and setters
 * The recycler view adapter uses Event type objects on the applications main screen
 * The list view adapter uses Event type objects for displaying the detailed description of an event
 * @author: Szekely Csongor 28/12/2017.
 */

public class Event implements Serializable{
    private String eventTitle, eventShortDescription, eventLongDescription;
    private List<String> eventImages;

    /*public Event() {
    }*/

    /**
     * @constructor initalizes the Event datas
     *
     * @param eventTitle - title
     * @param eventLongDescription - long description
     * @param eventShortDescription - short description
     * @param eventImages - image download URL list - String type
     */

    public Event( String eventTitle, String eventLongDescription, String eventShortDescription, List<String> eventImages) {
        this.eventTitle = eventTitle;
        this.eventLongDescription = eventLongDescription;
        this.eventShortDescription = eventShortDescription;
        this.eventImages = eventImages;
    }

    /*public Event(String eventTitle, String eventShortDescription , String eventLongDescription) {
        this.eventTitle = eventTitle;
        this.eventLongDescription = eventLongDescription;
        this.eventShortDescription = eventShortDescription;
    }

    public Event(String eventTitle, String eventShortDescription) {
        this.eventTitle = eventTitle;
        this.eventShortDescription = eventShortDescription;
    }*/

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

    public List<String> getEventImages() {
        return eventImages;
    }

    public void setEventImages(List<String> eventImages) {
        this.eventImages = eventImages;
    }
}
