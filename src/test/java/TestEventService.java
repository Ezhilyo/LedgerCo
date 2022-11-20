import models.Event.Event;
import models.Event.LoanSanctionedEvent;
import models.Event.LumpSumPaymentEvent;
import models.EventType;
import models.LoanDetail;
import models.User;
import org.junit.jupiter.api.Test;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

public class TestEventService {
    private List<Event> events;
    private List<User> users;
    private List<LoanDetail> loanDetails;
    TestEventService(){
        events=new ArrayList<>();
        users = new ArrayList<>();
        loanDetails = new ArrayList<>();
    }

    @Test
    public void testgetTotalAmountPaid(){
        loanDetails.add(new LoanDetail(1, 1, 10000, 2, 5, "a", "IDI",
                ));
        events.add(new LoanSanctionedEvent(1, 1, EventType.LOAN_SANCTIONED, 10000));
        events.add(new LumpSumPaymentEvent(1, 2, EventType.LUMP_SUM, 1000, 3));

    }

}
