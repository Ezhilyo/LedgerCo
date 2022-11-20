import Models.Event.EmiPaymentEvent;
import Models.Event.Event;
import Models.Event.LoanSanctionedEvent;
import Models.Event.LumpSumPaymentEvent;
import Models.EventType;
import Models.LoanDetail;
import Models.User;

import java.util.List;


public class LoanService {
    private int counter;
    private AccountingService accountingService;
    LoanService(AccountingService accountingService){
        this.counter = 1;
        this.accountingService = accountingService;
    }

    public LoanDetail getLoanDetail(User user, List<LoanDetail> loanDetails){
        for(LoanDetail ld : loanDetails){
            if(ld.getName().equals(user.getName()) && user.getBankName().equals(ld.getBankName())){
                return ld;
            }
        }
        return null;
    }

    public double calculateInterest(double borrowedAmount, double roi, float no_of_months){
        return (borrowedAmount * roi * (no_of_months/12.0))/100.0;
    }

    public int calculateEmiAmount(double amount, int no_of_months){
        return (int)Math.ceil((amount/(float) no_of_months));
    }

    public double calculateTotalAmountToBePaid(double borrowedAmount, double roi, float no_of_months){
        return borrowedAmount + calculateInterest(borrowedAmount, roi, no_of_months);
    }

    public void createLoanDetail(User user, float borrowedAmount, float roi, int no_of_years,
                                 List<LoanDetail> loanDetails, List<Event> events) throws Exception{

        LoanDetail existingLoanDetail = getLoanDetail(user, loanDetails);
        if(existingLoanDetail!=null){
            throw new Exception("Loan Already present");
        }

        int no_of_months = no_of_years * 12;
        double totalAmountToBePaid = calculateTotalAmountToBePaid(borrowedAmount, roi, no_of_months);
        int emiAmount = calculateEmiAmount(totalAmountToBePaid, no_of_months);

        LoanDetail newLoanDetail = new LoanDetail(counter++, user.getId(), borrowedAmount, no_of_months, roi,
                user.getName(), user.getBankName(), (float)totalAmountToBePaid, emiAmount);

        loanDetails.add(newLoanDetail);

        events.add(new LoanSanctionedEvent(accountingService.generateEventId(), newLoanDetail.getId(),
                EventType.LOAN_SANCTIONED, borrowedAmount));
    }

    public void updateEmiPayments(LoanDetail loanDetail, int month, List<Event> events, float remainingAmount){

        for(int mon=loanDetail.getLastEmiPaidMonth()+1;mon<=month;mon++){
            float amountPaid = Math.min(remainingAmount, loanDetail.getEmiAmount());
            remainingAmount-=amountPaid;
            if(amountPaid <= 0){
                break;
            }
            loanDetail.setLastEmiPaidMonth(mon);
            String event_id = accountingService.generateEventId();
            EmiPaymentEvent emiPaymentEvent = new EmiPaymentEvent(loanDetail.getId(), event_id, EventType.EMI_PAYMENT,
                    amountPaid, mon);
            events.add(emiPaymentEvent);
        }

    }

    public void updateLumpPayment(LoanDetail loanDetail, int month, float amount, List<Event> events){
        events.add(new LumpSumPaymentEvent(loanDetail.getId(), accountingService.generateEventId(), EventType.EMI_PAYMENT,
                amount, month));
    }

    public float getAmountPaidTillGivenMonth(int month, LoanDetail loanDetail, List<Event> events){

        float remainingAmount = accountingService.getRemainingAmountToBePaid(loanDetail, events);
        //        update payments till the month if not updated
        updateEmiPayments(loanDetail, month, events, remainingAmount);
        return accountingService.getTotalAmountPaidTillGivenMonth(loanDetail.getId(), events, month);
    }

    public int calculateNoOfEMIRemaining(float remainingAmount, float emiAmount){
        return (int)(Math.ceil(remainingAmount/emiAmount));
    }

    public int getNoOfEMIRemaining(LoanDetail loanDetail, List<Event> events, int month){
        float totalAmountPaidSoFar = accountingService.getTotalAmountPaidTillGivenMonth(loanDetail.getId(),
                events, month);
        float remainingAmount = Math.max(0,loanDetail.getTotalRepaymentAmount() - totalAmountPaidSoFar);
//        update payments till the month if not updated
        updateEmiPayments(loanDetail, month, events, remainingAmount);
        float remainingAmountAfterUpdate = Math.max(0, loanDetail.getTotalRepaymentAmount())-accountingService.getTotalAmountPaidTillGivenMonth(loanDetail.getId(),
                events, month);
        return calculateNoOfEMIRemaining(remainingAmountAfterUpdate, loanDetail.getEmiAmount());
    }

}
