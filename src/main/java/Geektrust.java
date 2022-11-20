import java.util.List;

import static Utils.ReadFileUtil.readFile;


public class Geektrust {

    public static void main(String[] args) throws Exception {
        String filePath = args[0];
        List<String> commands = readFile(filePath);
        new Service().runCommands(commands);
    }
}
