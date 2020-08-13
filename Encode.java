package correcter;

import java.io.*;
import java.util.ArrayList;

public class Encode {
    private static StringBuilder bufferString = new StringBuilder();
    private static ArrayList<Byte> myList = new ArrayList<>();

    public static void openFileAndEncode() {
        try (FileOutputStream outputStream = new FileOutputStream("encoded.txt", false)) {
            readFileAndProcessData();
            for (Byte aByte : myList) { // write to encoded.txt
                outputStream.write(aByte);
            }
        } catch (FileNotFoundException fnf) {
            System.out.println("File not found; exception number one");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static byte makeFromStringByte(StringBuilder stringBuilder) {
//        here i get from string one byte
        return (byte) Integer.parseInt(stringBuilder.toString(), 2);
    }

    private static StringBuilder encodeByte(StringBuilder str) {
        byte[] myFourElements = str.toString().getBytes();
        boolean zeroPower = 1 == (myFourElements[0] + myFourElements[1] + myFourElements[3]) % 2;
        boolean firstPlace = 1 == (myFourElements[0] + myFourElements[2] + myFourElements[3]) % 2;
        boolean secondPower = 1 == (myFourElements[1] + myFourElements[2] + myFourElements[3]) % 2;
        str.insert(0, zeroPower ? '1' : '0');
        str.insert(1, firstPlace ? '1' : '0');
        str.insert(3, secondPower ? '1' : '0');
        str.append('0');
        return str;
    }

    private static void readFileAndProcessData() {
        try (FileInputStream fileInputStream = new FileInputStream("send.txt")) {
            byte[] bytes;
            bytes = fileInputStream.readAllBytes();
            StringBuilder stringWithBytes = new StringBuilder();
            for (byte aByte : bytes) {
//                  the line below transfer decimal byte to the binary
                stringWithBytes.append(String.format("%8s", Integer.toBinaryString(aByte & 0xFF)).replace(' ', '0'));
            }
            for (int i = 0; i < stringWithBytes.length(); i += 4) {
                bufferString.append(encodeByte(new StringBuilder(stringWithBytes.subSequence(i, i + 4))));
            }
            for (int i = 0; i < bufferString.length(); i += 8) {
                myList.add(makeFromStringByte(new StringBuilder(bufferString.subSequence(i, i + 8))));
            }

        } catch (FileNotFoundException fnf) {
            System.out.println("Sorry file not found; exception with files here");
        } catch (IOException io) {
            io.printStackTrace();
        }

    }
}
