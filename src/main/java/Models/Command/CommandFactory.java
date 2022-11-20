package Models.Command;

import java.util.Arrays;
import java.util.List;

public class CommandFactory {
    public static Command getCommand(String comm) throws Exception{
        List<String> strs = Arrays.asList(comm.split(" "));
        switch (strs.get(0)) {
            case "LOAN":
                return new LoanCommand(strs.get(1), strs.get(2), Float.parseFloat(strs.get(3)),
                        Integer.parseInt(strs.get(4)), Float.parseFloat(strs.get(5)));
            case "BALANCE":
                return new BalanceCommand(strs.get(1), strs.get(2), Integer.parseInt(strs.get(3)));
            case "PAYMENT":
                return new PaymentCommand(strs.get(1), strs.get(2), Float.parseFloat(strs.get(3)),
                        Integer.parseInt(strs.get(4)));
        }
        throw new Exception("Invalid Command");
    }
}
