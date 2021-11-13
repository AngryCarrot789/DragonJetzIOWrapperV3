package reghzy;

import com.fazecast.jSerialComm.SerialPort;
import reghzy.connection.serial.SerialConnection;
import reghzy.packet.packets.Packet1Chat;
import reghzy.packet.system.PacketSystem;
import reghzy.packet.utils.PacketUtils;

public class MainServer {
    public static void main(String[] args) {
        PacketUtils.load(Packet1Chat.class);
        PacketSystem<SerialConnection> system = new PacketSystem<SerialConnection>(new SerialConnection("COM21", 9600));
        system.getConnection().getPort().setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 5000, 5000);
        system.getConnection().connect();

        while (true) {
            Packet1Chat packet = (Packet1Chat) system.readPacket();
            System.out.println("Packet: " + packet.getText());
        }
    }
}
