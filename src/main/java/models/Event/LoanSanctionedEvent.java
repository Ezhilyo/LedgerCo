package models.Event;

import models.EventType;

public class LoanSanctionedEvent extends Event{
    public float getSanctionedAmount() {
        return sanctionedAmount;
    }

    public float sanctionedAmount;

    public LoanSanctionedEvent(int eventId, int loanId, EventType eventType, float sanctionedAmount){
        super(loanId, eventId, eventType);
        this.sanctionedAmount = sanctionedAmount;
    }

}
