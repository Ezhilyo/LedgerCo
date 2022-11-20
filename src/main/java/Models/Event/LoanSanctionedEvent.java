package Models.Event;

import Models.EventType;

public class LoanSanctionedEvent extends Event{
    public float getSanctionedAmount() {
        return sanctionedAmount;
    }

    public float sanctionedAmount;

    public LoanSanctionedEvent(String eventId, int loanId, EventType eventType, float sanctionedAmount){
        super(loanId, eventId, eventType);
        this.sanctionedAmount = sanctionedAmount;
    }

}
