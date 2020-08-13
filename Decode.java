package correcter;

import java.io.*;
import java.util.ArrayList;

public class Decode {
    private static StringBuilder answerSB = new StringBuilder();
    private static ArrayList<Byte> myList = new ArrayList<>();

    public static void firstMethod() {
        try (FileOutputStream outputStream = new FileOutputStream("decoded.txt", false)) {
            decode();
            for (Byte aByte : myList) { // write result to decode.txt
                outputStream.write(aByte);
            }
        } catch (FileNotFoundException fnf) {
            System.out.println("Alert! Attention! File not Found");
        } catch (IOException io) {
            System.out.println("Exception is occur");
        }
    }

    // method for getting three bits from one byte
    private static String getFourBits(StringBuilder stringBuilder) {
        int firstPairs = 0;
        int zeroPairs = 0;
        int secondPairs = 0;
        for (int i = 2; i < stringBuilder.length(); i++) {
            if (i == 2 || i >= 4 && i <= 6) {
                if (stringBuilder.charAt(i) == '1') {
                    if (i != 5) {
                        zeroPairs++;
                    }
                    if (i != 4) {
                        firstPairs++;
                    }
                    if (i != 2) {
                        secondPairs++;
                    }
                }
            }
        }
        zeroPairs %= 2;
        firstPairs %= 2;
        secondPairs %= 2;
        int indexOfMistake = ((String.valueOf(zeroPairs).equals(String.valueOf(stringBuilder.charAt(0))) ? 0 : 1) +
                ((String.valueOf(firstPairs).equals(String.valueOf(stringBuilder.charAt(1)))) ? 0 : 2) +
                (String.valueOf(secondPairs).equals(String.valueOf(stringBuilder.charAt(3))) ? 0 : 4)) - 1;
        byte rightByte = stringBuilder.charAt(indexOfMistake) != '1' ? (byte) 1 : (byte) 0;
        StringBuilder str = new StringBuilder();
        for (int j = 2; j < stringBuilder.length(); j++) {
            if (j == 2 || j >= 4 && j <= 6) {
                if (indexOfMistake == j) {
                    str.append(rightByte);
                } else {
                    str.append(stringBuilder.charAt(j));
                }
            }
        }
        return str.toString();
    }

    private static void decode() {
        try (FileInputStream fileInputStream = new FileInputStream("received.txt")) {
            byte[] bytes; // array for all bits
            bytes = fileInputStream.readAllBytes();
            StringBuilder stringWithBytes = new StringBuilder();
            for (byte aByte : bytes) {
//                  the line below transfer decimal byte to the binary
                stringWithBytes.append(String.format("%8s", Integer.toBinaryString(aByte & 0xFF)).replace(' ', '0'));
            }
            for (int i = 0; i < stringWithBytes.length(); i += 8) {
                answerSB.append(getFourBits(new StringBuilder(stringWithBytes.subSequence(i, i + 7))));
            }
            for (int i = 0; i < answerSB.length(); i += 8) {
                myList.add(Encode.makeFromStringByte(new StringBuilder(answerSB.subSequence(i, i + 8))));
            }
        } catch (FileNotFoundException fnf) {
            System.out.println("Exception!");
        } catch (IOException e) {
            System.out.println("Error ");
            e.printStackTrace();
        }
    }
}

