package reghzy.packet.utils;

import reghzy.packet.Packet;
import reghzy.packet.packets.Packet1Chat;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class PacketUtils {
    public static int getStringLenWL(String str) {
        return 2 + (str.length() * 2);
    }

    public static int getStringLenNL(String str) {
        return str.length() * 2;
    }

    /**
     * Writes 2 bytes (a short, being the length of the string), and 2 bytes for each character of the given char array
     * @param value The array of chars to write (maximum length allowed is 65535)
     * @param output The data output to write to
     * @throws IOException Thrown if there was an IOException while writing a character
     */
    public static void writeCharsWL(char[] value, DataOutput output) throws IOException {
        output.writeShort(value.length);
        for(char c : value) {
            output.writeChar(c);
        }
    }

    /**
     * Writes 2 bytes for each character of the given character array
     * @param value  The array of chars to write (maximum length allowed is 65535)
     * @param output The data output to write to
     * @throws IOException Thrown if there was an IOException while writing a character
     */
    public static void writeCharsNL(char[] value, DataOutput output) throws IOException {
        for (char c : value) {
            output.writeChar(c);
        }
    }

    /**
     * Writes 2 bytes (a short, being the length of the string), and 2 bytes for each character of the given char array
     * @param value  The array of chars to write (maximum length allowed is 65535)
     * @param output The data output to write to
     * @throws IOException Thrown if there was an IOException while writing a character
     */
    public static void writeStringWL(String value, DataOutput output) throws IOException {
        output.writeShort(value.length());
        output.writeChars(value);
    }

    /**
     * Writes 2 bytes for each character of the given character array
     * @param value  The array of chars to write (maximum length allowed is 65535)
     * @param output The data output to write to
     * @throws IOException Thrown if there was an IOException while writing a character
     */
    public static void writeStringNL(String value, DataOutput output) throws IOException {
        output.writeChars(value);
    }

    /**
     * Reads 2 bytes (being the length of a string) as a short value, and reads that many characters
     * @param input The data input
     * @return A string
     * @throws IOException Thrown if there's an IO exception while reading the length, or a character
     */
    public static String readStringWL(DataInput input) throws IOException {
        int length = input.readUnsignedShort();
        int i = 0;
        StringBuilder string = new StringBuilder(length);

        try {
            for (i = 0; i < length; i++) {
                string.append(input.readChar());
            }
        }
        catch (IOException e) {
            throw new IOException("Failed to read string (of length " + length + ", at index " + i + ")");
        }

        return string.toString();
    }

    public static String readStringNL(DataInput input, int length) throws IOException {
        int i = 0;
        StringBuilder string = new StringBuilder(length);

        try {
            for (i = 0; i < length; i++) {
                string.append(input.readChar());
            }
        }
        catch (IOException e) {
            throw new IOException("Failed to read string (of length " + length + ", at index " + i + ")");
        }

        return string.toString();
    }

    public static byte[] readBytesWL(DataInput input) throws IOException {
        int length = input.readUnsignedShort();
        byte[] buffer = new byte[length];
        input.readFully(buffer, 0, length);
        return buffer;
    }

    public static void load(Class<? extends Packet> clazz) {
        try {
            Class.forName(clazz.getName());
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to locate packet class" + clazz.getName());
        }
    }
}
