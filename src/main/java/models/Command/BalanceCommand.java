package models.Command;

public class BalanceCommand extends Command{
    public int month;
    public BalanceCommand(String bankName, String userName, int month) {
        super(userName, bankName);
        this.month = month;
    }
}
