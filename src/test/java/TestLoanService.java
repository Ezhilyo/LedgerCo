import models.Event.Event;
import models.Event.LoanSanctionedEvent;
import models.Event.LumpSumPaymentEvent;
import models.EventType;
import models.LoanDetail;
import models.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class TestLoanService {

    List<Event> events;
    List<User> users;
    List<LoanDetail> loanDetails;
    AccountingService accountingService;
    LoanService loanService;

    public TestLoanService(){
        events = new ArrayList<>();
        users = new ArrayList<>();
        loanDetails = new ArrayList<>();
        accountingService = new AccountingService();
        loanService = new LoanService(accountingService);
    }

    @Test
    public void testCalculateEmiMonths(){
        assert loanService.calculateNoOfEMIRemaining(3450, 456) == 8;
        assert loanService.calculateNoOfEMIRemaining(12346, 1000) == 13;
        assert loanService.calculateNoOfEMIRemaining(9876, 990) == 10;
        assert loanService.calculateNoOfEMIRemaining(3450, 456) == 8;
        assert loanService.calculateNoOfEMIRemaining(3450, 456) == 8;
    }

    @Test
    public void testgetAmountPaidTillGivenMonth(){
        events = new ArrayList<>();
        loanDetails = new ArrayList<>();
        loanDetails.add(new LoanDetail(1, 1, 10000,
                2, 5, "a", "IDI",11000, 459
        ));
        events.add(new LoanSanctionedEvent(accountingService.generateEventId(), 1, EventType.LOAN_SANCTIONED, 10000));
        assert loanService.getAmountPaidTillGivenMonth(5, loanDetails.get(0), events) == 2295;
        events.add(new LumpSumPaymentEvent(1, accountingService.generateEventId(), EventType.LUMP_SUM, 2000, 3));
        float res = loanService.getAmountPaidTillGivenMonth(20, loanDetails.get(0), events);
        System.out.println(res);
        assert loanService.getAmountPaidTillGivenMonth(3, loanDetails.get(0), events) == 3377;
        assert loanService.getAmountPaidTillGivenMonth(5, loanDetails.get(0), events) == 4295;
        assert loanService.getAmountPaidTillGivenMonth(20, loanDetails.get(0), events) == 11000;
        assert loanService.getAmountPaidTillGivenMonth(30, loanDetails.get(0), events) == 11000;
    }

    @Test
    public void getNoOfEMIRemaining(){
        events = new ArrayList<>();
        loanDetails = new ArrayList<>();
        loanDetails.add(new LoanDetail(1, 1, 20000,
                2, 5, "a", "IDI",23000, 1100
        ));
        events.add(new LoanSanctionedEvent(accountingService.generateEventId(), 1, EventType.LOAN_SANCTIONED, 10000));
        assert loanService.getNoOfEMIRemaining(loanDetails.get(0), events, 0) == 21;
        assert loanService.getNoOfEMIRemaining(loanDetails.get(0), events, 10) == 11;
        events.add(new LumpSumPaymentEvent(1, accountingService.generateEventId(), EventType.LUMP_SUM, 2000, 3));
        assert loanService.getNoOfEMIRemaining(loanDetails.get(0), events, 10) == 10;
        assert loanService.getNoOfEMIRemaining(loanDetails.get(0), events, 20) == 0;
        assert loanService.getNoOfEMIRemaining(loanDetails.get(0), events, 10) == 10;
    }


    @Test
    public void testupdateEmiPayments(){
        loanDetails.add(new LoanDetail(1, 1, 10000,
                2, 5, "a", "IDI",11000, 459
        ));
        events.add(new LoanSanctionedEvent(accountingService.generateEventId(), 1, EventType.LOAN_SANCTIONED, 10000));
        loanService.updateEmiPayments(loanDetails.get(0), 5, events,  loanDetails.get(0).getTotalRepaymentAmount());
        assert events.size() == 6;
        float remainingAmount = accountingService.getRemainingAmountToBePaid(loanDetails.get(0), events);
        loanService.updateEmiPayments(loanDetails.get(0), 36, events,  remainingAmount);
        assert events.size() == 25;

        loanDetails.add(new LoanDetail(2, 2, 20000,
                2, 5, "a", "IDI",23500, 1000
        ));

        events = new ArrayList<>();

        events.add(new LoanSanctionedEvent(accountingService.generateEventId(), 2, EventType.LOAN_SANCTIONED,
                10000));
        events.add(new LumpSumPaymentEvent(2, accountingService.generateEventId(), EventType.LUMP_SUM, 5000, 3));

        remainingAmount = accountingService.getRemainingAmountToBePaid(loanDetails.get(1), events);

        loanService.updateEmiPayments(loanDetails.get(1), 40, events,
                remainingAmount);

        System.out.println(events.size());

        assert events.size() == 21;

    }

    @Test
    public void testCalculateEmiAmount(){
        assert loanService.calculateEmiAmount(12345, 13) == 950;
        assert loanService.calculateEmiAmount(10000, 11) == 910;
        assert loanService.calculateEmiAmount(5436, 9) == 604;
        assert loanService.calculateEmiAmount(1235.60, 6) == 206;
    }
}
