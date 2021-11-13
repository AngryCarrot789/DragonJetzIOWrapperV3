package reghzy;

import com.fazecast.jSerialComm.SerialPort;
import com.sun.corba.se.impl.presentation.rmi.StubFactoryStaticImpl;
import com.sun.org.apache.xerces.internal.impl.xs.SchemaSymbols;
import jdk.xml.internal.SecuritySupport;
import reghzy.connection.IConnection;
import reghzy.connection.serial.SerialConnection;
import reghzy.packet.Packet;
import reghzy.packet.packets.Packet1Chat;
import reghzy.packet.system.PacketSystem;
import reghzy.packet.utils.PacketUtils;

public class MainClient {
    public static void main(String[] args) {
        PacketUtils.load(Packet1Chat.class);
        PacketSystem<SerialConnection> system = new PacketSystem<SerialConnection>(new SerialConnection("COM20", 9600));
        system.getConnection().getPort().setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 30000, 30000);
        system.getConnection().getPort().setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 30000, 30000);
        system.getConnection().connect();

        int count = 0;
        while (true) {
            String str = "hello lol " + ++count;
            system.sendPacket(new Packet1Chat(str));
            System.out.println("Sent: '" + str + "'");

            try {
                Thread.sleep(3000);
            }
            catch (InterruptedException e) {

            }
        }
    }
}
