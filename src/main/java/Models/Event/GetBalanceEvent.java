package Models.Event;

import Models.EventType;

public class GetBalanceEvent extends Event {

    public GetBalanceEvent(int loanId, String eventId, EventType eventType) {
        super(loanId, eventId, eventType);
    }
}
