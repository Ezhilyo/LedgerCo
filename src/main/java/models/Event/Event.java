package models.Event;

import models.EventType;

import java.util.Date;

public class Event{
    private int eventId;
    private EventType eventType;
    private Date createdTime;
    private int loanId;

    public int getEventId() {
        return eventId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Date getCreatedTime() {
        return createdTime;
    }



    public Event(int loanId, int eventId, EventType eventType) {
        this.loanId = loanId;
        this.eventId = eventId;
        this.eventType = eventType;
        this.createdTime = new Date();;
    }

    public int getLoanId(){
        return loanId;
    }
}
