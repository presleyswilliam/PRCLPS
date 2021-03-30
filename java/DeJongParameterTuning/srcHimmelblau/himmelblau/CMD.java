package himmelblau;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class CMD {
    public static void runSingleCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
