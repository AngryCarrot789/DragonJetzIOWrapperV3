package reghzy.packet.system;

import reghzy.connection.IConnection;
import reghzy.packet.Packet;

import java.io.IOException;

public class PacketSystem<T extends IConnection> {
    private final T connection;

    public PacketSystem(T connection) {
        this.connection = connection;
    }

    public void sendPacket(Packet packet) {
        try {
            Packet.writePacket(packet, this.connection.getOutput());
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to write packet", e);
        }
    }

    public Packet readPacket() {
        IConnection connection = this.connection;
        while(connection.getAvailableBytes() < 3) {
            try {
                Thread.sleep(1);
            }
            catch (InterruptedException e) { }
        }

        try {
            return Packet.readPacketHeadAndTail(this.connection);
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to read packet", e);
        }
    }

    public T getConnection() {
        return connection;
    }
}
