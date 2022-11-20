package Models.Command;

public class LoanCommand extends Command {
    public float amount;
    public int no_of_years;
    public  float roi;

    public LoanCommand(String bankName, String userName, float amount, int no_of_years, float roi) {
        super(userName, bankName);
        this.roi = roi;
        this.no_of_years = no_of_years;
        this.amount = amount;
    }

}
