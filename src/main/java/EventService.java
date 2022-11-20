import models.Event.*;
import java.util.ArrayList;
import java.util.List;


public class EventService {
    private int counter=1;
    EventService(){
        counter=1;
    }

    public int generateEventId(){
        return counter++;
    }

    public static List<Event> getEventsForLoan(int loan_id, List<Event> events){
        List<Event> out=new ArrayList<>();
        for(Event evt: events){
            if(evt.getLoanId()==loan_id){
                out.add(evt);
            }
        }
        return out;
    }

    public float getTotalAmountPaid(int loan_id, List<Event> events, int month) {
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

    public float getRemainingAmountToBePaid(int loan_id, List<Event> events, int month){
        List<Event> eventsForLoan = getEventsForLoan(loan_id, events);
        float balance=0;
        for(Event event: eventsForLoan){
            if(event instanceof LoanSanctionedEvent evt){
                balance+=evt.getSanctionedAmount();
            }else if(event instanceof EmiPaymentEvent evt){
                if(evt.getMonth()<=month) {
                    balance -= evt.getPaidAmount();
                }
            }else if(event instanceof LumpSumPaymentEvent evt){
                if(evt.getMonth()<=month) {
                    balance -= evt.getPaidAmount();
                }
            }
        }
        return balance;
    }

    public int getCounter(){
        return counter++;
    }

}
