import models.Event.EmiPaymentEvent;
import models.Event.Event;
import models.Event.LoanSanctionedEvent;
import models.Event.LumpSumPaymentEvent;
import models.EventType;
import models.LoanDetail;
import models.User;

import java.util.List;


public class LoanService {
    private int counter;
    private EventService eventService;
    LoanService(EventService eventService){
        this.counter = 1;
        this.eventService = eventService;
    }

    public LoanDetail getLoanDetail(User user, List<LoanDetail> loanDetails){
        for(LoanDetail ld : loanDetails){
            if(ld.getName().equals(user.getName()) && user.getBankName().equals(ld.getBankName())){
                return ld;
            }
        }
        return null;
    }

    private double calculateInterest(double borrowedAmount, double roi, float no_of_months){
        return (borrowedAmount * roi * (no_of_months/12.0))/100.0;
    }

    private int calculateEmiAmount(double amount, int no_of_months){
        return (int)Math.ceil((amount/(float) no_of_months));
    }

    public void createLoanDetail(User user, float borrowedAmount, float roi, int no_of_years,
                                 List<LoanDetail> loanDetails, List<Event> events) throws Exception{
        LoanDetail existingLoanDetail = getLoanDetail(user, loanDetails);
        if(existingLoanDetail!=null){
            throw new Exception("Loan Already present");
        }

        int no_of_months = no_of_years * 12;
        double interest = calculateInterest(borrowedAmount, roi, no_of_months);
        double totalAmountToBePaid = borrowedAmount + interest;
        int emiAmount = calculateEmiAmount(totalAmountToBePaid, no_of_months);

        LoanDetail newLoanDetail = new LoanDetail(counter++, user.getId(), borrowedAmount, no_of_months, roi,
                user.getName(), user.getBankName(), (float)totalAmountToBePaid, emiAmount);

        loanDetails.add(newLoanDetail);

        events.add(new LoanSanctionedEvent(eventService.generateEventId(), newLoanDetail.getId(),
                EventType.LOAN_SANCTIONED, borrowedAmount));
    }

    private void updateEmiPayments(LoanDetail loanDetail, int month, List<Event> events, float remainingAmount){

        for(int mon=loanDetail.getLastEmiPaidMonth()+1;mon<=month;mon++){
            float amountPaid = Math.min(remainingAmount, loanDetail.getEmiAmount());
            remainingAmount-=amountPaid;
            loanDetail.setLastEmiPaidMonth(mon);
            if(amountPaid <= 0){
                break;
            }
            int event_id = eventService.generateEventId();
            EmiPaymentEvent emiPaymentEvent = new EmiPaymentEvent(loanDetail.getId(), event_id, EventType.EMI_PAYMENT,
                    amountPaid, mon);
            events.add(emiPaymentEvent);
        }

    }

    public void updateLumpPayment(LoanDetail loanDetail, int month, float amount, List<Event> events){
        events.add(new LumpSumPaymentEvent(loanDetail.getId(), eventService.generateEventId(), EventType.EMI_PAYMENT,
                amount, month));
    }

    public float getAmountPaidTillGivenMonth(int month, LoanDetail loanDetail, List<Event> events){

        float remainingAmount = eventService.getRemainingAmountToBePaid(loanDetail.getId(), events,
                loanDetail.getLastEmiPaidMonth());
        updateEmiPayments(loanDetail, month, events, remainingAmount);
        return eventService.getTotalAmountPaid(loanDetail.getId(), events, month);
    }

    private int calculateNoOfEMIRemaining(float remainingAmount, float emiAmount){
        return (int)(Math.ceil(remainingAmount/emiAmount));
    }

    public int getNoOfEMIRemaining(LoanDetail loanDetail, List<Event> events, int month){
        float totalAmountPaidSoFar = eventService.getTotalAmountPaid(loanDetail.getId(), events, month);
        float remainingAmount = loanDetail.getAmountToBePaid() - totalAmountPaidSoFar;
        return calculateNoOfEMIRemaining(remainingAmount, loanDetail.getEmiAmount());
    }

}
