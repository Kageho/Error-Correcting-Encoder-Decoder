package correcter;

import java.io.*;
import java.util.Random;

public class Send {
    public static void openFileReadMakeErrorsSave() {
        // i make static method which open file send.txt
//        takes from it one byte make shift to one bit
//        and write this result to received.txt
        Random random = new Random();
        final String initialPath = "encoded.txt";
        final String resultPath = "received.txt";

        try (FileInputStream fileInputStream = new FileInputStream(initialPath)) {
            try (FileOutputStream outputStream = new FileOutputStream(resultPath)) {
                byte[] value = fileInputStream.readAllBytes();
                for (byte b : value) {
                    byte variable = (byte) (b ^ 1 << (random.nextInt(7) + 1));
                    outputStream.write(variable);
                }
            }
        } catch (FileNotFoundException fnf) {
            System.out.println("Too bad for you");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
