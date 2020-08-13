package correcter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.printf("%s ", "Write a mode:");
        final Scanner scanner = new Scanner(System.in);
        final String mode = scanner.next().toLowerCase();
        switch (mode) {
            case "send":
                Send.openFileReadMakeErrorsSave();
                break;
            case "encode":
                Encode.openFileAndEncode();
                break;
            case "decode":
                Decode.firstMethod();
                break;
            default:
                System.out.printf("\n%s", "Error! Invalid mode");
                break;
        }
    }
}
