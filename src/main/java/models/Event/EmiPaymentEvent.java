package models.Event;

import models.EventType;

public class EmiPaymentEvent extends Event{
    public float getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(float paidAmount) {
        this.paidAmount = paidAmount;
    }

    private float paidAmount;
    private int month;
    public
    EmiPaymentEvent(int loanId, int eventId, EventType eventType, float paidAmount, int month) {
        super(loanId, eventId, eventType);
        this.paidAmount = paidAmount;
        this.month = month;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
