package reghzy.packet;

import reghzy.connection.IConnection;
import reghzy.packet.utils.PKTCreator;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;

public abstract class Packet {
    private static final HashMap<Integer, Class<? extends Packet>> idToClass = new HashMap<Integer, Class<? extends Packet>>();
    private static final HashMap<Class<? extends Packet>, Integer> classToId = new HashMap<Class<? extends Packet>, Integer>();
    private static final HashMap<Integer, PKTCreator<? extends Packet>> idToCreator = new HashMap<Integer, PKTCreator<? extends Packet>>();

    protected static <T extends Packet> void register(Class<T> clazz, int id, PKTCreator<T> creator) {
        if (id < 0 || id > 255) {
            throw new RuntimeException("ID must be between 0 and 255");
        }

        idToClass.put(id, clazz);
        classToId.put(clazz, id);
        idToCreator.put(id, creator);
    }

    public static void writePacket(Packet packet, DataOutput output) throws IOException {
        Integer id = classToId.get(packet.getClass());
        if (id == null) {
            throw new RuntimeException("Missing mapping for packet class " + packet.getClass().getName());
        }

        try {
            output.writeByte(id);
            output.writeShort(packet.getLength());
        }
        catch (IOException e) {
            throw new IOException("IOException while writing packet header", e);
        }

        try {
            packet.write(output);
        }
        catch (IOException e) {
            throw new IOException("IOException while writing packet data", e);
        }
        catch (Throwable e) {
            throw new RuntimeException("Failed to write packet data", e);
        }
    }

    public static Packet readPacketHeadAndTail(IConnection connection) throws IOException {
        DataInput input = connection.getInput();
        int id = input.readUnsignedByte();
        int len = input.readUnsignedShort();
        while (connection.getAvailableBytes() < len) {
            try {
                Thread.sleep(1);
            }
            catch (InterruptedException e) { }
        }

        return readPacketTail(id, len, input);
    }

    public static Packet readPacketTail(int id, int len, DataInput input) throws IOException {
        PKTCreator<? extends Packet> creator = idToCreator.get(id);
        if (creator == null) {
            throw new RuntimeException("Missing mapping for packet id " + id);
        }

        try {
            return creator.create(input, len);
        }
        catch (Throwable e) {
            throw new RuntimeException("Failed to create packet", e);
        }
    }

    public Packet() {

    }

    /**
     * Writes the packet data to the data output
     * @param output The data output (non-null)
     */
    public abstract void write(DataOutput output) throws IOException;

    /**
     * The length of the packet, in bytes
     * <p>
     *     Must be between 0 and 65535
     * </p>
     */
    public abstract int getLength();
}
