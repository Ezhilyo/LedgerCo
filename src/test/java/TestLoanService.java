import models.Event.Event;
import models.Event.LoanSanctionedEvent;
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

    public void testCalculateTotalAmount(){

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
    public void testupdateEmiPayments(){
        loanDetails.add(new LoanDetail(1, 1, 10000,
                2, 5, "a", "IDI",11000, 459
        ));
        events.add(new LoanSanctionedEvent("1", 1, EventType.LOAN_SANCTIONED, 10000));
        loanService.updateEmiPayments(loanDetails.get(0), 5, events,  loanDetails.get(0).getTotalRepaymentAmount());
        assert events.size() == 6;
        float remainingAmount = accountingService.getRemainingAmountToBePaid(loanDetails.get(0), events, 36);
        System.out.println(remainingAmount);
        loanService.updateEmiPayments(loanDetails.get(0), 36, events,  remainingAmount);
        System.out.println(events.size());
        assert events.size() == 25;
    }

    @Test
    public void testCalculateEmiAmount(){
        assert loanService.calculateEmiAmount(12345, 13) == 950;
        assert loanService.calculateEmiAmount(10000, 11) == 910;
        assert loanService.calculateEmiAmount(5436, 9) == 604;
        assert loanService.calculateEmiAmount(1235.60, 6) == 206;
    }
}
