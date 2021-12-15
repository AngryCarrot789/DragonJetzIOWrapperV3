package reghzy;

import com.fazecast.jSerialComm.SerialPort;
import reghzy.connection.serial.SerialConnection;
import reghzy.packet.packets.Packet1Chat;
import reghzy.packet.packets.Packet2AccountInfo;
import reghzy.packet.system.PacketSystem;
import reghzy.packet.utils.PacketUtils;

public class MainClient {
    public static void main(String[] args) {
        PacketUtils.load(Packet1Chat.class);
        PacketUtils.load(Packet2AccountInfo.class);
        PacketSystem<SerialConnection> system = new PacketSystem<SerialConnection>(new SerialConnection("COM20", 9600));
        system.getConnection().getPort().setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 30000, 30000);
        system.getConnection().getPort().setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 30000, 30000);
        system.getConnection().getPort().clearRTS();
        system.getConnection().connect();

        int count = 0;
        try {

            while (true) {
                String str = "hello lol " + ++count;
                system.sendPacket(new Packet1Chat(str));
                System.out.println("Sent: '" + str + "'");

                system.sendPacket(new Packet2AccountInfo("joe ronimo", 69, true,  5));
                System.out.println("Sent packet 2");

                Thread.sleep(3000);
            }
        }
        catch (InterruptedException e) {

        }
    }
}
