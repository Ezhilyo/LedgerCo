package models.Event;

import models.EventType;

public class LumpSumPaymentEvent extends Event{
    private float paidAmount;
    private int month;

    public LumpSumPaymentEvent(int loanId, int eventId, EventType eventType, float paidAmount, int month) {
        super(loanId, eventId, eventType);
        this.paidAmount=paidAmount;
        this.month = month;
    }

    public float getPaidAmount() {
        return paidAmount;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
