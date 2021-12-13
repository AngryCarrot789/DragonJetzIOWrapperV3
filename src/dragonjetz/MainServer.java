package dragonjetz;

import com.fazecast.jSerialComm.SerialPort;
import dragonjetz.connection.serial.SerialConnection;
import dragonjetz.packet.Packet;
import dragonjetz.packet.packets.Packet1Chat;
import dragonjetz.packet.packets.Packet2AccountInfo;
import dragonjetz.packet.system.PacketSystem;
import dragonjetz.packet.utils.PacketUtils;

import java.text.MessageFormat;

public class MainServer {
    public static void main(String[] args) {
        PacketUtils.load(Packet1Chat.class);
        PacketUtils.load(Packet2AccountInfo.class);
        PacketSystem<SerialConnection> system = new PacketSystem<SerialConnection>(new SerialConnection("COM21", 9600));
        system.getConnection().getPort().setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 5000, 5000);
        system.getConnection().connect();

        while (true) {
            Packet packet = system.readPacket();
            handlePacket(packet);
        }
    }

    public static void handlePacket(Packet packet) {
        if (packet instanceof Packet1Chat) {
            System.out.println("Received Packet1Chat: '" + ((Packet1Chat) packet).getText() + "'");
        }
        else if (packet instanceof Packet2AccountInfo) {
            Packet2AccountInfo info = (Packet2AccountInfo) packet;
            System.out.println(
                    MessageFormat.format(
                            "Received Packet2AccountInfo. Name = {0}, Age = {1}, married = {2}, kids = {3}",
                            info.getUserName(), info.getAge(), info.isMarried(), info.getChildrenCount()));
        }
    }
}
