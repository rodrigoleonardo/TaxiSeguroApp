package edu.galileo.android.taxiseguro.contacttaxiservicelist.events;

import edu.galileo.android.taxiseguro.entities.User;

/**
 * Created by rodrigo on 14/06/16.
 */
public class ContactTaxiServiceListEvent {
    public final static int onContactAdded = 0;
    public final static int onContactChanged = 1;
    public final static int onContactRemoved = 2;

    private User user;
    private int eventType;

    public User getUser(){
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }
}
