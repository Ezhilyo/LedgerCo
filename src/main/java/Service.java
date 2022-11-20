import Models.Command.*;
import Models.Event.Event;
import Models.LoanDetail;
import Models.User;

import java.util.ArrayList;
import java.util.List;

public class Service {

    private List<User> users;
    private List<LoanDetail> loanDetails;
    private List<Event> events;

    private UserService userService;
    private AccountingService accountingService;
    private LoanService loanService;
    Service(){
        this.users = new ArrayList<>();
        this.loanDetails = new ArrayList<>();
        this.events = new ArrayList<>();

        this.userService = new UserService();
        this.accountingService = new AccountingService();
        this.loanService = new LoanService(accountingService);
    }

    public void runCommands(List<String> commands) throws Exception {
        for(String command: commands) {
            Command comm = CommandFactory.getCommand(command);

            User user = userService.createOrgetUser(comm.bankName, comm.userName, users);

            if(comm instanceof LoanCommand lc){

                try {
                    loanService.createLoanDetail(user, lc.amount, lc.roi,
                            lc.no_of_years, loanDetails, events);
                }catch (Exception e){
                    System.out.println("Loan detail already present");
                }

            }else if(comm instanceof BalanceCommand bc){

                LoanDetail loanDetail = loanService.getLoanDetail(user, loanDetails);
                float amountPaid = loanService.getAmountPaidTillGivenMonth(bc.month, loanDetail, events);
                int noOfEmiRemaining = loanService.getNoOfEMIRemaining(loanDetail, events, bc.month);
                System.out.println(user.getBankName()+" "+user.getName()+" "+amountPaid+ " "+noOfEmiRemaining);

            }else if(comm instanceof PaymentCommand pc){

                LoanDetail loanDetail = loanService.getLoanDetail(user, loanDetails);
                loanService.updateLumpPayment(loanDetail, pc.month, pc.amount, events);

            }
        }
    }
}
