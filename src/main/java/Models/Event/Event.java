package Models.Event;

import Models.EventType;

import java.util.Date;

public class Event{
    private String eventId;
    private EventType eventType;
    private Date createdTime;
    private int loanId;

    public String getEventId() {
        return eventId;
    }

    public EventType getEventType() {
        return eventType;
    }

    public Date getCreatedTime() {
        return createdTime;
    }



    public Event(int loanId, String eventId, EventType eventType) {
        this.loanId = loanId;
        this.eventId = eventId;
        this.eventType = eventType;
        this.createdTime = new Date();
    }

    public int getLoanId(){
        return loanId;
    }
}
