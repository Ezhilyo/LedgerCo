package Models.Event;

import Models.EventType;

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
    EmiPaymentEvent(int loanId, String eventId, EventType eventType, float paidAmount, int month) {
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
