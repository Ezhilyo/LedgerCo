package models.Event;

import models.EventType;

public class GetBalanceEvent extends Event {

    public GetBalanceEvent(int loanId, String eventId, EventType eventType) {
        super(loanId, eventId, eventType);
    }
}
