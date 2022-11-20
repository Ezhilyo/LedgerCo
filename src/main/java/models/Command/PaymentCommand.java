package models.Command;

public class PaymentCommand extends Command{

    public float amount;
    public int month;

    public PaymentCommand(String bankName, String userName, float amount, int month) {
        super(userName, bankName);
        this.amount=amount;
        this.month = month;

    }
}
