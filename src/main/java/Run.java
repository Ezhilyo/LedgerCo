import java.util.List;

import static Utils.ReadFileUtil.readFile;


public class Run {

    public static void main(String[] args) throws Exception {
        String filePath = "/Users/ezhilkannan/Downloads/LedgerCoAssignment/test.txt";
        List<String> commands = readFile(filePath);
        new Service().runCommands(commands);
    }
}
