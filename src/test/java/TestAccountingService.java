import Models.Event.EmiPaymentEvent;
import Models.Event.Event;
import Models.Event.LoanSanctionedEvent;
import Models.Event.LumpSumPaymentEvent;
import Models.EventType;
import Models.LoanDetail;
import Models.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestAccountingService {
    private List<Event> events;
    private List<User> users;
    private List<LoanDetail> loanDetails;
    private AccountingService accountingService;
    TestAccountingService(){
        events=new ArrayList<>();
        users = new ArrayList<>();
        loanDetails = new ArrayList<>();
        accountingService = new AccountingService();
    }

    @Test
    public void testgetTotalAmountPaid(){
        loanDetails.add(new LoanDetail(1, 1, 10000,
                2, 5, "a", "IDI",11000, 459
                ));
        events.add(new LoanSanctionedEvent("1", 1, EventType.LOAN_SANCTIONED, 10000));
        events.add(new LumpSumPaymentEvent(1, "2", EventType.LUMP_SUM, 1000, 3));
        events.add(new EmiPaymentEvent(1, "3", EventType.EMI_PAYMENT, 459, 1));
        events.add(new EmiPaymentEvent(1, "3", EventType.EMI_PAYMENT, 459, 2));
        events.add(new EmiPaymentEvent(1, "3", EventType.EMI_PAYMENT, 459, 3));
        events.add(new EmiPaymentEvent(1, "3", EventType.EMI_PAYMENT, 459, 4));
        events.add(new EmiPaymentEvent(1, "3", EventType.EMI_PAYMENT, 459, 5));

        assert accountingService.getTotalAmountPaidTillGivenMonth(1, events, 4) == 2836;
        assert accountingService.getTotalAmountPaidTillGivenMonth( 1, events, 2) == 459*2;
        assert accountingService.getTotalAmountPaidTillGivenMonth( 1, events, 5) == 2836+459;

        events.add(new LumpSumPaymentEvent(1, "2", EventType.LUMP_SUM, 3000, 2));
        assert accountingService.getTotalAmountPaidTillGivenMonth( 1, events, 5) == 2836+459+3000;

    }

}
