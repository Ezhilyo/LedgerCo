import models.Event.*;
import models.LoanDetail;

import java.util.ArrayList;
import java.util.List;


public class AccountingService {
    private int counter;
    AccountingService(){
        this.counter=1;
    }

    public String generateEventId(){
        Integer id = counter++;
        return id.toString();
    }

    public List<Event> getEventsForLoan(int loan_id, List<Event> events){
        List<Event> out=new ArrayList<>();
        for(Event evt: events){
            if(evt.getLoanId()==loan_id){
                out.add(evt);
            }
        }
        return out;
    }

    public float getTotalAmountPaidTillGivenMonth(int loan_id, List<Event> events, int month) {
        List<Event> eventsForLoan = getEventsForLoan(loan_id, events);
        float paidAmount = 0;
        for (Event event : eventsForLoan) {if (event instanceof EmiPaymentEvent evt) {
            if (evt.getMonth() <= month) {
                paidAmount += evt.getPaidAmount();
                }
            } else if (event instanceof LumpSumPaymentEvent evt) {
                if (evt.getMonth() <= month) {
                    paidAmount += evt.getPaidAmount();
                }
            }
        }
        return paidAmount;
    }

    public float getRemainingAmountToBePaid(LoanDetail loanDetail, List<Event> events, int month){
        float totalAmountPaid = getTotalAmountPaidTillGivenMonth(loanDetail.getId(), events,
                loanDetail.getLastEmiPaidMonth());
        return loanDetail.getTotalRepaymentAmount() - totalAmountPaid;
    }

}
